package com.fraczekkrzysztof.gocycling.entity;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "confirmation")
public class Confirmation implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "con_id")
    private long id;

    @Column(name = " con_email",nullable = false)
    private String email;

    @Column(name = "con_name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "con_ev_id")
    private Event event;

    public Confirmation(){

    }

    public Confirmation(long id, String email, String name, Event event) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public static class Builder{

        private long id;
        private String email;
        private String name;
        private Event event;

        public Builder(){
            //return new Builder instance
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setEmail(String email){
            this.email = email;
            return this;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setEvent(Event event){
            this.event = event;
            return this;
        }

        public Confirmation build(){
            Confirmation newConfirmation = new Confirmation();
            newConfirmation.setId(id);
            newConfirmation.setEmail(email);
            newConfirmation.setName(name);
            newConfirmation.setEvent(event);
            return newConfirmation;
        }

    }
}
