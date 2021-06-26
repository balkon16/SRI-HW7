package edu.pja.sri.s23452.sri02.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Double exchangeRate;
    private String quoteCurrency;
    private String baseCurrency;
    private LocalDateTime observationDateTime;
    private int multiplier;
//    private String source;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private DataSource source;
}
