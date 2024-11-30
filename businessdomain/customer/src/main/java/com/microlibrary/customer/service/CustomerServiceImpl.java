/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.customer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.microlibrary.customer.common.CustomerRequestMapper;
import com.microlibrary.customer.common.CustomerResponseMapper;
import com.microlibrary.customer.dto.CustomerRequest;
import com.microlibrary.customer.dto.CustomerResponse;
import com.microlibrary.customer.entities.Customer;
import com.microlibrary.customer.exception.BussinesRuleException;
import com.microlibrary.customer.repository.CustomerRepository;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository cr;

    @Autowired
    CustomerRequestMapper prq;

    @Autowired
    CustomerResponseMapper prp;

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

    @Override
    public List<CustomerResponse> getAll() throws BussinesRuleException {

        List<Customer> findAll = cr.findAll();

        if (findAll.isEmpty()) {

            throw new BussinesRuleException("404", "customers not found", HttpStatus.NOT_FOUND);
        }

        List<CustomerResponse> findAllResponse = prp.CustomerListToCustomerResponseList(findAll);

        return findAllResponse;

    }

    @Override
    public CustomerResponse getById(long id) throws BussinesRuleException {

        Optional<Customer> findById = cr.findById(id);

        if (!findById.isPresent()) {
            throw new BussinesRuleException("404", "customer not found", HttpStatus.NOT_FOUND);
        }

        Customer customer = findById.get();

        CustomerResponse findIdResponse = prp.CustomerToCustomerResponse(customer);

        return findIdResponse;
    }
    
    @Override
    public List<CustomerResponse> getByPartnerId(long partner_Id) throws BussinesRuleException {

        List<Customer> findById = cr.findByPartnerId(partner_Id);
        
        if(findById.isEmpty()){
            
            throw new BussinesRuleException("404", "customers not found", HttpStatus.NOT_FOUND);
        }
        
        List<CustomerResponse>customerResponseList=prp.CustomerListToCustomerResponseList(findById);

       


        return customerResponseList;
    }

    @Override
    public String getPartner(long id) throws BussinesRuleException, UnknownHostException {

        CustomerResponse customer = getById(id);

        String PartnerName = getPartnerName(customer.getPartner_Id());

        if (PartnerName.isBlank()) {
            throw new BussinesRuleException("404", "Partner associate not found", HttpStatus.NOT_FOUND);
        }

        return PartnerName;

    }

    @Override
    public CustomerResponse post(CustomerRequest input) throws BussinesRuleException {

        if (input == null || input.getName() == null || input.getSurname() == null) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST);
        }

        Customer post = prq.CustomerRequestToCustomer(input);

        cr.save(post);

        CustomerResponse customerResponse = prp.CustomerToCustomerResponse(post);

        return customerResponse;
    }

    @Override
    public CustomerResponse put(long id, CustomerRequest input) throws BussinesRuleException {

        CustomerResponse put = getById(id);

        if (put == null) {

            throw new BussinesRuleException("404", "customer not found", HttpStatus.NOT_FOUND);
        }

        put.setName(input.getName());
        put.setSurname(input.getSurname());
        put.setPartner_Id(input.getPartner_Id());

        if (put.getName().isEmpty() || put.getSurname().isEmpty()) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.NOT_FOUND);
        }

        Customer customer = prp.CustomerResponseToCustomer(put);

        cr.save(customer);

        CustomerResponse customerResponse = prp.CustomerToCustomerResponse(customer);

        return customerResponse;
    }

    @Override
    public CustomerResponse delete(long id) throws BussinesRuleException {

        CustomerResponse delete = getById(id);

        if (delete == null) {

            throw new BussinesRuleException("404", "customer not found", HttpStatus.NOT_FOUND);
        }

        Customer customer = prp.CustomerResponseToCustomer(delete);

        cr.delete(customer);

        CustomerResponse customerResponse = prp.CustomerToCustomerResponse(customer);

        return customerResponse;

    }


    public String getPartnerName(long id) throws UnknownHostException {
        String name = "";

        try {
            // Configura el WebClient con la URL base correcta
            WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl("http://microlibrary-partner/partner")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "http://microlibrary-partner/partner"))
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
