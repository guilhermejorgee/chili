package com.gui.chili.services;

import com.gui.chili.dtos.ChiliDto;
import com.gui.chili.dtos.ChiliInsertDto;
import com.gui.chili.dtos.CustomerDto;
import com.gui.chili.dtos.CustomerInsertDto;
import com.gui.chili.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CustomerService {
    Page<CustomerDto> list(PageRequest request);
    CustomerDto getById(String id);
    CustomerDto getByEmail(String email);
    CustomerDto post(CustomerInsertDto customerInsertDto);
    CustomerDto update(CustomerDto customerDto);
    void delete(String id);
}
