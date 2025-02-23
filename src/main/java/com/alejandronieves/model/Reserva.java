package com.alejandronieves.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreserva")
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechainicio")
    private LocalDate fechainicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechafin")
    private LocalDate fechafin;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "horainicio")
    private LocalTime horainicio;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "horafin")
    private LocalTime horafin;

    @Column(name = "activo")
    private Boolean activo;
    
    @Column(name = "validar")
    private Boolean validar;

    // Relación con Aula
    @ManyToOne
    @JoinColumn(name = "AULAidaula", nullable = false)
    private Aula aula;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "USUARIOidusuario", nullable = false)
    private Usuario usuario;

    // Relación con Curso
    @ManyToOne
    @JoinColumn(name = "CURSOidcurso", nullable = false)
    private Curso curso;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getFechainicio() {
        return fechainicio;
    }
    public void setFechainicio(LocalDate fechainicio) {
        this.fechainicio = fechainicio;
    }
    public LocalDate getFechafin() {
        return fechafin;
    }
    public void setFechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
    }
    public LocalTime getHorainicio() {
        return horainicio;
    }
    public void setHorainicio(LocalTime horainicio) {
        this.horainicio = horainicio;
    }
    public LocalTime getHorafin() {
        return horafin;
    }
    public void setHorafin(LocalTime horafin) {
        this.horafin = horafin;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public Aula getAula() {
        return aula;
    }
    public void setAula(Aula aula) {
        this.aula = aula;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public Boolean getValidar() {
        return validar;
    }

    public void setValidar(Boolean validar) {
        this.validar = validar;
    }
}
