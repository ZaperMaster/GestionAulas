package com.alejandronieves.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fechaactivacion")
    private LocalDate fechaActivacion;

    @Column(name = "fechadesactivacion")
    private LocalDate fechaDesactivacion;

    @Column(name = "dni")
    private String dni;

    // Se elimina "unique = true" para que no se restrinja la unicidad
    @Column(name = "email")
    private String email;

    // Aumentamos la longitud para almacenar el hash de BCrypt (60 o más caracteres)
    @Column(name = "contrasenya", length = 100)
    private String password;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "telefono")
    private String telefono;

    // Relación con TIPOUSUARIO (para asignar un rol, por ejemplo)
    @ManyToOne
    @JoinColumn(name = "TIPOUSUARIOidtipousuario")
    private TipoUsuario tipoUsuario;

    public Usuario() {
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
  
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(LocalDate fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public LocalDate getFechaDesactivacion() {
        return fechaDesactivacion;
    }

    public void setFechaDesactivacion(LocalDate fechaDesactivacion) {
        this.fechaDesactivacion = fechaDesactivacion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
  
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }
  
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getTelefono() {
        return telefono;
    }
  
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
  
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
