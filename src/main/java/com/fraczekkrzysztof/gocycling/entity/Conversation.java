package com.fraczekkrzysztof.gocycling.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @Column(name = "cov_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cov_useruid")
    private String userUid;

    @Column(name = "cov_username")
    private String username;

    @Column(name = "cov_created", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "cov_message")
    private String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cov_ev_id")
    private Event event;

    public Conversation() {
    }

    public Conversation(long id, String userUid, String username, LocalDateTime created, String message, Event event) {
        this.id = id;
        this.userUid = userUid;
        this.username = username;
        this.created = created;
        this.message = message;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public static class Builder{

        private long id;
        private String userUid;
        private String username;
        private LocalDateTime created = LocalDateTime.now();
        private String message;
        private Event event;

        public Builder(){
            //return new Builder instance
        }

        public Builder setId(long id){
            this.id=id;
            return this;
        }

        public Builder setUserUid(String userUid){
            this.userUid=userUid;
            return this;
        }

        public Builder setUsername(String username){
            this.username=username;
            return this;
        }

        public Builder setCreated(LocalDateTime created){
            this.created = created;
            return this;
        }

        public Builder setMessage(String message){
            this.message = message;
            return this;
        }

        public Builder setEvent(Event event){
            this.event = event;
            return this;
        }

        public Conversation build(){
            Conversation conversationToReturn = new Conversation();
            conversationToReturn.setId(this.id);
            conversationToReturn.setUserUid(this.userUid);
            conversationToReturn.setUsername(this.username);
            conversationToReturn.setCreated(this.created);
            conversationToReturn.setMessage(this.message);
            conversationToReturn.setEvent(this.event);
            return conversationToReturn;
        }

    }
}
