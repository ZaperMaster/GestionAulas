package com.alejandronieves.model;

import javax.persistence.*;

@Entity
@Table(name = "planta")
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idplanta")
    private Long id;

    private String descripcion;
    private Integer naulasdocentes;
    private Integer naulaauxiliares;
    private Boolean activo;

    // Relación con Edificio: cada Planta pertenece a un Edificio
    @ManyToOne(optional = false)
    @JoinColumn(name = "EDIFICIOidedificio", nullable = false)
    private Edificio edificio;

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
    public Integer getNaulasdocentes() {
        return naulasdocentes;
    }
    public void setNaulasdocentes(Integer naulasdocentes) {
        this.naulasdocentes = naulasdocentes;
    }
    public Integer getNaulaauxiliares() {
        return naulaauxiliares;
    }
    public void setNaulaauxiliares(Integer naulaauxiliares) {
        this.naulaauxiliares = naulaauxiliares;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public Edificio getEdificio() {
        return edificio;
    }
    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }
}
