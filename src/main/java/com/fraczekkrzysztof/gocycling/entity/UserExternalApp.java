package com.fraczekkrzysztof.gocycling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_external_app")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExternalApp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ext_id")
    Long id;

    @Column(name = "ext_app_type")
    @Enumerated(EnumType.STRING)
    ExternalApps appType;

    @Column(name="ext_access_token")
    String accessToken;

    @Column(name="ext_refresh_token")
    String refreshToken;

    @Column(name="ext_expires_at")
    Long expiresAt;

    @Column(name="ext_app_user_id")
    Long appUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ext_int_user_id")
    User user;

}
