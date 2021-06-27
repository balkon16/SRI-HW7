package edu.pja.sri.s23452.sri02.dto.mapper;

import edu.pja.sri.s23452.sri02.dto.DataSourceDetailsDto;
import edu.pja.sri.s23452.sri02.dto.DataSourceDto;
import edu.pja.sri.s23452.sri02.model.DataSource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSourceDtoMapper {
    private final ModelMapper modelMapper;

    public DataSourceDetailsDto convertToDtoDetails(DataSource ds) {
        return modelMapper.map(ds, DataSourceDetailsDto.class);
    }

    public DataSourceDto convertToDto(DataSource ds) {
        return modelMapper.map(ds, DataSourceDto.class);
    }

    public DataSource convertToEntity(DataSourceDto dto) {
        return modelMapper.map(dto, DataSource.class);
    }

    public DataSource convertToEntityDetails(DataSourceDetailsDto dto) {
        return modelMapper.map(dto, DataSource.class);
    }

    // Jeżeli będziesz tworzył za jednym zamachem
    // DataSource z kursami musisz mieć metodę convertToEntityDetails
}
