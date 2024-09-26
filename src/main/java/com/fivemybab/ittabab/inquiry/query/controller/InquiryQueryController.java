package com.fivemybab.ittabab.inquiry.query.controller;

import com.fivemybab.ittabab.inquiry.query.dto.InquiryDto;
import com.fivemybab.ittabab.inquiry.query.service.InquiryQueryService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/inquiry")
public class InquiryQueryController {

    private final InquiryQueryService inquiryQueryService;

    public InquiryQueryController(InquiryQueryService inquiryQueryService) {
        this.inquiryQueryService = inquiryQueryService;
    }

    /* 전체 목록 */
    @GetMapping("/admin")
    public ResponseEntity<List<InquiryDto>> findInquiryList() throws NotFoundException {
        List<InquiryDto> inquiryList = inquiryQueryService.findInquiryList();
        return new ResponseEntity<>(inquiryList, HttpStatus.OK);
    }

    /* 문의 조회 (사용자)*/
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InquiryDto>> findInquiryListByUserId(@PathVariable Long userId) throws NotFoundException {
        List<InquiryDto> inquiryId = inquiryQueryService.findInquiryListByUserId(userId);
        return new ResponseEntity<>(inquiryId,HttpStatus.OK);
    }
}
