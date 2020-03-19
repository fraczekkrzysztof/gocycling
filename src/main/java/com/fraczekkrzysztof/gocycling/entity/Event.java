package com.fraczekkrzysztof.gocycling.entity;



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

    @Column (name = "ev_details", columnDefinition = "text")
    private String details;

    @Column(name = "ev_created_by", nullable = false)
    private String createdBy;

    @Column(name = "ev_canceled")
    private boolean canceled = false;

    @OneToMany(mappedBy = "event", cascade = {CascadeType.ALL})
    private List<Confirmation> confirmationList;

    public Event() {
    }

    public Event(long id, String name, String place, LocalDateTime dateAndTime, LocalDateTime created, String details, String createdBy, boolean canceled, List<Confirmation> confirmationList) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.dateAndTime = dateAndTime;
        this.created = created;
        this.details = details;
        this.createdBy = createdBy;
        this.canceled = canceled;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Confirmation> getConfirmationList() {
        return confirmationList;
    }

    public void setConfirmationList(List<Confirmation> confirmationList) {
        this.confirmationList = confirmationList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public static class Builder {

        private long id;
        private String name;
        private String place;
        private LocalDateTime dateAndTime;
        private LocalDateTime created;
        private String details;
        private String createdBy;
        private boolean canceled;
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

        public Builder setDetails(String details){
            this.details = details;
            return this;
        }
        public Builder setCreatedBy(String createdBy){
            this.createdBy = createdBy;
            return this;
        }

        public Builder setCanceled(boolean canceled){
            this.canceled = canceled;
            return this;
        }

        public Event build(){
            Event newEvent = new Event();
            newEvent.setId(id);
            newEvent.setName(name);
            newEvent.setPlace(place);
            newEvent.setDateAndTime(dateAndTime);
            newEvent.setCreated(created);
            newEvent.setDetails(details);
            newEvent.setConfirmationList(confirmationList);
            newEvent.setCreatedBy(createdBy);
            newEvent.setCanceled(canceled);
            return newEvent;
        }

    }
}
