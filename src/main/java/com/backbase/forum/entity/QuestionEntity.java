package com.backbase.forum.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FORUM_QUESTIONS")
public class QuestionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL)
    private List<ReplyEntity> replies;
}
