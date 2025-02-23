package com.alejandronieves.model;

import javax.persistence.*;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcurso")
    private Long id;

    private String descripcion;
    private Boolean activo;

    // Relación ManyToOne con FamCurso
    @ManyToOne(optional = false)
    @JoinColumn(name = "FAMCURSOidfamcurso", nullable = false)
    private FamCurso famCurso;

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public FamCurso getFamCurso() {
        return famCurso;
    }
    public void setFamCurso(FamCurso famCurso) {
        this.famCurso = famCurso;
    }
}
