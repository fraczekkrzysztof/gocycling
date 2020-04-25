package com.fraczekkrzysztof.gocycling.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_external_app")
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

    @ManyToOne()
    @JoinColumn(name = "ext_int_user_id")
    User user;

    public UserExternalApp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExternalApps getAppType() {
        return appType;
    }

    public void setAppType(ExternalApps appType) {
        this.appType = appType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }


}
