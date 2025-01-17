package com.poolup.poolup.shared.domain;

import com.poolup.poolup.common.domain.BaseEntity;
import com.poolup.poolup.interview.domain.Interview;
import com.poolup.poolup.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberInterview extends BaseEntity {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String answer;
    private String strength;
    private String weakness;

    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne @JoinColumn(name = "interview_id")
    private Interview interview;

}
