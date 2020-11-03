package com.fraczekkrzysztof.gocycling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "club")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cl_id")
    long id;

    @Column(name = "cl_name", nullable = false)
    String name;

    @Column(name = "cl_location", nullable = false)
    String location;

    @Column(name = "cl_latitude", nullable = false)
    private double latitude;

    @Column(name = "cl_longitude", nullable = false)
    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cl_owner_id")
    private User user;

    @Column(name = "cl_created", nullable = false, columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "cl_details")
    private String details;

    @Column(name = "cl_private_mode")
    private boolean privateMode = false;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Event> eventList;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "mem_cl_id")
    private List<Member> memberList;

}
