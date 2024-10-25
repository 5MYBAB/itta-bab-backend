package com.fivemybab.ittabab.schedule.command.application.controller;

import com.fivemybab.ittabab.schedule.command.application.dto.ScheduleCreateRequire;
import com.fivemybab.ittabab.schedule.command.application.dto.ScheduleDto;
import com.fivemybab.ittabab.schedule.command.application.dto.ScheduleModifyRequest;
import com.fivemybab.ittabab.schedule.command.application.service.ScheduleCommandService;
import com.fivemybab.ittabab.security.util.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Tag(name = "Schedule", description = "일정 관련 API")
public class ScheduleCommandController {

    private final ScheduleCommandService scheduleCommandService;

    /* 일정 입력 */
    @Operation(summary = "일정 등록")
    @PostMapping
    public ResponseEntity<String> registSchedule(@RequestBody ScheduleCreateRequire scheduleCreateRequire, @AuthenticationPrincipal CustomUserDetails loginUser){
        Long userId = loginUser.getUserId();
        scheduleCommandService.registSchedule(scheduleCreateRequire, userId);
        return new ResponseEntity<>("등록 완료", HttpStatus.OK);
    }

    /* 일정 수정 */
    @Operation(summary = "일정 수정")
    @PutMapping
    public ResponseEntity<String> modifySchedule(@RequestBody ScheduleModifyRequest scheduleModifyRequest, @AuthenticationPrincipal CustomUserDetails loginUser){
        Long userId = loginUser.getUserId();
        Long scheduleDtoUserId = scheduleCommandService.getScheduleByUserId(scheduleModifyRequest.getScheduleId());
        if(!userId.equals(scheduleDtoUserId)){
            return new ResponseEntity<>("작성자가 아닙니다.", HttpStatus.OK);
        }
        scheduleModifyRequest.setScheduleDate(LocalDate.now());
        scheduleCommandService.modifySchedule(scheduleModifyRequest);
        return new ResponseEntity<>("수정 완료"+scheduleModifyRequest, HttpStatus.OK);
    }

    /* 일정 삭제 */
    @Operation(summary = "일정 삭제")
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("scheduleId") Long scheduleId, @AuthenticationPrincipal CustomUserDetails loginUser) {
        Long userId = loginUser.getUserId();
        Long scheduleUserId = scheduleCommandService.getScheduleByUserId(scheduleId);
        if(!userId.equals(scheduleUserId)){
            return new ResponseEntity<>("작성자만 삭제가능합니다.", HttpStatus.OK);
        }
        scheduleCommandService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
