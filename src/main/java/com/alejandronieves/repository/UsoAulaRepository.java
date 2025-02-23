package com.alejandronieves.repository;

import com.alejandronieves.model.UsoAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsoAulaRepository extends JpaRepository<UsoAula, Long> {
}
