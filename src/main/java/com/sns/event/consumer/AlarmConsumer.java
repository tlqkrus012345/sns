package com.sns.event.consumer;

import com.sns.event.dto.AlarmEvent;
import com.sns.member.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {
    private final AlarmService alarmService;
    @KafkaListener(topics = "alarm")
    public void consumeAlarm(AlarmEvent alarmEvent, Acknowledgment ack) {
        log.info("Consume the event {}", alarmEvent);
        alarmService.send(alarmEvent.getAlarmType(), alarmEvent.getAlarmArguments(), alarmEvent.getReceiveMemberId());
        ack.acknowledge();
    }
}
