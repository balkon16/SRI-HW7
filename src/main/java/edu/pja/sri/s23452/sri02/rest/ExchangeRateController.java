package edu.pja.sri.s23452.sri02.rest;

import edu.pja.sri.s23452.sri02.dto.ExchangeRateDto;
import edu.pja.sri.s23452.sri02.dto.mapper.ExchangeRateDtoMapper;
import edu.pja.sri.s23452.sri02.model.ExchangeRate;
import edu.pja.sri.s23452.sri02.repo.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exchangeRates")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateDtoMapper exchangeRateDtoMapper;

    @GetMapping
    public ResponseEntity<Collection<ExchangeRateDto>> getExchangeRates(){
        List<ExchangeRate> allExchangeRates = exchangeRateRepository.findAll();
        List<ExchangeRateDto> result = allExchangeRates.stream()
                .map(exchangeRateDtoMapper::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findByQuoteCurrency")
    public ResponseEntity<Collection<ExchangeRateDto>> getExchangeRatesByQuoteCurrency(@RequestParam String quoteCurrency){
        List<ExchangeRate> allExchangeRates = exchangeRateRepository.findExchangeRateByQuoteCurrency(quoteCurrency);
        List<ExchangeRateDto> result = allExchangeRates.stream()
                .map(exchangeRateDtoMapper::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewExchangeRate(@Valid @RequestBody ExchangeRateDto dto){
        ExchangeRate entity = exchangeRateDtoMapper.convertToEntity(dto);
        exchangeRateRepository.save(entity);

        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    // TODO: dodać walidację -> @Valid
    // TODO: sprawdzić czy działa walidacja
    @PutMapping("/{exchangeRateId}")
    public ResponseEntity updateExchangeRate(@PathVariable UUID exchangeRateId, @RequestBody ExchangeRateDto exchangeRateDto){
        Optional<ExchangeRate> currentExchangeRate = exchangeRateRepository.findById(exchangeRateId);
        if(currentExchangeRate.isPresent()){
            exchangeRateDto.setId(exchangeRateId);
            ExchangeRate entity = exchangeRateDtoMapper.convertToEntity(exchangeRateDto);
            exchangeRateRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{exchangeRateId}")
    public ResponseEntity deleteExchangeRate(@PathVariable UUID exchangeRateId){
        Optional<ExchangeRate> currentExchangeRate = exchangeRateRepository.findById(exchangeRateId);
        if (currentExchangeRate.isPresent()){
            exchangeRateRepository.deleteById(exchangeRateId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }  else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}


