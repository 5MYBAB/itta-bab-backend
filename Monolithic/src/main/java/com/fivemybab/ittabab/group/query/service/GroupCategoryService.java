package com.fivemybab.ittabab.group.query.service;

import com.fivemybab.ittabab.group.query.mapper.GroupCategoryMapper;
import com.fivemybab.ittabab.group.query.dto.GroupCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupCategoryService {

    private final GroupCategoryMapper mapper;

    @Autowired
    public GroupCategoryService(GroupCategoryMapper mapper) {
        this.mapper = mapper;
    }

    public List<GroupCategoryDto> findGroupCategory() {
        List<GroupCategoryDto> groupCategoryDtos = new ArrayList<>();

        return mapper.findGroupCategory();
    }
}
