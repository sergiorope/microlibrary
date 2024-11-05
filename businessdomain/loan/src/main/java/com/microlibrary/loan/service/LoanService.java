package com.microlibrary.loan.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.microlibrary.loan.common.LoanRequestMapper;
import com.microlibrary.loan.common.LoanResponseMapper;
import com.microlibrary.loan.dto.LoanRequest;
import com.microlibrary.loan.dto.LoanResponse;
import com.microlibrary.loan.entities.Loan;
import com.microlibrary.loan.repository.LoanRepository;
import com.microlibrary.loan.exception.BussinesRuleException;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;

/**
 *
 * @author Sergio
 */
@Service
public class LoanService {

    @Autowired
    LoanRepository lr;

    @Autowired
    LoanRequestMapper prq;

    @Autowired
    LoanResponseMapper prp;
    
    
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    HttpClient client = HttpClient.create()
            //Connection Timeout: is a period within which a connection between a client and a server must be established
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            //Response Timeout: The maximun time we wait to receive a response after sending a request
            .responseTimeout(Duration.ofSeconds(1))
            // Read and Write Timeout: A read timeout occurs when no data was read within a certain 
            //period of time, while the write timeout when a write operation cannot finish at a specific time
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });
    
    

    public List<LoanResponse> getAll() throws BussinesRuleException {

        List<Loan> findAll = lr.findAll();

        if (findAll.isEmpty()) {
            throw new BussinesRuleException("404", "Loans not found", HttpStatus.NOT_FOUND);
        }

        List<LoanResponse> findAllResponse = prp.LoanListToLoanResponseList(findAll);

        return findAllResponse;
    }

    public LoanResponse getById(long id) throws BussinesRuleException {

        Optional<Loan> findById = lr.findById(id);

        if (!findById.isPresent()) {
            throw new BussinesRuleException("404", "Loan not found", HttpStatus.NOT_FOUND);
        }

        Loan loan = findById.get();

        LoanResponse findIdResponse = prp.LoanToLoanResponse(loan);

        return findIdResponse;
    }
    
      public String getCustomer(long id) throws BussinesRuleException, UnknownHostException {

        LoanResponse loan = getById(id);

        String customerName = getCustomerName(loan.getCustomer_Id());

        if (customerName.isBlank()) {
            throw new BussinesRuleException("404", "Customer associate not found", HttpStatus.NOT_FOUND);
        }

        return customerName;

    }


    public List<LoanResponse> getByCustomerId(long customer_Id) throws BussinesRuleException {

        List<Loan> findById = lr.findByCustomerId(customer_Id);
        
         if(findById.isEmpty()){
            
            throw new BussinesRuleException("404", "loans not found", HttpStatus.NOT_FOUND);
        }
        
        List<LoanResponse>loanResponseList=prp.LoanListToLoanResponseList(findById);

        return loanResponseList;
    }

    public LoanResponse post(LoanRequest input) throws BussinesRuleException {

        if (input == null || input.getStart_date() == null || input.getEnd_date() == null
                || input.getStatus().isEmpty()) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST);
        }

        Loan post = prq.LoanRequestToLoan(input);

        lr.save(post);

        LoanResponse loanResponse = prp.LoanToLoanResponse(post);

        return loanResponse;
    }

    public LoanResponse put(long id, LoanRequest input) throws BussinesRuleException {

        LoanResponse put = getById(id);

        if (put == null) {
            throw new BussinesRuleException("404", "Loan not found", HttpStatus.NOT_FOUND);
        }

        put.setStart_date(input.getStart_date());
        put.setEnd_date(input.getEnd_date());
        put.setStatus(input.getStatus());
        put.setCustomer_Id(input.getCustomer_Id());

        if (put.getStart_date().isEmpty() || put.getEnd_date().isEmpty() || put.getStatus().isEmpty()) {
            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.NOT_FOUND);
        }

        Loan loan = prp.LoanResponseToLoan(put);

        lr.save(loan);

        LoanResponse loanResponse = prp.LoanToLoanResponse(loan);

        return loanResponse;
    }

    public LoanResponse delete(long id) throws BussinesRuleException {

        LoanResponse delete = getById(id);

        if (delete == null) {
            throw new BussinesRuleException("404", "Loan not found", HttpStatus.NOT_FOUND);
        }

        Loan loan = prp.LoanResponseToLoan(delete);

        lr.delete(loan);

        LoanResponse loanResponse = prp.LoanToLoanResponse(loan);

        return loanResponse;
    }
    
    private String getCustomerName(long id) throws UnknownHostException {
        String name = "";

        try {
            // Configura el WebClient con la URL base correcta
            WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl("http://microlibrary-customer/customer")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "http://microlibrary-customer/customer"))
                    .build();

            JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

       
            name = block.get("name").asText();
        } catch (WebClientResponseException ex) {
            // Manejo de excepciones para diagnosticar el error
            System.out.println("Error response body: " + ex.getResponseBodyAsString());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "";
            } else {
                throw new UnknownHostException("Error: " + ex.getMessage() + ", Status Code: " + ex.getStatusCode());
            }
        }
        return name;
    }
    

}
