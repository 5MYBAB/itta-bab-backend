package com.fivemybab.ittabab.report.query.controller;

import com.fivemybab.ittabab.report.query.dto.ReportDto;
import com.fivemybab.ittabab.report.query.service.ReportQueryService;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
@Tag(name = "Report", description = "신고 관련 API")
public class ReportQueryController {

    private final ReportQueryService reportQueryService;

    // 모든 신고 조회
    @Operation(summary = "모든 신고 조회(관리자)")
    @GetMapping("/admin")
    public ResponseEntity<List<ReportDto>> getAllReports(){

        List<ReportDto> reportList =reportQueryService.findReportList();
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

    // 특정 유저가 한 신고 조회
    @Operation(summary = "특정 유저가 한 신고 조회")
    @GetMapping("/user")
    public ResponseEntity<List<ReportDto>> getAllMyReports(@AuthenticationPrincipal CustomUserDetails loginUser){

        Long userId = loginUser.getUserId();

        List<ReportDto> reportList =reportQueryService.findMyReportList(userId);
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

}
