package edu.pja.sri.s23452.sri02.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "shortName is mandatory")
    @Size(max = 10, message = "maximum length for shortName is 10 characters")
    private String shortName;

    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @NotBlank(message = "country is mandatory")
    @Pattern(regexp = "[A-Z]{2}", message = "country is two capital letters")
    private String country;

    @OneToMany(mappedBy = "source")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ExchangeRate> exchangeRates;
}
