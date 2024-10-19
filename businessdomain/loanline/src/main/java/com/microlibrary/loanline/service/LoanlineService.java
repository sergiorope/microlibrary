package com.microlibrary.loanline.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.microlibrary.loanline.common.LoanlineRequestMapper; // Actualizado
import com.microlibrary.loanline.common.LoanlineResponseMapper; // Actualizado
import com.microlibrary.loanline.dto.LoanlineRequest; // Actualizado
import com.microlibrary.loanline.dto.LoanlineResponse; // Actualizado
import com.microlibrary.loanline.entities.Loanline; // Actualizado
import com.microlibrary.loanline.repository.LoanlineRepository; // Actualizado
import com.microlibrary.loanline.exception.BussinesRuleException;
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
public class LoanlineService { 

    @Autowired
    LoanlineRepository llr; 

    @Autowired
    LoanlineRequestMapper prq; 

    @Autowired
    LoanlineResponseMapper prp; 
    
    
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

    public List<LoanlineResponse> getAll() throws BussinesRuleException {

        List<Loanline> findAll = llr.findAll(); // Actualizado

        if (findAll.isEmpty()) {
            throw new BussinesRuleException("404", "Loanlines not found", HttpStatus.NOT_FOUND); 
        }

        List<LoanlineResponse> findAllResponse = prp.LoanlineListToLoanlineResponseList(findAll); 

        return findAllResponse;
    }

    public LoanlineResponse getById(long id) throws BussinesRuleException {

        Optional<Loanline> findById = llr.findById(id); // Actualizado

        if (!findById.isPresent()) {
            throw new BussinesRuleException("404", "Loanline not found", HttpStatus.NOT_FOUND); 
        }

        Loanline loanline = findById.get(); // Actualizado

        LoanlineResponse findIdResponse = prp.LoanlineToLoanlineResponse(loanline); 

        return findIdResponse;
    }
    
      public List<LoanlineResponse> getByLoanId(long loan_Id) throws BussinesRuleException {

        List<Loanline> findById = llr.findByLoanId(loan_Id);
        
         if(findById.isEmpty()){
            
            throw new BussinesRuleException("404", "loanline not found", HttpStatus.NOT_FOUND);
        }
        
       List<LoanlineResponse> findByIdResponse = prp.LoanlineListToLoanlineResponseList(findById);

        return findByIdResponse;
    }
      
      public String getProduct(long id) throws BussinesRuleException, UnknownHostException {

        LoanlineResponse loanline = getById(id);

        String ProductName = getProductName(loanline.getProduct_Id());

        if (ProductName.isBlank()) {
            throw new BussinesRuleException("404", "Product associate not found", HttpStatus.NOT_FOUND);
        }

        return ProductName;

    }


    public LoanlineResponse post(LoanlineRequest input) throws BussinesRuleException { 

        if (input == null || input.getProduct_Id() <= 0 || input.getLoan_Id() <= 0) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST);
        }

        Loanline post = prq.LoanlineRequestToLoanline(input); // Actualizado

        llr.save(post);

        LoanlineResponse loanlineResponse = prp.LoanlineToLoanlineResponse(post); 

        return loanlineResponse;
    }

    public LoanlineResponse put(long id, LoanlineRequest input) throws BussinesRuleException { 

        LoanlineResponse put = getById(id); // Actualizado

        if (put == null) {
            throw new BussinesRuleException("404", "Loanline not found", HttpStatus.NOT_FOUND); 
        }

        put.setProduct_Id(input.getProduct_Id());
        put.setLoan_Id(input.getLoan_Id());

        if (put.getProduct_Id() <= 0 || put.getLoan_Id() <= 0) {
            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST); 
        }

        Loanline loanline = prp.LoanlineResponseToLoanline(put); 

        llr.save(loanline);

        LoanlineResponse loanlineResponse = prp.LoanlineToLoanlineResponse(loanline); 

        return loanlineResponse;
    }

    public LoanlineResponse delete(long id) throws BussinesRuleException { 

        LoanlineResponse delete = getById(id); // Actualizado

        if (delete == null) {
            throw new BussinesRuleException("404", "Loanline not found", HttpStatus.NOT_FOUND); 
        }

        Loanline loanline = prp.LoanlineResponseToLoanline(delete);

        llr.delete(loanline);

        LoanlineResponse loanlineResponse = prp.LoanlineToLoanlineResponse(loanline); 

        return loanlineResponse;
    }
    
    private String getProductName(long id) throws UnknownHostException {
        String name = "";

        try {
            // Configura el WebClient con la URL base correcta
            WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl("http://microlibrary-product/product")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "http://microlibrary-product/product"))
                    .build();

            JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            // Obtiene el nombre del partner
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
