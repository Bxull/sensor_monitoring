package kz.beksultan.springsensor.service;

import kz.beksultan.springsensor.model.Measurement;
import kz.beksultan.springsensor.model.Sensor;
import kz.beksultan.springsensor.repository.MeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class MeasurementServiceTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private MeasurementService measurementService;

    private Sensor testSensor;
    private Measurement testMeasurement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testSensor = new Sensor(1L, "Test");
        testMeasurement = new Measurement(1L, true, testSensor, 15.2);
    }

    @Test
    void testSaveMeasurement() {
        when(sensorService.getSensorByName("TestSensor")).thenReturn(Optional.of(testSensor));
        when(measurementRepository.save(testMeasurement)).thenReturn(testMeasurement);

        Measurement savedMeasurement = measurementService.saveMeasurement(testMeasurement);

        assertNotNull(savedMeasurement);
        assertEquals(savedMeasurement.getSensor(), testSensor);
        verify(measurementRepository, times(1)).save(testMeasurement);
    }

    @Test
    void testCountRainyDays() {
        when(measurementRepository.countByRainingTrue()).thenReturn(5L);

        long rainyDaysCount = measurementService.countRainyDays();

        assertEquals(5L, rainyDaysCount);
    }
}
