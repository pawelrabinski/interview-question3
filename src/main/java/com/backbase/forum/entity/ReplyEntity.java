package com.backbase.forum.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FORUM_REPLIES")
public class ReplyEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    @ToString.Exclude
    private QuestionEntity question;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "MESSAGE", nullable = false)
    private String message;
}
