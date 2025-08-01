package org.example.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "curso_profesor")
@IdClass(CursoProfesor.class)
public class CursoProfesor {

    @Id
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Id
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    public CursoProfesor() {
    }

    public CursoProfesor(Curso curso, Profesor profesor) {
        this.curso = curso;
        this.profesor = profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}
