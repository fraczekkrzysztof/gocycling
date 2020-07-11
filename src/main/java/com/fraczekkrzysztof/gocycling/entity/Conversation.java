package com.fraczekkrzysztof.gocycling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conversation {

    @Id
    @Column(name = "cov_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cov_useruid")
    private String userUid;

    @Column(name = "cov_username")
    private String username;

    @Column(name = "cov_created", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "cov_message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cov_ev_id")
    private Event event;


}
