/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.microlibrary.partner.common;

import com.microlibrary.partner.dto.PartnerResponse;
import com.microlibrary.partner.entities.Partner;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author sergio
 */
@Mapper(componentModel = "spring")
public interface PartnerResponseMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
    })
            
    PartnerResponse PartnerToPartnerResponse(Partner source);

    List<PartnerResponse> PartnerListToPartnerResponseList(List<Partner> source);

    @InheritInverseConfiguration
    Partner PartnerResponseToPartner(PartnerResponse srr);

    @InheritInverseConfiguration
    List<Partner> PartnerResponseToPartnerList(List<PartnerResponse> source);
}
