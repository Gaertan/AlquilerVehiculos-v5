package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	
	private static final String HOST = "localhost:3306";
	private static final String ESQUEMA = "alquilervehiculos";
	private static final String USUARIO = "root";
	private static final String CONTRASENA = "mysql2223";
	
	private static Connection conexion = null;
	
	private MySQL() {
		//Evitamos que se creen instancias
	}
	
	public static Connection establecerConexion() {
		if (conexion != null)
			return conexion;
		try {
						
			//Class.forName("com.mysql.jdbc.Driver").newInstance(); // ESTA LÍNEA ESTÁ OBSOLETA
			
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/alquilervehiculos","root",CONTRASENA);
			System.out.println("Conexión a MySQL realizada correctamente.");
		} catch (SQLException e) {
			System.out.println("ERROR MySQL:  "+ e.toString());
		}
		return conexion;
	}
	public static void crearDatabase() {
//	try {Connection conex = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root",CONTRASENA);
//
//		Statement statement = conex.createStatement();
//		statement.executeUpdate("CREATE DATABASE if not exists `alquilervehiculos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;\n"
//				+ "use alquilervehiculos;\n"
//				+ "CREATE TABLE if not exists`clientes` (\n"
//				+ "  `nombre` varchar(40) ,\n"
//				+ "  `dni` varchar(40) NOT NULL,\n"
//				+ "  `telefono` varchar(40),\n"
//				+ "  PRIMARY KEY (`dni`)\n"
//				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
//				+ "CREATE TABLE if not exists `vehiculos` (\n"
//				+ "  `marca` varchar(40) ,\n"
//				+ "  `modelo` varchar(40) ,\n"
//				+ "  `matricula` varchar(40) NOT NULL,\n"
//				+ "  `cilindrada` int unsigned ,\n"
//				+ "  `plazas` tinyint unsigned ,\n"
//				+ "  `pma` int unsigned ,\n"
//				+ "  `tipo` varchar(40) ,\n"
//				+ "  PRIMARY KEY (`matricula`)\n"
//				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
//				+ "CREATE TABLE if not exists `alquileres` (\n"
//				+ "  `matricula` varchar(40) NOT NULL,\n"
//				+ "  `dni` varchar(40) NOT NULL,\n"
//				+ "  `fechaAlquiler` date NOT NULL,\n"
//				+ "  `fechaDevolucion` date,\n"
//				+ "  KEY `matricula`(`matricula`),\n"
//				+ "  KEY `dni` (`dni`),\n"
//				+ "  KEY `fechaAlquiler` (`fechaAlquiler`),\n"
//				+ "  CONSTRAINT `prestamos_ibfk_1` FOREIGN KEY (`dni`) REFERENCES `clientes` (`dni`) ON DELETE RESTRICT ON UPDATE CASCADE,\n"
//				+ "  CONSTRAINT `prestamos_ibfk_2` FOREIGN KEY (`matricula`) REFERENCES `vehiculos` (`matricula`) ON DELETE RESTRICT ON UPDATE CASCADE\n"
//				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
//				+ "\n"
//				+ "");
//		cerrarConexion();
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
		
		try {
		    Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", CONTRASENA);
		    
		    // Crear base de datos
		    Statement statement = conexion.createStatement();
		    statement.executeUpdate("CREATE DATABASE IF NOT EXISTS alquilervehiculos");
		    
		    statement.executeUpdate("USE alquilervehiculos");
		  
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS clientes (nombre VARCHAR(40), dni VARCHAR(40) NOT NULL, telefono VARCHAR(40), PRIMARY KEY (dni)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci");
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS vehiculos (marca VARCHAR(40), modelo VARCHAR(40), matricula VARCHAR(40) NOT NULL, cilindrada INT UNSIGNED, plazas TINYINT UNSIGNED, pma INT UNSIGNED, tipo VARCHAR(40), PRIMARY KEY (matricula)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci");
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS alquileres (matricula VARCHAR(40) NOT NULL, dni VARCHAR(40) NOT NULL, fechaAlquiler DATE NOT NULL, fechaDevolucion DATE, KEY matricula (matricula), KEY dni (dni), KEY fechaAlquiler (fechaAlquiler), CONSTRAINT prestamos_ibfk_1 FOREIGN KEY (dni) REFERENCES clientes (dni) ON DELETE RESTRICT ON UPDATE CASCADE, CONSTRAINT prestamos_ibfk_2 FOREIGN KEY (matricula) REFERENCES vehiculos (matricula) ON DELETE RESTRICT ON UPDATE CASCADE) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci");
	
		    conexion.close();
		} catch (SQLException e) {e.printStackTrace();
		}
		

		
		
	}
	public static void cerrarConexion() {
		try {
			if (conexion != null) {
				conexion.close();
				conexion = null;
				System.out.println("Conexión a MySQL cerrada correctamente.");
			}
		} catch (SQLException e) {
			System.out.println("ERROR MySQL: "+ e.toString());
		}
	}

}
