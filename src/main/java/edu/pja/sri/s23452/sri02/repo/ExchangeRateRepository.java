package edu.pja.sri.s23452.sri02.repo;

import edu.pja.sri.s23452.sri02.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, UUID> {
    List<ExchangeRate> findAll();
    List<ExchangeRate> findExchangeRateByQuoteCurrency(String quoteCurrency);
}
