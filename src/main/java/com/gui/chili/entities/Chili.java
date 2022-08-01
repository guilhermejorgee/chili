package com.gui.chili.entities;

import com.gui.chili.dtos.ChiliDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@Data
@Document
public class Chili {
    @Id
    private String id;
    private String name;
    @Setter(AccessLevel.NONE)
    private Instant createdAt;
    @Setter(AccessLevel.NONE)
    private Instant updateAt;

    @Builder
    public Chili(String name){
        this.name = name;
        this.createdAt = Instant.now();
    }

    public void update(ChiliDto chiliDto) {
        this.name = chiliDto.getName();
        this.updateAt = Instant.now();
    }
}
