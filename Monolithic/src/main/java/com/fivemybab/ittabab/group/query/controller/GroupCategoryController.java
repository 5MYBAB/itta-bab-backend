package com.fivemybab.ittabab.group.query.controller;

import com.fivemybab.ittabab.group.query.dto.GroupCategoryDto;
import com.fivemybab.ittabab.group.query.service.GroupCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/groupCategory")
@Slf4j
@Tag(name = "Group", description = "모임 관련 API")
public class GroupCategoryController {

    private final GroupCategoryService service;

    @Autowired
    public GroupCategoryController(GroupCategoryService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<GroupCategoryDto>> findGroupCategory() {
        List<GroupCategoryDto> groupCategoryDtoList = service.findGroupCategory();

        return new ResponseEntity<>(groupCategoryDtoList, HttpStatus.OK);
    }
}
