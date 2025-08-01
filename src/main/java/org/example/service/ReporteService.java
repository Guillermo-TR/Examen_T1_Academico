package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class ReporteService {


    public void listarCursosConTotalInscripcionesYPromedioCreditos(EntityManager em) {
        System.out.println("=== Cursos con total de inscripciones y promedio de créditos ===");

        String jpql = """
            SELECT c.nombre, COUNT(i.id), AVG(c.creditos)
            FROM Curso c
            JOIN c.inscripciones i
            GROUP BY c.nombre
            HAVING COUNT(i.id) >= 2
            """;

        List<Object[]> resultados = em.createQuery(jpql, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.printf("Curso: %s | Inscripciones: %s | Prom. Créditos: %.2f%n",
                    row[0], row[1], row[2]);
        }
    }


    public void estudiantesConMasDeDosCursos(EntityManager em) {
        System.out.println("=== Estudiantes con más de 2 cursos ===");

        String jpql = """
            SELECT e.nombre, e.email, COUNT(i.curso.id)
            FROM Estudiante e
            JOIN e.inscripciones i
            GROUP BY e.id, e.nombre, e.email
            HAVING COUNT(DISTINCT i.curso.id) > 2
            """;

        List<Object[]> resultados = em.createQuery(jpql, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.printf("Estudiante: %s | Email: %s | Cursos Inscritos: %s%n", row[0], row[1], row[2]);
        }
    }

    public void buscarInscripcionesConFiltros(EntityManager em, LocalDate fechaInicio, LocalDate fechaFin,
                                              String estado, String nombreEstudiante, String codigoCurso) {
        System.out.println("=== Búsqueda de Inscripciones con filtros dinámicos ===");

        StringBuilder sb = new StringBuilder("""
            SELECT i FROM Inscripcion i
            WHERE i.fechaInscripcion BETWEEN :inicio AND :fin
              AND i.estado = :estado
            """);

        if (nombreEstudiante != null && !nombreEstudiante.isEmpty()) {
            sb.append(" AND i.estudiante.nombre LIKE :nombreEstudiante");
        }

        if (codigoCurso != null && !codigoCurso.isEmpty()) {
            sb.append(" AND i.curso.codigo = :codigoCurso");
        }

        sb.append(" ORDER BY i.fechaInscripcion DESC");

        TypedQuery<?> query = em.createQuery(sb.toString(), Object.class);
        query.setParameter("inicio", fechaInicio);
        query.setParameter("fin", fechaFin);
        query.setParameter("estado", estado);

        if (nombreEstudiante != null && !nombreEstudiante.isEmpty()) {
            query.setParameter("nombreEstudiante", "%" + nombreEstudiante + "%");
        }

        if (codigoCurso != null && !codigoCurso.isEmpty()) {
            query.setParameter("codigoCurso", codigoCurso);
        }

        List<?> resultados = query.getResultList();
        for (Object result : resultados) {
            System.out.println(result);
        }
    }


    public void reporteCargaAcademicaDeProfesores(EntityManager em) {
        System.out.println("=== Reporte de carga académica por profesor ===");

        String jpql = """
            SELECT p.nombre, SUM(c.creditos)
            FROM CursoProfesor cp
            JOIN cp.profesor p
            JOIN cp.curso c
            GROUP BY p.nombre
            HAVING COUNT(cp) > 0
            """;

        List<Object[]> resultados = em.createQuery(jpql, Object[].class).getResultList();
        for (Object[] row : resultados) {
            System.out.printf("Profesor: %s | Carga académica total: %s créditos%n", row[0], row[1]);
        }
    }

}


