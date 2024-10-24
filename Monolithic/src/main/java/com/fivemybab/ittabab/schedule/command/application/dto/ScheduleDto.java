package com.fivemybab.ittabab.schedule.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ScheduleDto {
    private LocalDate scheduleDate;
    private String scheduleTitle;
    private String scheduleContent;
}
