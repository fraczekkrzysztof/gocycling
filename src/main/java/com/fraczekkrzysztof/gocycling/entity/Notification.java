package com.fraczekkrzysztof.gocycling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "not_id")
    private long id;

    @Column(name = "not_userUid", updatable = false)
    private String userUid;

    @Column(name = "not_title", updatable = false)
    private String title;

    @Column(name = "not_content", updatable = false)
    private String content;

    @Column(name = "not_created", updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "not_read")
    private boolean read = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "not_event_id")
    private Event event;
}
