package com.fraczekkrzysztof.gocycling.entity.helper;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


/**
 * it's a view entity to show all events confirmed by user
 */
@Entity
@Immutable
@Subselect(value = "select e.ev_id, e.ev_name, e.ev_place, e.ev_date_and_time, e.ev_created, c.con_id, c.con_user_uid from public.event e join public.confirmation c on (e.ev_id=c.con_ev_id ) ")
@Synchronize({"Event","Confirmation"})
public class EventsConfirmedByUser {


    private long evId;
    private String evName;
    private String evPlace;
    private LocalDateTime evDateAndTime;
    private LocalDateTime evCreated;
    @Id
    private long conId;
    private String conUserUid;


    public EventsConfirmedByUser() {
    }

    public long getEvId() {
        return evId;
    }

    public void setEvId(long evId) {
        this.evId = evId;
    }

    public String getEvName() {
        return evName;
    }

    public void setEvName(String evName) {
        this.evName = evName;
    }

    public String getEvPlace() {
        return evPlace;
    }

    public void setEvPlace(String evPlace) {
        this.evPlace = evPlace;
    }

    public LocalDateTime getEvDateAndTime() {
        return evDateAndTime;
    }

    public void setEvDateAndTime(LocalDateTime evDateAndTime) {
        this.evDateAndTime = evDateAndTime;
    }

    public LocalDateTime getEvCreated() {
        return evCreated;
    }

    public void setEvCreated(LocalDateTime evCreated) {
        this.evCreated = evCreated;
    }

    public long getConId() {
        return conId;
    }

    public void setConId(long conId) {
        this.conId = conId;
    }

    public String getConUserUid() {
        return conUserUid;
    }

    public void setConUserUid(String conUserUid) {
        this.conUserUid = conUserUid;
    }
}
