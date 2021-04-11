package edu.pja.sri.s23452.sri02.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {

    private UUID id;
    private Double exchangeRate;
    private String quoteCurrency;
    private String baseCurrency;

    @DateTimeFormat(pattern = "yyyy-dd-MMTHH:mm:ss")
    private LocalDateTime observationDateTime;
    private int multiplier;
    private String source;
}
