package edu.pja.sri.s23452.sri02.rest;

import edu.pja.sri.s23452.sri02.dto.DataSourceDetailsDto;
import edu.pja.sri.s23452.sri02.dto.DataSourceDto;
import edu.pja.sri.s23452.sri02.dto.mapper.DataSourceDtoMapper;
import edu.pja.sri.s23452.sri02.model.DataSource;
import edu.pja.sri.s23452.sri02.repo.DataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
