package org.example;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.Profesor;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cademico");

    
        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();


            Profesor profesor = new Profesor();
            profesor.setNombre("Juan Pérez");
            profesor.setEspecialidad("Matemáticas");

            em.persist(profesor); // guardar en la base de datos


            em.getTransaction().commit();
            System.out.println(" Profesor guardado correctamente.");

        } catch (Exception e) {

            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {

            em.close();
            emf.close();
        }
    }
}
