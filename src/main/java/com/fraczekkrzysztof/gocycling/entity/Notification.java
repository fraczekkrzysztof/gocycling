package com.fraczekkrzysztof.gocycling.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "not_id")
    private long id;

    @Column(name = "not_userUid", updatable = false)
    private String userUid;

    @Column(name = "not_title", updatable = false)
    private String title;

    @Column(name = "not_content", updatable = false)
    private String content;

    @Column(name = "not_created", updatable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "not_read")
    private boolean read = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "not_event_id")
    private Event event;

    public Notification() {
    }

    public Notification(long id, String userUid, String title, String content, LocalDateTime created, boolean read, Event event) {
        this.id = id;
        this.userUid = userUid;
        this.title = title;
        this.content = content;
        this.created = created;
        this.read = read;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
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
        private String title;
        private String content;
        private LocalDateTime created;
        private boolean read;
        private Event event;

        public Builder(){
            //get Builder instance
        }

        public Builder setId(long id){
            this.id = id;
            return this;
        }

        public Builder setUserUid(String userUid){
            this.userUid = userUid;
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setContent(String content){
            this.content = content;
            return this;
        }

        public Builder setCreated(LocalDateTime created){
            this.created=created;
            return this;
        }

        public Builder setRead(boolean read){
            this.read = read;
            return this;
        }

        public Builder setEvent(Event event){
            this.event = event;
            return this;
        }

        public Notification build(){
            Notification toReturn = new Notification();
            toReturn.setId(this.id);
            toReturn.setUserUid(this.userUid);
            toReturn.setTitle(this.title);
            toReturn.setContent(this.content);
            toReturn.setCreated(this.created);
            toReturn.setRead(this.read);
            toReturn.setEvent(this.event);
            return toReturn;
        }
    }
}
