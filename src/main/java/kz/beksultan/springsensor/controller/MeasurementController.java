package kz.beksultan.springsensor.controller;

import jakarta.validation.Valid;
import kz.beksultan.springsensor.dto.MeasurementDTO;
import kz.beksultan.springsensor.model.Measurement;
import kz.beksultan.springsensor.model.Sensor;
import kz.beksultan.springsensor.service.MeasurementService;
import kz.beksultan.springsensor.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private SensorService sensorService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Measurement addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO) {
        Sensor sensor = sensorService.getSensorByName(measurementDTO.getSensor().getName())
                .orElseThrow(() -> new RuntimeException("Sensor not found"));

        Measurement measurement = Measurement.builder()
                .value(measurementDTO.getValue())
                .raining(measurementDTO.isRaining())
                .sensor(sensor)
                .build();

        return measurementService.saveMeasurement(measurement);
    }


    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementService.getAllMeasurements();
    }

    @GetMapping("/rainyDaysCount")
    public long getRainyDaysCount() {
        return measurementService.countRainyDays();
    }
}
