package org.dzvo.bilanzierungsgebiete.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GebietRepository extends JpaRepository<Gebiet, Long> {
    Gebiet findById(long id);
}
