package com.alejandronieves.repository;

import com.alejandronieves.model.FamCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamCursoRepository extends JpaRepository<FamCurso, Long> {
}
