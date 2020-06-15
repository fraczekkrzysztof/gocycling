package com.fraczekkrzysztof.gocycling.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "confirmation",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"con_user_uid","con_ev_id"})}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Confirmation implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "con_id")
    private long id;

    @Column(name = "con_user_uid", nullable = false)
    private String userUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "con_ev_id")
    private Event event;

}
