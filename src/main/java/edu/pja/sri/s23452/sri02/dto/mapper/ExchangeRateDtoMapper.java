package edu.pja.sri.s23452.sri02.dto.mapper;

import edu.pja.sri.s23452.sri02.dto.ExchangeRateDto;
import edu.pja.sri.s23452.sri02.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateDtoMapper {

    private final ModelMapper modelMapper;

    public ExchangeRateDto convertToDto(ExchangeRate e){
        return modelMapper.map(e, ExchangeRateDto.class);
    }

    public ExchangeRate convertToEntity(ExchangeRateDto dto){
        return modelMapper.map(dto, ExchangeRate.class);
    }


}
