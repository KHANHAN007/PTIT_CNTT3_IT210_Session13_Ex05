package com.session13.ex05.repository;

import com.session13.ex05.model.entity.Prescription;
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
public class PrescriptionRepository {

    @Autowired
    private EntityManager entityManager;

    public Prescription save(Prescription prescription) {
        Session session = entityManager.unwrap(Session.class);
        if (prescription.getId() == null) {
            session.persist(prescription);
        } else {
            session.merge(prescription);
        }
        return prescription;
    }

    public Optional<Prescription> findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Prescription prescription = session.get(Prescription.class, id);
        return Optional.ofNullable(prescription);
    }

    public List<Prescription> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Prescription> query = session.createQuery(
            "FROM Prescription p JOIN FETCH p.patient ORDER BY p.id DESC",
            Prescription.class
        );
        return query.getResultList();
    }

    public List<Prescription> findByPatientCode(String patientCode) {
        Session session = entityManager.unwrap(Session.class);
        Query<Prescription> query = session.createQuery(
            "FROM Prescription p JOIN FETCH p.patient WHERE p.patient.code = :code ORDER BY p.id DESC",
            Prescription.class
        );
        query.setParameter("code", patientCode);
        return query.getResultList();
    }

    public List<Prescription> findByPatientId(Long patientId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Prescription> query = session.createQuery(
            "FROM Prescription p JOIN FETCH p.patient WHERE p.patient.id = :patientId ORDER BY p.id DESC",
            Prescription.class
        );
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    public void delete(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Prescription prescription = session.get(Prescription.class, id);
        if (prescription != null) {
            session.remove(prescription);
        }
    }
}

