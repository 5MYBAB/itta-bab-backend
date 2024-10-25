package com.fivemybab.ittabab.schedule.command.application.service;

import com.fivemybab.ittabab.exception.NotFoundException;
import com.fivemybab.ittabab.schedule.command.application.dto.ScheduleCreateRequire;
import com.fivemybab.ittabab.schedule.command.application.dto.ScheduleDto;
import com.fivemybab.ittabab.schedule.command.application.dto.ScheduleModifyRequest;
import com.fivemybab.ittabab.schedule.command.domain.aggregate.ScheduleInfo;
import com.fivemybab.ittabab.schedule.command.domain.repository.ScheduleRepository;
import com.fivemybab.ittabab.user.command.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    /* 일정 추가 */
    @Transactional
    public void registSchedule(ScheduleCreateRequire scheduleCreateRequire, Long userId) {
        ScheduleInfo scheduleInfo = ScheduleInfo.builder()
                .scheduleDate(scheduleCreateRequire.getScheduleDate())
                .scheduleTitle(scheduleCreateRequire.getScheduleTitle())
                .scheduleContent(scheduleCreateRequire.getScheduleContent())
                .userId(userId) // userId 설정
                .build();

        scheduleRepository.save(scheduleInfo);
    }

    /* 일정 수정 */
    @Transactional
    public void modifySchedule(Long scheduleId, ScheduleModifyRequest scheduleModifyRequest) {
        ScheduleInfo foundSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("일정을 찾을 수 없습니다."));

        foundSchedule.modifyScheduleContent(scheduleModifyRequest.getScheduleContent());
        foundSchedule.modifyScheduleTitle(scheduleModifyRequest.getScheduleTitle());
        foundSchedule.modifyScheduleDate(scheduleModifyRequest.getScheduleDate());

        scheduleRepository.save(foundSchedule);
    }

    /* 일정 삭제 */
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new NotFoundException("삭제하려는 일정이 존재하지 않습니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }

    public Long getScheduleById(Long scheduleId) {
        ScheduleInfo schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("일정을 찾을 수 없습니다."));
        return schedule.getScheduleId();
    }

    public Long getScheduleByUserId(Long scheduleId) {

        ScheduleInfo schedule = scheduleRepository.findByScheduleId(scheduleId);

        if (schedule == null) {
            throw new IllegalArgumentException("찾는 일정이 없습니다 : " + scheduleId);
        }

        return schedule.getUserId();
    }
}
