package com.sns.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArguments {
    private Long fromMemberId;
    private Long hostId;
}
