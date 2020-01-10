package com.fraczekkrzysztof.gocycling.entity;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "confirmation",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"con_user_uid","con_ev_id"})}
)
public class Confirmation implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "con_id")
    private long id;

    @Column(name = "con_user_uid", nullable = false)
    private String userUid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "con_ev_id")
    private Event event;

    public Confirmation(){

    }

    public Confirmation(long id, String userUid, Event event) {
        this.id = id;
        this.userUid = userUid;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public static class Builder{

        private long id;
        private String userUid;
        private Event event;

        public Builder(){
            //return new Builder instance
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder serUserUid(String userUid){
            this.userUid = userUid;
            return this;
        }

        public Builder setEvent(Event event){
            this.event = event;
            return this;
        }

        public Confirmation build(){
            Confirmation newConfirmation = new Confirmation();
            newConfirmation.setId(id);
            newConfirmation.setUserUid(userUid);
            newConfirmation.setEvent(event);
            return newConfirmation;
        }

    }
}
