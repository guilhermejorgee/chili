package com.gui.chili.services;


import com.gui.chili.dtos.CustomerDto;
import com.gui.chili.dtos.CustomerInsertDto;
import com.gui.chili.entities.Customer;
import com.gui.chili.events.InsertCustomerNewsletterEvent;
import com.gui.chili.events.RemoveCustomerNewsletterEvent;
import com.gui.chili.exceptions.DuplicateEntityException;
import com.gui.chili.exceptions.EntityNotFoundException;
import com.gui.chili.repositories.CustomerRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public CustomerServiceImpl(CustomerRepository repository, ApplicationEventPublisher eventPublisher){
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Page<CustomerDto> list(PageRequest request) {
        return repository.findAll(request).map(CustomerDto::new);
    }

    @Override
    public CustomerDto getById(String id) {
        Customer entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new CustomerDto(entity);
    }

    @Override
    public CustomerDto getByEmail(String email) {
        Customer entity = repository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return new CustomerDto(entity);
    }

    @Override
    public CustomerDto post(CustomerInsertDto customerInsertDto) {
        checkEmailIsAlreadyRegistered(customerInsertDto.getEmail());
        Customer entity = Customer.builder()
                .name(customerInsertDto.getName())
                .email(customerInsertDto.getEmail())
                .notificationEmail(customerInsertDto.getNotificationEmail())
                .build();
        repository.insert(entity);
        submitNotification(entity);
        return new CustomerDto(entity);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Customer entity = repository.findById(customerDto.getId()).orElseThrow(EntityNotFoundException::new);
        checkEmailIsAlreadyRegistered(customerDto.getEmail());
        entity.update(customerDto);
        repository.insert(entity);
        submitNotification(entity);
        return new CustomerDto(entity);
    }

    @Override
    public void delete(String id) {
        Customer entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(entity);
        eventPublisher.publishEvent(new RemoveCustomerNewsletterEvent(entity.getEmail()));
    }

    private void submitNotification(Customer customer){
        if(customer.getNotificationEmail()){
            eventPublisher.publishEvent(new InsertCustomerNewsletterEvent(customer.getEmail()));
        }
    }

    private void checkEmailIsAlreadyRegistered(String email){
       Optional<Customer> customer = repository.findByEmail(email);
       if(customer.isPresent()){
           throw new DuplicateEntityException();
       }
    }
}
