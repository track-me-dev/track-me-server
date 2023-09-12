package com.app.trackme.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
public class TestUtils {

    @Autowired
    private EntityManager em;

    @Transactional
    public void rollback() {
        em.createNativeQuery("DELETE FROM LOCATION_FOR_TRACK")
                .executeUpdate();
        em.createNativeQuery("DELETE FROM LOCATION_FOR_TRACK_RECORD")
                .executeUpdate();
        em.createQuery("DELETE FROM Track t")
                .executeUpdate();
    }
}
