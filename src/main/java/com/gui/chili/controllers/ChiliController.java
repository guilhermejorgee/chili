package com.gui.chili.controllers;

import com.gui.chili.dtos.ChiliDto;
import com.gui.chili.dtos.ChiliInsertDto;
import com.gui.chili.services.ChiliService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/chilis")
public class ChiliController {

    private final ChiliService service;

    public ChiliController(ChiliService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ChiliDto>> list(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ){
        return ResponseEntity.ok(service.list(PageRequest.of(page, linesPerPage, direction, orderBy)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiliDto> getById(@PathVariable String id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ChiliDto> getByName(@PathVariable String name){
        return ResponseEntity.ok(service.getByName(name));
    }

    @PostMapping
    public ResponseEntity<ChiliDto> post(@Valid @RequestBody ChiliInsertDto chiliInsertDto){
        ChiliDto chiliDto = service.post(chiliInsertDto);
        URI uri = URI.create(
                ServletUriComponentsBuilder.
                fromCurrentContextPath().
                path("/chilis/{id}").
                buildAndExpand(chiliDto.getId()).
                toUriString());
        return ResponseEntity.created(uri).body(chiliDto);
    }

    @PutMapping
    public ResponseEntity<ChiliDto> update(@Valid @RequestBody ChiliDto chiliDto){
        return ResponseEntity.ok(service.update(chiliDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ChiliDto> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
