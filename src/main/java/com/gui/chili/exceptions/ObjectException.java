package com.gui.chili.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ObjectException {
    private Instant timestamp;
    private Integer status;
    private String error;
}
