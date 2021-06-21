package com.fraczekkrzysztof.gocycling.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "confirmation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Confirmation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "con_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "con_user_id")
    private User user;
}
