package com.fraczekkrzysztof.gocycling.dao;

import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "confirmations")
@Transactional
public interface ConfirmationRepository extends JpaRepository<Confirmation,Long> {
    //if you want to pass existing event then pas as "event":"/api/events/{id}"
}
