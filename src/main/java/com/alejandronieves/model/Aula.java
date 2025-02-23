package com.alejandronieves.model;

import javax.persistence.*;

@Entity
@Table(name = "aula")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idaula")
    private Long id;

    @Column(name = "nombreaula")
    private String nombreaula;

    private String descripcion;

    private Integer capacidad;

    // Otros atributos (proyector, smarttv, hdmi, appletv, aireacondicionado, nenchufes, activo)
    private Boolean proyector;
    private Boolean smarttv;
    private Boolean hdmi;
    private Boolean appletv;
    private Boolean aireacondicionado;
    private Integer nenchufes;
    private Boolean activo;

    // Relación ManyToOne con Planta
    @ManyToOne
    @JoinColumn(name = "PLANTAidplanta")
    private Planta planta;

    // Si necesitas la relación con TipoAula, agrégala (o elimina esta parte si no es necesaria)
    @ManyToOne
    @JoinColumn(name = "TIPOAULAidtipo")
    private TipoAula tipoAula;

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreaula() {
        return nombreaula;
    }
    public void setNombreaula(String nombreaula) {
        this.nombreaula = nombreaula;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
    public Boolean getProyector() {
        return proyector;
    }
    public void setProyector(Boolean proyector) {
        this.proyector = proyector;
    }
    public Boolean getSmarttv() {
        return smarttv;
    }
    public void setSmarttv(Boolean smarttv) {
        this.smarttv = smarttv;
    }
    public Boolean getHdmi() {
        return hdmi;
    }
    public void setHdmi(Boolean hdmi) {
        this.hdmi = hdmi;
    }
    public Boolean getAppletv() {
        return appletv;
    }
    public void setAppletv(Boolean appletv) {
        this.appletv = appletv;
    }
    public Boolean getAireacondicionado() {
        return aireacondicionado;
    }
    public void setAireacondicionado(Boolean aireacondicionado) {
        this.aireacondicionado = aireacondicionado;
    }
    public Integer getNenchufes() {
        return nenchufes;
    }
    public void setNenchufes(Integer nenchufes) {
        this.nenchufes = nenchufes;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public Planta getPlanta() {
        return planta;
    }
    public void setPlanta(Planta planta) {
        this.planta = planta;
    }
    public TipoAula getTipoAula() {
        return tipoAula;
    }
    public void setTipoAula(TipoAula tipoAula) {
        this.tipoAula = tipoAula;
    }
}
