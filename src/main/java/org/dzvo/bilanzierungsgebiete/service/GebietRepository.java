package org.dzvo.bilanzierungsgebiete.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GebietRepository extends JpaRepository<Gebiet, Long> {
    //List<Gebiet> findByLastName(String lastName);
    Gebiet findById(long id);

    List<Gebiet> findByStromnetzbetreiber(String rz_EIC);
}
