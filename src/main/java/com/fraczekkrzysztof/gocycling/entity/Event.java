package com.fraczekkrzysztof.gocycling.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ev_id")
    private long id;

    @Column(name = "ev_name", nullable = false)
    private String name;

    @Column(name = "ev_place", nullable = false)
    private String place;

    @Column(name = "ev_latitude", nullable = false)
    private double latitude;

    @Column (name = "ev_longitude", nullable = false)
    private double longitude;

    @Column(name = "ev_dateAndTime", nullable = false, columnDefinition = "TIMESTAMP ")
    private LocalDateTime dateAndTime;

    @Column(name = "ev_created", nullable = false, columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column (name = "ev_details", columnDefinition = "text")
    private String details;

    @Column(name = "ev_created_by", nullable = false)
    private String createdBy;

    @Column(name = "ev_canceled")
    private boolean canceled = false;

    @Column(name ="ev_route_link")
    private String routeLink;

    @OneToMany(mappedBy = "event", cascade = {CascadeType.ALL})
    private List<Confirmation> confirmationList;


}
