package com.gui.chili.controllers;

import com.gui.chili.dtos.CustomerDto;
import com.gui.chili.dtos.CustomerInsertDto;
import com.gui.chili.repositories.CustomerRepository;
import com.gui.chili.services.CustomerService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> list(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
        @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) {
        return ResponseEntity.ok(service.list(PageRequest.of(page, linesPerPage, direction, orderBy)));
    }
        @GetMapping("/{id}")
        public ResponseEntity<CustomerDto> getById(@PathVariable String id){
            return ResponseEntity.ok(service.getById(id));
        }

        @GetMapping("/email/{email}")
        public ResponseEntity<CustomerDto> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(service.getByEmail(email));
    }

        @PostMapping
        public ResponseEntity<CustomerDto> post (@Valid @RequestBody CustomerInsertDto customerInsertDto){
            CustomerDto customerDto = service.post(customerInsertDto);
            URI uri = URI.create(
                ServletUriComponentsBuilder.
                fromCurrentContextPath().
                path("/customers/{id}").
                buildAndExpand(customerDto.getId()).
                toUriString());
            return ResponseEntity.created(uri).body(customerDto);
        }

        @PutMapping
        public ResponseEntity<CustomerDto> update (@Valid @RequestBody CustomerDto customerDto){
            return ResponseEntity.ok(service.update(customerDto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<CustomerDto> delete (@PathVariable String id){
            service.delete(id);
            return ResponseEntity.ok().build();
        }

    }
