package kz.beksultan.springsensor.repository;

import kz.beksultan.springsensor.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Optional<Sensor> findByName(String name);

    boolean existsByName(String name);
}
