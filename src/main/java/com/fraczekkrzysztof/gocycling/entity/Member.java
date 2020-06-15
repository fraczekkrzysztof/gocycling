package com.fraczekkrzysztof.gocycling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mem_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mem_cl_id", nullable = false)
    private Club club;

    @Column(name="mem_user_uid", nullable = false)
    private String userUid;

    @Column(name="mem_confirmed")
    private boolean confirmed;

}
