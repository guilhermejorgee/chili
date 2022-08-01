package com.gui.chili.services;

import com.gui.chili.dtos.ChiliDto;
import com.gui.chili.dtos.ChiliInsertDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ChiliService {
    Page<ChiliDto> list(PageRequest request);
    ChiliDto getById(String id);
    ChiliDto getByName(String name);
    ChiliDto post(ChiliInsertDto chiliInsertDto);
    ChiliDto update(ChiliDto chiliDto);
    void delete(String id);
}
