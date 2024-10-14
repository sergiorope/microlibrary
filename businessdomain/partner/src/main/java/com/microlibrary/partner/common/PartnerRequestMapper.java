package com.microlibrary.partner.common;

import com.microlibrary.partner.dto.PartnerRequest;
import com.microlibrary.partner.entities.Partner;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PartnerRequestMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),})
            
    Partner PartnerRequestToPartner(PartnerRequest source);

    List<Partner> PartnerRequestListToPartnerList(List<PartnerRequest> source);

    @InheritInverseConfiguration
    PartnerRequest PartnerToPartnerRequest(Partner source);

    @InheritInverseConfiguration
    List<PartnerRequest> PartnerListToPartnerRequestList(List<Partner> source);
}
