package edu.pja.sri.s23452.sri02.repo;

import edu.pja.sri.s23452.sri02.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

// TODO: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods ->
//  jeżeli będziesz tworzył inne metody, np. wyszukanie po nazwie waluty

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, UUID> {
    List<ExchangeRate> findAll();
    List<ExchangeRate> findExchangeRateByQuoteCurrency(String quoteCurrency);
}
