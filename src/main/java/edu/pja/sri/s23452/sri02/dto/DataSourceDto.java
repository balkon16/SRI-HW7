package edu.pja.sri.s23452.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceDto {
    private Long id;

    // TODO: nie więcej niż 10 znaków capsem
    @NotBlank
    private String shortName;

    @NotBlank
    private String fullName;

    // TODO: dwuliterowy kod capsem
    @NotBlank
    private String country;
}
