package com.sns.member.dto;

import com.sns.member.entity.Alarm;
import com.sns.member.entity.AlarmArguments;
import com.sns.member.entity.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmDto {
    private Long id;
    private MemberDto memberDto;
    private AlarmType alarmType;
    private AlarmArguments alarmArguments;

    public static AlarmDto from(Alarm alarm) {
        return new AlarmDto(
                alarm.getId(),
                MemberDto.from(alarm.getMember()),
                alarm.getAlarmType(),
                alarm.getAlarmArguments()
        );
    }
}
