package com.gui.chili.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.gui.chili.entities.Chili;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChiliDto {

    @NotNull
    private String id;
    private String name;

    public ChiliDto(Chili entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }

}
