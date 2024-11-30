package com.microlibrary.partner.service;

import com.microlibrary.partner.dto.PartnerRequest;
import com.microlibrary.partner.dto.PartnerResponse;
import com.microlibrary.partner.exception.BussinesRuleException;

import java.util.List;

public interface PartnerService {

    List<PartnerResponse> getAll() throws BussinesRuleException;

    PartnerResponse getById(long id) throws BussinesRuleException;

    PartnerResponse post(PartnerRequest input) throws BussinesRuleException;

    PartnerResponse put(long id, PartnerRequest input) throws BussinesRuleException;

    PartnerResponse delete(long id) throws BussinesRuleException;
}
