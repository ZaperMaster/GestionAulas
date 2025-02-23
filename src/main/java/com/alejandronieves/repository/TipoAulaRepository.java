package com.alejandronieves.repository;

import com.alejandronieves.model.TipoAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAulaRepository extends JpaRepository<TipoAula, Long> {
}
