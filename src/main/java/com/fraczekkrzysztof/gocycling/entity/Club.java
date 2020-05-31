package com.fraczekkrzysztof.gocycling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="club")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cl_id")
    long id;

    @Column(name="cl_name", nullable = false)
    String name;

    @Column(name="cl_location", nullable = false)
    String location;

    @Column(name = "cl_latitude", nullable = false)
    private double latitude;

    @Column (name = "cl_longitude", nullable = false)
    private double longitude;

    @Column(name = "cl_owner", nullable = false)
    private String owner;

    @Column(name = "cl_details")
    private String details;

    @Column(name = "cl_private_mode")
    private boolean privateMode = false;

}
