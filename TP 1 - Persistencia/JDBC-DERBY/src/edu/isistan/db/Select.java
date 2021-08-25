package edu.isistan.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Select {
	public static void main(String[] args) {
		//para registrar el driver:
		
		//opc1:
		//creo una instancia del driver y no la utilizo
		//con esta opcion no puedo cambiar el driver sin tener que cambiar el codigo 
		
		//new EmbeddedDriver();
		
		//op2, mejor: almaceno el driver en una variable (aca hardcodeada pero la idea es que venga desde afuera)
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		
		try {
			//esta es la linea que escribo, despues con el cartelito de error hago que se genere el try/catch automatico
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			//y agrego esta linea para que si surge un problema salga del programa
			System.exit(1);
		}
		
		//una vez configurado el driver, tenemos que conectarnos a la BD
		//para esto necesitamos definir la direccion donde esta la BD:
		//particularidades, siempre empieza con 
		//"jdbc : tipo de la base de datos : path de la BD o poner un nombre si es de cero ; parametro que indica que si no existe la cree "
		String uri = "jdbc:derby:MyDerbyDb;create=true";
		
		//una vez definida la uri, creamos la conexion 
		//selecciono la connection que viene de java.sql
		//como puede surgir un problema, rodeo con try catch
		try {
			//abro la conexion
			Connection conn = DriverManager.getConnection(uri);
			
			//aca todo lo que querramos hacer por ejemplo:
			String select = "SELECT * FROM persona";
			PreparedStatement ps = conn.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("id: " + rs.getInt(1) + " nombre: " + rs.getString(2) + " edad: " + rs.getInt(3));
			}
			
			//cierro la conexion
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}



}
