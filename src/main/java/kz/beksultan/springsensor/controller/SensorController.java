package kz.beksultan.springsensor.controller;

import jakarta.validation.Valid;
import kz.beksultan.springsensor.dto.SensorDTO;
import kz.beksultan.springsensor.model.Sensor;
import kz.beksultan.springsensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping("/registration")
    public ResponseEntity<Sensor> registerSensor(@Valid @RequestBody SensorDTO sensorDTO) {
        Sensor sensor = Sensor.builder()
                .name(sensorDTO.getName())
                .build();

        Sensor registeredSensor = sensorService.registerSensor(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredSensor);
    }
    @GetMapping
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();
        return ResponseEntity.ok(sensors);
    }
}
