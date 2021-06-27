package edu.pja.sri.s23452.sri02.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRate {

    @Id
// commented out for the purposes of reproducibility:
//    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private LocalDateTime observationDateTime;

    @Min(value = 1, message = "multiplier must be equal or greater than one")
    private int multiplier;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private DataSource source;
}
