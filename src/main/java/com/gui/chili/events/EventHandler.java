package com.gui.chili.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @EventListener
    public void insertCustomerNewsletter(InsertCustomerNewsletterEvent event){

    }

    @EventListener
    public void removeCustomerNewsletter(RemoveCustomerNewsletterEvent event){

    }

    @EventListener
    public void postChiliNewsletter(PostChiliNewsletter event){

    }
}
