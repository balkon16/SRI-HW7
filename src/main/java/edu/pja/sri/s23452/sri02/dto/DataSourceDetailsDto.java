package edu.pja.sri.s23452.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceDetailsDto extends RepresentationModel<DataSourceDetailsDto> {
    private Long id;

    @NotBlank(message = "shortName is mandatory")
    @Size(max = 10, message = "maximum length for shortName is 10 characters")
    private String shortName;

    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @NotBlank(message = "country is mandatory")
    @Pattern(regexp = "[A-Z]{2}", message = "country is two capital letters")
    private String country;

    @Valid
    private Set<ExchangeRateDto> exchangeRates;
}
