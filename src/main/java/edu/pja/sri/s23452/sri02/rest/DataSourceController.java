package edu.pja.sri.s23452.sri02.rest;

import edu.pja.sri.s23452.sri02.dto.DataSourceDetailsDto;
import edu.pja.sri.s23452.sri02.dto.DataSourceDto;
import edu.pja.sri.s23452.sri02.dto.mapper.DataSourceDtoMapper;
import edu.pja.sri.s23452.sri02.model.DataSource;
import edu.pja.sri.s23452.sri02.repo.DataSourceRepository;

import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dataSources")
@RequiredArgsConstructor
public class DataSourceController {
    private final DataSourceRepository dataSourceRepository;
    private final DataSourceDtoMapper dataSourceDtoMapper;

    @GetMapping
    public ResponseEntity<Collection<DataSourceDto>> getDataSources(){
        List<DataSource> allDataSources = dataSourceRepository.findAll();
        List<DataSourceDto> result = allDataSources.stream()
                .map(dataSourceDtoMapper::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // TODO: Pobranie wyłącznie powiązanych obiektów dla wybranego DataSource
    // TODO: Stworzenie nowego powiązania między istniejącymi obiektami.
    // TODO: Usunięcie powiązania pomiędzy istniejącymi obiektami

    // TODO: dodanie nowego źródła + towarzyszące kursy



    @GetMapping("/{dataSourceId}")
    public ResponseEntity<DataSourceDetailsDto> getDataSourceById(@PathVariable Long dataSourceId){
        Optional<DataSource> dataSource = dataSourceRepository.findById(dataSourceId);
        if (dataSource.isPresent()){
            DataSourceDetailsDto dto = dataSourceDtoMapper.convertToDtoDetails(dataSource.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shortName/{shortName}")
    public ResponseEntity<DataSourceDetailsDto> getDataSourceByShortName(@PathVariable String shortName) {
        Optional<DataSource> dataSource = dataSourceRepository.getDataSourceDetailsByShortName(shortName);
        if (dataSource.isPresent()){
            DataSourceDetailsDto dto = dataSourceDtoMapper.convertToDtoDetails(dataSource.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addNewDataSource(@Valid @RequestBody DataSourceDto dto) {
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
        Optional<DataSource> currentDataSource = dataSourceRepository.findById(dataSourceId);
        if (currentDataSource.isPresent()) {
            dto.setId(dataSourceId);
            DataSource entity = dataSourceDtoMapper.convertToEntity(dto);
            dataSourceRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // TODO: Należy usunąć również powiązane obiekty, aby nie naruszyć constraint:
    //  Referential integrity constraint violation: "FKGO09MWX8AKJQRQITM6ODAX5FQ: PUBLIC.EXCHANGE_RATE FOREIGN KEY(SOURCE_ID) REFERENCES PUBLIC.DATA_SOURCE(ID) (1)"; SQL statement:
    @DeleteMapping("/{dataSourceId}")
    public ResponseEntity deleteDataSource(@PathVariable Long dataSourceId) {
        Optional<DataSource> currentDataSource = dataSourceRepository.findById(dataSourceId);
        if (currentDataSource.isPresent()) {
            dataSourceRepository.deleteById(dataSourceId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
