package edu.pja.sri.s23452.sri02;

import edu.pja.sri.s23452.sri02.model.DataSource;
import edu.pja.sri.s23452.sri02.model.ExchangeRate;
import edu.pja.sri.s23452.sri02.repo.DataSourceRepository;
import edu.pja.sri.s23452.sri02.repo.ExchangeRateRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;

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

        ExchangeRate er1 = ExchangeRate.builder()
                .quoteCurrency("EUR")
                .baseCurrency("PLN")
                .multiplier(1)
                .exchangeRate(4.55)
                .observationDateTime(LocalDateTime.now())
                .source(nbp)
                .build();

        nbp.getExchangeRates().add(er1);


        // saveAll do wrzucenia kilku naraz
//        exchangeRateRepository.saveAll(List.of(er1));
        dataSourceRepository.save(nbp);
        exchangeRateRepository.save(er1);

    }

}
