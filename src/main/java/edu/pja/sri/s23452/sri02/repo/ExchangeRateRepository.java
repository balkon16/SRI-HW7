package edu.pja.sri.s23452.sri02.repo;

import edu.pja.sri.s23452.sri02.model.ExchangeRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, UUID> {
    List<ExchangeRate> findAll();
    List<ExchangeRate> findExchangeRateByQuoteCurrency(String quoteCurrency);

    @Query("SELECT d.exchangeRates FROM DataSource AS d WHERE d.id=:dataSourceId")
    List<ExchangeRate> findExchangeRateByDataSourceId(@PathVariable Long dataSourceId);
}
