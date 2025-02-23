package com.alejandronieves.repository;

import com.alejandronieves.model.SubUsoAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubUsoAulaRepository extends JpaRepository<SubUsoAula, Long> {
}
