package edu.isistan;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.isistan.dao.Persona;

public class Select {

	public static void main(String[] args) {
		//el nombre es el que pusimos en el persistence.xml
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
		EntityManager em = emf.createEntityManager();
		
		//iniciamos la transaccion para empezar a mandar datos
		em.getTransaction().begin();
		
		//mostrar las personas de manera manual, tengo que previamente saber cual es su id
//		Persona p1 = em.find(Persona.class,1);
//		Persona p2 = em.find(Persona.class,2);
//		
//		System.out.println(p1);
//		System.out.println(p2);
		
		//creo la query para traer todas las personas que tenga la tabla Persona
		@SuppressWarnings("unchecked")
		List<Persona> personas = em.createQuery("SELECT p FROM Persona p").getResultList();
		
		personas.forEach(p -> System.out.println(p));
		
		
		
		em.getTransaction().commit();
		em.close();
		emf.close();

	}

}
