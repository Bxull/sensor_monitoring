package kz.beksultan.springsensor.service;

import kz.beksultan.springsensor.model.Measurement;
import kz.beksultan.springsensor.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    // Сохранение измерения
    public Measurement saveMeasurement(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    // Получение всех измерений
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    // Подсчет дождливых дней
    public long countRainyDays() {
        return measurementRepository.countByRainingTrue();
    }
}
