package org.example.entity;


import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private int creditos;

    @ManyToMany
    @JoinTable(
            name = "cursoProfesor",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "profesor_id")
    )
    private Set<Profesor> profesores;

    @OneToMany(mappedBy = "curso")
    private Set<Inscripcion> inscripciones;

    public Curso() {
    }

    public Curso(String nombre, String codigo, int creditos) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.creditos = creditos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public Set<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(Set<Profesor> profesores) {
        this.profesores = profesores;
    }

    public Set<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(Set<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
}


