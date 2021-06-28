package edu.pja.sri.s23452.sri02.rest.Hateoas;

import edu.pja.sri.s23452.sri02.rest.DataSourceController;
import edu.pja.sri.s23452.sri02.rest.ExchangeRateController;
import org.springframework.hateoas.Link;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class HateoasLinkGenerator {

    public static Link createExchangeRateSelfLink(UUID id) {
        return linkTo(methodOn(ExchangeRateController.class).getExchangeRateById(id)).withSelfRel();
    }

    public static Link createDataSourceSelfLink(Long dataSourceId) {
        return linkTo(methodOn(DataSourceController.class).getDataSourceById(dataSourceId)).withSelfRel();
    }

    public static Link createDataSourceSelfLink(String shortName) {
        return linkTo(methodOn(DataSourceController.class).getDataSourceByShortName(shortName)).withSelfRel();
    }
}
