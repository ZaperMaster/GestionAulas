package com.alejandronieves.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIPOUSUARIO")
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipousuario")
    private Long id;

    // Se usa el campo "tipo" para definir el rol (ej.: "ADMIN", "USER")
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;

    // Constructores
    public TipoUsuario() {
    }

    public TipoUsuario(String tipo, String descripcion, Boolean activo) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }
  
    public void setId(Long id) {
        this.id = id;
    }
  
    public String getTipo() {
        return tipo;
    }
  
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
}
