package edu.isistan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.isistan.dao.Direccion;
import edu.isistan.dao.Persona;


public class Insert {
	public static void main(String[] args) {
		//el nombre es el que pusimos en el persistence.xml
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
		EntityManager em = emf.createEntityManager();
		
		//iniciamos la transaccion para empezar a mandar datos
		em.getTransaction().begin();
		Direccion d = new Direccion ("Tandil","Chacabuco");
		
		//digo que persista la direccion para que la guarde
		em.persist(d);
		Persona p1 =  new Persona(1,"Nina",23,d);
		Persona p2 =  new Persona(2,"Marta",34,d);
		em.persist(p1);
		em.persist(p2);
		
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
