package com.gui.chili.services;

import com.gui.chili.dtos.ChiliDto;
import com.gui.chili.dtos.ChiliInsertDto;
import com.gui.chili.entities.Chili;
import com.gui.chili.events.PostChiliNewsletter;
import com.gui.chili.exceptions.DuplicateEntityException;
import com.gui.chili.exceptions.EntityNotFoundException;
import com.gui.chili.repositories.ChiliRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChiliServiceImpl implements ChiliService {

    private final ChiliRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public ChiliServiceImpl(ChiliRepository repository, ApplicationEventPublisher eventPublisher){
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Page<ChiliDto> list(PageRequest request) {
        return repository.findAll(request).map(ChiliDto::new);
    }

    @Override
    public ChiliDto getById(String id) {
        Chili entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ChiliDto(entity);
    }

    @Override
    public ChiliDto getByName(String name) {
        Chili entity = repository.findByName(name).orElseThrow(EntityNotFoundException::new);
        return new ChiliDto(entity);
    }

    @Override
    public ChiliDto post(ChiliInsertDto chiliInsertDto) {
        checkChiliIsAlreadyRegistered(chiliInsertDto.getName());
        Chili entity = Chili.builder().name(chiliInsertDto.getName()).build();
        repository.insert(entity);
        System.out.println(entity);
        eventPublisher.publishEvent(new PostChiliNewsletter(entity.getName()));
        return new ChiliDto(entity);
    }

    @Override
    public ChiliDto update(ChiliDto chiliDto) {
        Chili entity = repository.findById(chiliDto.getId()).orElseThrow(EntityNotFoundException::new);
        checkChiliIsAlreadyRegistered(chiliDto.getName());
        entity.update(chiliDto);
        repository.insert(entity);
        return new ChiliDto(entity);
    }

    @Override
    public void delete(String id) {
        Chili entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(entity);
    }

    private void checkChiliIsAlreadyRegistered(String name){
        Optional<Chili> chili = repository.findByName(name);
        if(chili.isPresent()){
            throw new DuplicateEntityException();
        }
    }
}
