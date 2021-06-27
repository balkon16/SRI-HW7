package edu.pja.sri.s23452.sri02;

import edu.pja.sri.s23452.sri02.model.DataSource;
import edu.pja.sri.s23452.sri02.model.ExchangeRate;
import edu.pja.sri.s23452.sri02.repo.DataSourceRepository;
import edu.pja.sri.s23452.sri02.repo.ExchangeRateRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer implements ApplicationRunner {

    private ExchangeRateRepository exchangeRateRepository;
    private DataSourceRepository dataSourceRepository;

    public DataInitializer(ExchangeRateRepository exchangeRateRepository, DataSourceRepository dataSourceRepository){
        this.exchangeRateRepository = exchangeRateRepository;
        this.dataSourceRepository = dataSourceRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        DataSource nbp = DataSource.builder()
                .shortName("NBP")
                .fullName("Narodowy Bank Polski")
                .country("PL")
                .exchangeRates(new HashSet<>())
                .build();

        DataSource ecb = DataSource.builder()
                .shortName("ECB")
                .fullName("European Central Bank")
                .country("DE")
                .exchangeRates(new HashSet<>())
                .build();

        ExchangeRate er1 = ExchangeRate.builder()
                .quoteCurrency("PLN")
                .baseCurrency("EUR")
                .multiplier(1)
                .exchangeRate(4.55)
                .observationDateTime(LocalDateTime.now())
                .source(nbp)
                .id(UUID.fromString("4e344733-f835-4832-a01b-b929d01af28d")) // for the purposes of reproducibility
                .build();

        ExchangeRate er2 = ExchangeRate.builder()
                .quoteCurrency("PLN")
                .baseCurrency("USD")
                .multiplier(1)
                .exchangeRate(3.72)
                .observationDateTime(LocalDateTime.now())
                .source(nbp)
                .id(UUID.fromString("08f51ed1-7704-4dcf-a5aa-899f623a43d3")) // for the purposes of reproducibility
                .build();

        ExchangeRate er3 = ExchangeRate.builder()
                .quoteCurrency("PLN")
                .baseCurrency("CHF")
                .multiplier(1)
                .exchangeRate(4.15)
                .observationDateTime(LocalDateTime.now())
                .id(UUID.fromString("4342f3f8-d0b1-4343-bca6-8d4d44616215")) // for the purposes of reproducibility
                .build();

        ExchangeRate er4 = ExchangeRate.builder()
                .quoteCurrency("EUR")
                .baseCurrency("USD")
                .multiplier(1)
                .exchangeRate(0.85)
                .observationDateTime(LocalDateTime.now())
                .source(ecb)
                .id(UUID.fromString("c2a70783-8738-4041-9456-8d65e5be2179"))
                .build();

        nbp.getExchangeRates().addAll(List.of(er1, er2)); //er3 is left out on purpose
        ecb.getExchangeRates().add(er4);
        dataSourceRepository.saveAll(List.of(nbp, ecb));
        exchangeRateRepository.saveAll(List.of(er1, er2, er3, er4));

    }

}
