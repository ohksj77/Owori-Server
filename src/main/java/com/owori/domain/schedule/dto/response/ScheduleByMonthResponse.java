package com.owori.domain.schedule.dto.response;

import com.owori.domain.member.entity.Color;
import com.owori.domain.schedule.entity.Alarm;
import com.owori.domain.schedule.entity.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleByMonthResponse {
    private UUID scheduleId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private ScheduleType scheduleType;
    private String nickname;
    private Color color;
    private Boolean ddayOption;
    private List<Alarm> alarmOptions;
}
