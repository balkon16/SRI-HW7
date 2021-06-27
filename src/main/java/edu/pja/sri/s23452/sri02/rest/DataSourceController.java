package edu.pja.sri.s23452.sri02.rest;

import edu.pja.sri.s23452.sri02.dto.DataSourceDetailsDto;
import edu.pja.sri.s23452.sri02.dto.DataSourceDto;
import edu.pja.sri.s23452.sri02.dto.ExchangeRateDto;
import edu.pja.sri.s23452.sri02.dto.mapper.DataSourceDtoMapper;
import edu.pja.sri.s23452.sri02.dto.mapper.ExchangeRateDtoMapper;
import edu.pja.sri.s23452.sri02.model.DataSource;
import edu.pja.sri.s23452.sri02.model.ExchangeRate;
import edu.pja.sri.s23452.sri02.repo.DataSourceRepository;
import edu.pja.sri.s23452.sri02.repo.ExchangeRateRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dataSources")
@RequiredArgsConstructor
public class DataSourceController {
    private final DataSourceRepository dataSourceRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final DataSourceDtoMapper dataSourceDtoMapper;
    private final ExchangeRateDtoMapper exchangeRateDtoMapper;

    @GetMapping
    public ResponseEntity<Collection<DataSourceDto>> getDataSources() {
        // TODO: dodać link self
        List<DataSource> allDataSources = dataSourceRepository.findAll();
        List<DataSourceDto> result = allDataSources.stream()
                .map(dataSourceDtoMapper::convertToDto)
                .collect(Collectors.toList());

        for (DataSourceDto dto : result) {
            dto.add(createDataSourceSelfLink(dto.getId()));
            // TODO: link do Exchange Rates
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{dataSourceId}")
    public ResponseEntity<DataSourceDetailsDto> getDataSourceById(@PathVariable Long dataSourceId) {
        Optional<DataSource> dataSource = dataSourceRepository.findById(dataSourceId);
        if (dataSource.isPresent()) {
            DataSourceDetailsDto dto = dataSourceDtoMapper.convertToDtoDetails(dataSource.get());
            dto.add(createDataSourceSelfLink(dataSourceId));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shortName/{shortName}")
    public ResponseEntity<DataSourceDetailsDto> getDataSourceByShortName(@PathVariable String shortName) {
        // TODO: dodać link self
        Optional<DataSource> dataSource = dataSourceRepository.getDataSourceDetailsByShortName(shortName);
        if (dataSource.isPresent()) {
            DataSourceDetailsDto dto = dataSourceDtoMapper.convertToDtoDetails(dataSource.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addNewDataSource(@Valid @RequestBody DataSourceDto dto) {
        // TODO: dodać link self
        DataSource entity = dataSourceDtoMapper.convertToEntity(dto);
        dataSourceRepository.save(entity);

        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @PutMapping("/{dataSourceId}")
    public ResponseEntity updateDataSource(@PathVariable Long dataSourceId, @Valid @RequestBody DataSourceDto dto) {
        // TODO: dodać link self
        Optional<DataSource> currentDataSource = dataSourceRepository.findById(dataSourceId);
        if (currentDataSource.isPresent()) {
            dto.setId(dataSourceId);
            DataSource entity = dataSourceDtoMapper.convertToEntity(dto);
            dataSourceRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


   @DeleteMapping("/{dataSourceId}")
    public ResponseEntity deleteDataSourceWithDetails(@PathVariable Long dataSourceId) {
       // TODO: dodać link self
        Optional<DataSource> currentDataSource = dataSourceRepository.findById(dataSourceId);
        if (currentDataSource.isPresent()) {

            for (ExchangeRate er : currentDataSource.get().getExchangeRates()) {
                exchangeRateRepository.deleteById(er.getId());
            }

            dataSourceRepository.deleteById(dataSourceId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{dataSourceId}/exchangeRates")
    public ResponseEntity getExchangeRatesByDataSource(@PathVariable Long dataSourceId) {
        // TODO: dodać link self
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findExchangeRateByDataSourceId(dataSourceId);
        List<ExchangeRateDto> result = exchangeRates.stream()
                .map(exchangeRateDtoMapper::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @PostMapping("/{dataSourceId}/exchangeRates/{exchangeRateId}")
    public ResponseEntity associateExchangeRateWithDataSource(@PathVariable Long dataSourceId,
                                                              @PathVariable UUID exchangeRateId) {
        // TODO: dodać link self
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findById(exchangeRateId);
        Optional<DataSource> dataSourceOptional = dataSourceRepository.findById(dataSourceId);

        if (exchangeRateOptional.isPresent() & dataSourceOptional.isPresent()) {
            ExchangeRate exchangeRate = exchangeRateOptional.get();
            DataSource dataSource = dataSourceOptional.get();

            if (exchangeRate.getSource() != null) {
                // do not update an already existing association
                return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
            }

            exchangeRate.setSource(dataSource);
            dataSource.getExchangeRates().add(exchangeRate);
            exchangeRateRepository.save(exchangeRate);
            dataSourceRepository.save(dataSource);

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{dataSourceId}/exchangeRates/{exchangeRateId}")
    public ResponseEntity removeExchangeRateAndDataSourceAssociation(@PathVariable Long dataSourceId,
                                                                     @PathVariable UUID exchangeRateId){
        // TODO: dodać link self
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findById(exchangeRateId);
        Optional<DataSource> dataSourceOptional = dataSourceRepository.findById(dataSourceId);

        if (exchangeRateOptional.isPresent() & dataSourceOptional.isPresent()) {
            ExchangeRate exchangeRate = exchangeRateOptional.get();
            DataSource dataSource = dataSourceOptional.get();

            exchangeRate.setSource(null);
            dataSource.getExchangeRates().removeIf(e -> e.getId().equals(exchangeRateId));
            exchangeRateRepository.save(exchangeRate);
            dataSourceRepository.save(dataSource);

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/fullInsert")
    public ResponseEntity addNewDataSourceWithExchangeRates(@Valid @RequestBody DataSourceDetailsDto dto) {
        // TODO: dodać link self
        DataSource dataSourceEntity = dataSourceDtoMapper.convertToEntityDetails(dto);
        dataSourceRepository.save(dataSourceEntity);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dataSourceEntity.getId())
            .toUri();

        List<URI> exchangeRateUris = new ArrayList<>();
        for (ExchangeRateDto exchangeRateDto : dto.getExchangeRates()) {
            ExchangeRate exchangeRateEntity = exchangeRateDtoMapper.convertToEntity(exchangeRateDto);
            exchangeRateEntity.setSource(dataSourceEntity);
            exchangeRateRepository.save(exchangeRateEntity);
            URI exchangeRateUri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(exchangeRateEntity.getId())
                    .toUri();
            exchangeRateUris.add(exchangeRateUri);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location - data source", location.toString());
        headers.add("Location - exchange rates", exchangeRateUris.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    private Link createDataSourceSelfLink(Long dataSourceId) {
        return linkTo(methodOn(DataSourceController.class).getDataSourceById(dataSourceId)).withSelfRel();
    }
}
