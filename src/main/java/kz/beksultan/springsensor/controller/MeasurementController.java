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

    // Метод для добавления измерения
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Measurement addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO) {
        // Получаем сенсор из базы данных по имени
        Sensor sensor = sensorService.getSensorByName(measurementDTO.getSensor().getName())
                .orElseThrow(() -> new RuntimeException("Sensor not found"));

        // Создаем объект Measurement без времени
        Measurement measurement = Measurement.builder()
                .value(measurementDTO.getValue())
                .raining(measurementDTO.isRaining())
                .sensor(sensor)
                .build();

        // Сохраняем измерение в базе данных
        return measurementService.saveMeasurement(measurement);
    }

    // Получение всех измерений
    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementService.getAllMeasurements();
    }

    // Подсчет количества дождливых дней
    @GetMapping("/rainyDaysCount")
    public long getRainyDaysCount() {
        return measurementService.countRainyDays();
    }
}
