package edu.pja.sri.s23452.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {

    private UUID id;


    @NotNull(message = "exchangeRate is mandatory")
    @DecimalMin(value = "0.0", message = "exchangeRate cannot be negative")
    private Double exchangeRate;


    @NotBlank(message = "quoteCurrency is mandatory")
    @Pattern(regexp = "[A-Z]{3}", message = "quoteCurrency should be three capital letters")
    private String quoteCurrency;

    @NotBlank(message = "baseCurrency is mandatory")
    @Pattern(regexp = "[A-Z]{3}", message = "baseCurrency should be three capital letters")
    private String baseCurrency;

    @NotNull(message = "observationDateTime is mandatory")
    @DateTimeFormat(pattern = "yyyy-dd-MMTHH:mm:ss")
    private LocalDateTime observationDateTime;

    @Min(value = 1, message = "multiplier must be equal or greater than one")
    private int multiplier;
}
