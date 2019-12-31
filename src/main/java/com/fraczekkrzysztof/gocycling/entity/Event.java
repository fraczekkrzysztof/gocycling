package com.fraczekkrzysztof.gocycling.entity;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ev_id")
    private long id;

    @Column(name = "ev_name", nullable = false)
    private String name;

    @Column(name = "ev_place", nullable = false)
    private String place;

    @Column(name = "ev_dateAndTime", nullable = false, columnDefinition = "TIMESTAMP ")
    private LocalDateTime dateAndTime;

    @Column(name = "ev_created", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    @OneToMany(mappedBy = "event", cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<Confirmation> confirmationList;

    public Event() {
    }

    public Event(long id, String name, String place, LocalDateTime dateAndTime, LocalDateTime created, List<Confirmation> confirmationList) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.dateAndTime = dateAndTime;
        this.created = created;
        this.confirmationList = confirmationList;
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

    public List<Confirmation> getConfirmationList() {
        return confirmationList;
    }

    public void setConfirmationList(List<Confirmation> confirmationList) {
        this.confirmationList = confirmationList;
    }

    public static class Builder {

        private long id;
        private String name;
        private String place;
        private LocalDateTime dateAndTime;
        private LocalDateTime created;
        private List<Confirmation> confirmationList;

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

        public Builder setConfirmationList(List<Confirmation> confirmationList){
            this.confirmationList = confirmationList;
            return this;
        }

        public Event build(){
            Event newEvent = new Event();
            newEvent.setId(id);
            newEvent.setName(name);
            newEvent.setPlace(place);
            newEvent.setDateAndTime(dateAndTime);
            newEvent.setCreated(created);
            newEvent.setConfirmationList(confirmationList);
            return newEvent;
        }

    }
}
