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

    @Column(name = "not_type", updatable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "not_cl_id", updatable = false)
    private long clubId;

    @Column(name = "not_ev_id", updatable = false)
    private long eventId;
}
