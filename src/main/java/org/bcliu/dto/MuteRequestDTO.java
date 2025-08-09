package org.bcliu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MuteRequestDTO {
    @NotNull
    @Min(value = 60)
    private Integer durationInMinutes;
}
