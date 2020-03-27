package org.dzvo.bilanzierungsgebiete.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GebietRepository extends JpaRepository<Gebiet, Long> {
    Gebiet findById(long id);

    List<Gebiet> findByStromnetzbetreiber(String stromnetzbetreiber);

    List<Gebiet> findByBilanzierungsgebietEIC(String bilanzierungsgebietEIC);
}
