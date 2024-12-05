package kz.beksultan.springsensor.repository;

import kz.beksultan.springsensor.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    long countByRainingTrue();
}
