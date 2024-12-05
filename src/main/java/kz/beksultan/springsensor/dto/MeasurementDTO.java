package kz.beksultan.springsensor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {

    @NotNull(message = "Temperature value cannot be null")
    private Double value;

    @NotNull(message = "Raining status cannot be null")
    private boolean raining;

    @NotNull(message = "Sensor must not be null")
    private SensorDTO sensor;


}
