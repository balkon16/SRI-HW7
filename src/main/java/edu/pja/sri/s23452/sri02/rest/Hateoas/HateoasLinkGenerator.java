package edu.pja.sri.s23452.sri02.rest.Hateoas;

import edu.pja.sri.s23452.sri02.dto.ExchangeRateDto;
import edu.pja.sri.s23452.sri02.rest.DataSourceController;
import edu.pja.sri.s23452.sri02.rest.ExchangeRateController;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class HateoasLinkGenerator {

    public static Link createExchangeRateByIdSelfLink(UUID id) {
        return linkTo(methodOn(ExchangeRateController.class).getExchangeRateById(id)).withSelfRel();
    }

    public static Link createDataSourceSelfLink(Long dataSourceId) {
        return linkTo(methodOn(DataSourceController.class).getDataSourceById(dataSourceId)).withSelfRel();
    }

    public static Link createDataSourceSelfLink(String shortName) {
        return linkTo(methodOn(DataSourceController.class).getDataSourceByShortName(shortName)).withSelfRel();
    }

    public static List<ExchangeRateDto> addLinkToEntityDtos(List<ExchangeRateDto> entityDtoList) {
        for (ExchangeRateDto dto : entityDtoList) {
            dto.add(HateoasLinkGenerator.createExchangeRateByIdSelfLink(dto.getId()));
        }
        return entityDtoList;
    }

    public static ExchangeRateDto addSelfLinkToExchangeRateDto(ExchangeRateDto dto) {
        dto.add(HateoasLinkGenerator.createExchangeRateByIdSelfLink(dto.getId()));
        return dto;
    }

}
