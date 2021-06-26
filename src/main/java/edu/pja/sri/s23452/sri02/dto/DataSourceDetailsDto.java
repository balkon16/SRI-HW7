package edu.pja.sri.s23452.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceDetailsDto {
    private Long id;

    //TODO: takie same constraints jak w DataSourceDto

    @NotBlank
    private String shortName;

    @NotBlank
    private String fullName;

    @NotBlank
    private String country;

    private Set<ExchangeRateDto> exchangeRates;
}
