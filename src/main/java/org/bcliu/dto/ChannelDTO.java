package org.bcliu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class ChannelDTO {
    @NotEmpty
    private String name;
    @NotNull
    private Boolean isPublic;
}
