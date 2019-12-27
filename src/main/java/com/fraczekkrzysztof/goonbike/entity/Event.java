package com.fraczekkrzysztof.goonbike.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false, columnDefinition = "TIMESTAMP ")
    private LocalDateTime dateAndTime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    public Event() {
    }

    public Event(long id, String name, String place, LocalDateTime dateAndTime, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.dateAndTime = dateAndTime;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public static class Builder {

        private long id;
        private String name;
        private String place;
        private LocalDateTime dateAndTime;
        private LocalDateTime created;

        public Builder(){
            //get new Builder instance
        }

        public Builder setId(long id){
            this.id = id;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setPlace(String place){
            this.place = place;
            return this;
        }

        public Builder setDateAndTime(LocalDateTime dateAndTime){
            this.dateAndTime = dateAndTime;
            return this;
        }

        public Builder setCreated(LocalDateTime created){
            this.created = created;
            return this;
        }

        public Event build(){
            return new Event(id,name,place,dateAndTime,created);
        }

    }
}
