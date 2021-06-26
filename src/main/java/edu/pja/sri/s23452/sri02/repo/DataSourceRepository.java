package edu.pja.sri.s23452.sri02.repo;

import edu.pja.sri.s23452.sri02.model.DataSource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DataSourceRepository extends CrudRepository<DataSource, Long> {
    List<DataSource> findAll();

    // pobieranie zależnych obiektów
    @Query("FROM DataSource AS ds LEFT JOIN FETCH ds.exchangeRates WHERE ds.id=:dataSourceId")
    Optional<DataSource> getDataSourceDetailsById(@Param("dataSourceId") Long dataSourceId);

    @Query("FROM DataSource AS ds LEFT JOIN FETCH ds.exchangeRates WHERE ds.shortName=:dataSourceShortName")
    Optional<DataSource> getDataSourceDetailsByShortName(@Param("dataSourceShortName") String dataSourceShortName);
}
