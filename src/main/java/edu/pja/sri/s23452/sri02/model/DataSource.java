package edu.pja.sri.s23452.sri02.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String shortName;
    private String fullName;
    private String country;

    @OneToMany(mappedBy = "source")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ExchangeRate> exchangeRates;
}
