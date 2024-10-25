package com.fivemybab.ittabab.inquiry.query.controller;

import com.fivemybab.ittabab.inquiry.query.dto.InquiryDto;
import com.fivemybab.ittabab.inquiry.query.service.InquiryQueryService;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
@Tag(name = "Inquiry", description = "문의 관련 API")
public class InquiryQueryController {

    private final InquiryQueryService inquiryQueryService;

    /* 전체 목록 */
    @Operation(summary = "문의 전체 목록(관리자)")
    @GetMapping("/admin")
    public ResponseEntity<List<InquiryDto>> findInquiryList() throws NotFoundException {
        List<InquiryDto> inquiryList = inquiryQueryService.findInquiryList();
        System.out.println("왔다감");
        System.out.println(inquiryList.get(0));
        return new ResponseEntity<>(inquiryList, HttpStatus.OK);
    }

    /* 문의 상세*/
    @Operation(summary = "문의 상세(관리자)")
    @GetMapping("/admin/{inquiryId}")
    public ResponseEntity<InquiryDto> findInquiryById (@PathVariable Long inquiryId){
        InquiryDto inquiryDto = inquiryQueryService.findInquiryListById(inquiryId);
        return new ResponseEntity<>(inquiryDto, HttpStatus.OK);
    }

    /* 문의 조회 (사용자)*/
    @Operation(summary = "문의 조회(사용자)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InquiryDto>> findInquiryListByUserId(@AuthenticationPrincipal CustomUserDetails loginUser) throws NotFoundException {
        List<InquiryDto> inquiryId = inquiryQueryService.findInquiryListByUserId(loginUser.getUserId());
        return new ResponseEntity<>(inquiryId,HttpStatus.OK);
    }
}
