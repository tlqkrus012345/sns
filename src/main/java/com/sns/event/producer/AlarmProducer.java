package com.sns.event.producer;

import com.sns.event.dto.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmProducer {
    private final KafkaTemplate<Long, AlarmEvent> kafkaTemplate;
    public void send(AlarmEvent alarmEvent) {
        kafkaTemplate.send("alarm",alarmEvent.getReceiveMemberId(), alarmEvent);
        log.info("Send to kafka");
    }
}
