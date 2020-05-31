package com.fraczekkrzysztof.gocycling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mem_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="mem_cl_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name="mem_us_id")
    private User user;

    @Column(name="mem_confirmed")
    private boolean confirmed;

}
