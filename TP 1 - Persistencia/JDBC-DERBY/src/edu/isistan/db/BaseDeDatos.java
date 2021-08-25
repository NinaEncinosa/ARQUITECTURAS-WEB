package edu.isistan.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//para la op1:
//import org.apache.derby.jdbc.EmbeddedDriver;

public class BaseDeDatos {

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
			createTables(conn);
			
			addPerson(conn,1,"Nina",24);
			addPerson(conn,2,"Carlos",53);
			
			
			//cierro la conexion
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
