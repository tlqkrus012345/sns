package com.sns.member.entity;
import com.sns.member.entity.AlarmArguments;
import com.sns.common.BaseTimeEntity;
import com.sns.post.entity.Post;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
@TypeDef(
        name = "json", typeClass = JsonType.class
)
@Getter
@NoArgsConstructor
@Entity
public class Alarm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;
    @Type(type ="json")
    @Column(columnDefinition = "json")
    private AlarmArguments alarmArguments;
    private Alarm(Member member, AlarmType alarmType, AlarmArguments alarmArgs) {
        this.member = member;
        this.alarmType = alarmType;
        this.alarmArguments = alarmArgs;
    }
    public static Alarm of(Member member, AlarmType alarmType, AlarmArguments alarmArgs) {
        return new Alarm(member, alarmType, alarmArgs);
    }
}
