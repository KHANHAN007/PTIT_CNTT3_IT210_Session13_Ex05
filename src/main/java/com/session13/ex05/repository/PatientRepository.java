package com.session13.ex05.repository;

import com.session13.ex05.model.entity.Patient;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PatientRepository {

    @Autowired
    private EntityManager entityManager;

    public Patient save(Patient patient) {
        Session session = entityManager.unwrap(Session.class);
        if (patient.getId() == null) {
            session.persist(patient);
        } else {
            session.merge(patient);
        }
        return patient;
    }

    public Optional<Patient> findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Patient patient = session.get(Patient.class, id);
        return Optional.ofNullable(patient);
    }

    public Optional<Patient> findByCode(String code) {
        Session session = entityManager.unwrap(Session.class);
        Query<Patient> query = session.createQuery("FROM Patient WHERE code = :code", Patient.class);
        query.setParameter("code", code);
        return query.uniqueResultOptional();
    }

    public List<Patient> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Patient> query = session.createQuery("FROM Patient ORDER BY id DESC", Patient.class);
        return query.getResultList();
    }

    public void delete(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Patient patient = session.get(Patient.class, id);
        if (patient != null) {
            session.remove(patient);
        }
    }
}

