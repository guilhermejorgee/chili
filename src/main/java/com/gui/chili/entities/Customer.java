package com.gui.chili.entities;

import com.gui.chili.dtos.ChiliDto;
import com.gui.chili.dtos.CustomerDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@Data
@Document
public class Customer {

    @Id
    private String id;
    private String name;
    private String email;
    private Boolean notificationEmail;
    @Setter(AccessLevel.NONE)
    private Instant createdAt;
    @Setter(AccessLevel.NONE)
    private Instant updateAt;

    @Builder
    public Customer(String name, String email, Boolean notificationEmail){
        this.name = name;
        this.email = email;
        this.notificationEmail = notificationEmail;
        this.createdAt = Instant.now();
    }

    public void update(CustomerDto customerDto) {
        this.name = customerDto.getName();
        this.email = customerDto.getEmail();
        this.notificationEmail = customerDto.getNotificationEmail();
        this.updateAt = Instant.now();
    }

}
