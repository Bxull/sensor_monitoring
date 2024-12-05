package kz.beksultan.springsensor.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import kz.beksultan.springsensor.dto.MeasurementDTO;
import kz.beksultan.springsensor.dto.SensorDTO;
import kz.beksultan.springsensor.model.Measurement;
import kz.beksultan.springsensor.model.Sensor;
import kz.beksultan.springsensor.service.MeasurementService;
import kz.beksultan.springsensor.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@WebMvcTest(MeasurementController.class)
class MeasurementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MeasurementService measurementService;

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private MeasurementController measurementController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(measurementController).build();
    }

    @Test
    void testAddMeasurement() throws Exception {
        // Arrange
        SensorDTO sensorDTO = new SensorDTO("Test2");
        MeasurementDTO measurementDTO = new MeasurementDTO(14.5, true, sensorDTO);
        Sensor sensor = new Sensor(1L, "Test");
        Measurement measurement = new Measurement(1L, true, sensor, 23.5);

        when(sensorService.getSensorByName(measurementDTO.getSensor().getName()))
                .thenReturn(java.util.Optional.of(sensor));
        when(measurementService.saveMeasurement(any(Measurement.class))).thenReturn(measurement);

        // Act & Assert
        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":14.5, \"raining\": true, \"sensor\": {\"name\": \"Test\"}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value").value(23.5))
                .andExpect(jsonPath("$.raining").value(true))
                .andExpect(jsonPath("$.sensor.name").value("Test"));

        verify(measurementService, times(1)).saveMeasurement(any(Measurement.class));
        verify(sensorService, times(1)).getSensorByName(measurementDTO.getSensor().getName());
    }
    @Test
    void testGetAllMeasurements() throws Exception {
        Measurement measurement1 = new Measurement(1L, true, new Sensor(1L, "TestSensor"), 23.5);
        Measurement measurement2 = new Measurement(2L, false, new Sensor(2L, "TestSensor2"), 18.0);

        when(measurementService.getAllMeasurements()).thenReturn(java.util.List.of(measurement1, measurement2));

        mockMvc.perform(get("/measurements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].value").value(23.5))
                .andExpect(jsonPath("$[1].value").value(18.0));
    }
    @Test
    void testGetRainyDaysCount() throws Exception {
        when(measurementService.countRainyDays()).thenReturn(5L);

        mockMvc.perform(get("/measurements/rainyDaysCount"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(measurementService).countRainyDays();
    }
}
