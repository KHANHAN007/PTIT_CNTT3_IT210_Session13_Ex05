package com.session13.ex05.repository;

import com.session13.ex05.model.entity.Medication;
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
public class MedicationRepository {
    @Autowired
    private EntityManager entityManager;

    public Medication save(Medication medication) {
        Session session = entityManager.unwrap(Session.class);
        if (medication.getId() == null) {
            session.persist(medication);
        } else {
            session.merge(medication);
        }
        return medication;
    }

    public Optional<Medication> findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Medication medication = session.get(Medication.class, id);
        return Optional.ofNullable(medication);
    }

    public List<Medication> findByPrescriptionId(Long prescriptionId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Medication> query = session.createQuery(
            "FROM Medication WHERE prescription.id = :prescriptionId ORDER BY id",
            Medication.class
        );
        query.setParameter("prescriptionId", prescriptionId);
        return query.getResultList();
    }

    public void delete(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Medication medication = session.get(Medication.class, id);
        if (medication != null) {
            session.remove(medication);
        }
    }
}

