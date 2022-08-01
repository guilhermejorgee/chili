package com.gui.chili.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.gui.chili.entities.Chili;
import com.gui.chili.entities.Customer;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerInsertDto {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotNull
    private Boolean notificationEmail;
}
