package org.bcliu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String code;
}
