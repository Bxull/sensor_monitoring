package kz.beksultan.springsensor.service;

import jakarta.validation.Valid;
import kz.beksultan.springsensor.model.Sensor;
import kz.beksultan.springsensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SensorService {

    private final SensorRepository sensorRepository;

    public Sensor registerSensor(@Valid Sensor sensor) {
        if (sensorRepository.existsByName(sensor.getName())) {
            throw new IllegalArgumentException("Sensor with the same name already exists: " + sensor.getName());
        }
        return sensorRepository.save(sensor);
    }
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> getSensorByName(String name) {
        return sensorRepository.findByName(name);
    }
}
