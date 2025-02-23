package com.alejandronieves.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "edificio")
public class Edificio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idedificio")
    private Long id;

    private String descripcion;
    private Integer naulas;
    private Integer npuertasacceso;
    private String ubicacion;
    private Boolean activo;

    // Relación bidireccional: un edificio tiene muchas plantas.
    @OneToMany(mappedBy = "edificio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Planta> plantas;

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
    public Integer getNaulas() {
        return naulas;
    }
    public void setNaulas(Integer naulas) {
        this.naulas = naulas;
    }
    public Integer getNpuertasacceso() {
        return npuertasacceso;
    }
    public void setNpuertasacceso(Integer npuertasacceso) {
        this.npuertasacceso = npuertasacceso;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public List<Planta> getPlantas() {
        return plantas;
    }
    public void setPlantas(List<Planta> plantas) {
        this.plantas = plantas;
    }
}
