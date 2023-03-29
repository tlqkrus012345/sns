package com.sns.member.dto.response;

import com.sns.member.dto.AlarmDto;
import com.sns.member.dto.MemberDto;
import com.sns.member.entity.Alarm;
import com.sns.member.entity.AlarmArguments;
import com.sns.member.entity.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmResponseDto {
    private Long id;
    private MemberResponseDto memberResponseDto;
    private AlarmType alarmType;
    private AlarmArguments alarmArguments;
    private String text;

    public static AlarmResponseDto from(AlarmDto alarm) {
        return new AlarmResponseDto(
                alarm.getId(),
                MemberResponseDto.from(alarm.getMemberDto()),
                alarm.getAlarmType(),
                alarm.getAlarmArguments(),
                alarm.getAlarmType().getAlarmText()
        );
    }
}
