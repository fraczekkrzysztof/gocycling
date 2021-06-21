package com.fraczekkrzysztof.gocycling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    @Column(name = "us_id")
    @Id
    private String id;
    @Column(name = "us_name")
    private String name;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "ext_int_user_id")
    private List<UserExternalApp> externalAppList;

}
