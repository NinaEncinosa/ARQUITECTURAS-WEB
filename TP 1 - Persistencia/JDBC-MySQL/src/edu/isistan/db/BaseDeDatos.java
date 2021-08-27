package edu.isistan.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BaseDeDatos {

	public static void main(String[] args) {

		String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		//path usando docker 3306
		String uri = "jdbc:mysql://localhost:3306/example?createDatabaseIfNotExist=true";
		
		//path usando MAMP
		//String uri = "jdbc:mysql://localhost:8889/example";
		
		try {
			//abro la conexion con docker
			Connection conn = DriverManager.getConnection(uri, "root", "password");
			
			//abro la conexion con MAMP
			//Connection conn = DriverManager.getConnection(uri, "root", "root");
			
			conn.setAutoCommit(false);
			
			//aca todo lo que querramos hacer por ejemplo:
			createTables(conn);
			
			addPerson(conn,1,"Nina",24);
			addPerson(conn,2,"Carlos",53);

			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

	private static void addPerson(Connection conn, int idd, String name, int age) throws SQLException {
		//NO HACER ESTO, puede generar un problema de seguridad, inyeccion de codigo SQL, pueden borrar toda la BD
		//String insert = "INSERT INTO persona (id,nombre,edad) VALUES (" + idd + "," + name + "," + age + ")"; 
		
		//ASI SI, hay que poner placeholders (los ? )
		String insert = "INSERT INTO persona (id,nombre,edad) VALUES (?,?,?)"; 
		
		PreparedStatement ps = conn.prepareStatement(insert);
		
		//defino los placeholders
		ps.setInt(1, idd);
		ps.setString(2, name);
		ps.setInt(3, age);
		
		ps.executeUpdate();
		
		ps.close();
		conn.commit();
		
	}

	private static void createTables(Connection conn) throws SQLException {
		String table = "CREATE TABLE persona("+
				"id INT," +
				"nombre VARCHAR(500),"+
				"edad INT,"+
				"PRIMARY KEY (id))";
		conn.prepareStatement(table).execute();
		conn.commit();
		
	}

}
