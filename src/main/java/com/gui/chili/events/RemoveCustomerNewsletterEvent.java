package com.gui.chili.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveCustomerNewsletterEvent {
    private String email;
}
