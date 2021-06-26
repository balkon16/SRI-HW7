package edu.pja.sri.s23452.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceDetailsDto {
    private Long id;
    private String shortName;
    private String fullName;
    private String country;
    private Set<ExchangeRateDto> exchangeRates;
}
