package com.alejandronieves.model;

import javax.persistence.*;

@Entity
@Table(name = "subusoaula")
public class SubUsoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsubuso")
    private Long id;

    private String descripcion;
    private Boolean activo;

    // Relación ManyToOne con UsoAula
    @ManyToOne(optional = false)
    @JoinColumn(name = "USOAULAiduso", nullable = false)
    private UsoAula usoAula;

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

    public UsoAula getUsoAula() {
        return usoAula;
    }
    public void setUsoAula(UsoAula usoAula) {
        this.usoAula = usoAula;
    }
}
