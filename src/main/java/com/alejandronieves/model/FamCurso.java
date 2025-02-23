package com.alejandronieves.model;

import javax.persistence.*;

@Entity
@Table(name = "famcurso")
public class FamCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfamcurso")
    private Long id;

    private String descripcion;
    private Boolean activo;

    // Relación ManyToOne con SubUsoAula
    @ManyToOne(optional = false)
    @JoinColumn(name = "SUBUSOAULAidsubuso", nullable = false)
    private SubUsoAula subUsoAula;

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

    public SubUsoAula getSubUsoAula() {
        return subUsoAula;
    }
    public void setSubUsoAula(SubUsoAula subUsoAula) {
        this.subUsoAula = subUsoAula;
    }
}
