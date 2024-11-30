/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.partner.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.microlibrary.partner.common.PartnerRequestMapper;
import com.microlibrary.partner.common.PartnerResponseMapper;
import com.microlibrary.partner.dto.PartnerRequest;
import com.microlibrary.partner.dto.PartnerResponse;
import com.microlibrary.partner.entities.Partner;
import com.microlibrary.partner.exception.BussinesRuleException;
import com.microlibrary.partner.repository.PartnerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
public class PartnerServiceImpl {

    @Autowired
    PartnerRepository par;

    @Autowired
    PartnerRequestMapper prq;

    @Autowired
    PartnerResponseMapper prp;

    

    public List<PartnerResponse> getAll() throws BussinesRuleException {

        List<Partner> findAll = par.findAll();

        if (findAll.isEmpty()) {
            throw new BussinesRuleException("404", "Partners not found", HttpStatus.NOT_FOUND);
        }

        List<PartnerResponse> findAllResponse = prp.PartnerListToPartnerResponseList(findAll);

        return findAllResponse;
    }

    public PartnerResponse getById(long id) throws BussinesRuleException {

        Optional<Partner> findById = par.findById(id);

        if (!findById.isPresent()) {
            throw new BussinesRuleException("404", "Partner not found", HttpStatus.NOT_FOUND);
        }

        Partner partner = findById.get();

        PartnerResponse findIdResponse = prp.PartnerToPartnerResponse(partner);

        return findIdResponse;
    }


    public PartnerResponse post(PartnerRequest input) throws BussinesRuleException {

        if (input == null || input.getName() == null || input.getDescription() == null) {
            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST);
        }

        Partner post = prq.PartnerRequestToPartner(input);

        par.save(post);

        PartnerResponse partnerResponse = prp.PartnerToPartnerResponse(post);

        return partnerResponse;
    }

    public PartnerResponse put(long id, PartnerRequest input) throws BussinesRuleException {

        PartnerResponse put = getById(id);

        if (put == null) {
            throw new BussinesRuleException("404", "Partner not found", HttpStatus.NOT_FOUND);
        }

        put.setName(input.getName());
        put.setDescription(input.getDescription());

        if (put.getName().isEmpty() || put.getDescription().isEmpty()) {
            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.NOT_FOUND);
        }

        Partner partner = prp.PartnerResponseToPartner(put);

        par.save(partner);

        PartnerResponse partnerResponse = prp.PartnerToPartnerResponse(partner);

        return partnerResponse;
    }

    public PartnerResponse delete(long id) throws BussinesRuleException {

        PartnerResponse delete = getById(id);

        if (delete == null) {
            throw new BussinesRuleException("404", "Partner not found", HttpStatus.NOT_FOUND);
        }

        Partner partner = prp.PartnerResponseToPartner(delete);

        par.delete(partner);

        PartnerResponse partnerResponse = prp.PartnerToPartnerResponse(partner);

        return partnerResponse;
    }

    

}
