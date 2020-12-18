package com.contacttura.contacttura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contacttura.contacttura.model.Contactura;

@Repository
public interface ContacturaRepository extends JpaRepository<Contactura, Long> {

}
