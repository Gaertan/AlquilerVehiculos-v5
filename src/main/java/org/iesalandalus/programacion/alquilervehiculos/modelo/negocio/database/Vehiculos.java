

package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database.utilidades.MySQL;




public class Vehiculos implements IVehiculos {



			private final String TURISMO="turismo";
			private final String AUTOBUS="autobus";
			private final String FURGONETA="furgoneta";

	private static Vehiculos instance = new Vehiculos();
	private Connection conexion;

	private Vehiculos() {}

	@Override
	public List<Vehiculo> get(){
		ArrayList<Vehiculo> vehiculosR = new ArrayList<>();
		
		try {
			String sentenciaStr = "select modelo, marca,matricula, cilindrada, plazas, pma, tipo from vehiculos";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			ResultSet filas = sentencia.executeQuery();
			while (filas.next()) {
			
				String marca = filas.getString(1);
				String modelo = filas.getNString(2);
				String matricula = filas.getString(3);
				int cilindrada = filas.getInt(4);
				int plazas = filas.getInt(5);
				int pma =filas.getInt(6);
				String tipo=filas.getString(7);
				Vehiculo vehiculo = null;
				if(tipo.equalsIgnoreCase(TURISMO)) {
					vehiculo = new Turismo(marca,modelo,cilindrada,matricula);
				}
				if(tipo.equalsIgnoreCase(FURGONETA)) {	
					vehiculo = new Furgoneta(marca,modelo,pma,plazas,matricula);
				}
				if(tipo.equalsIgnoreCase(AUTOBUS)) {	
					vehiculo = new Autobus(marca,modelo,plazas,matricula);
				}
				vehiculosR.add(vehiculo);
			}
		} 
		catch (SQLException e) {throw new IllegalArgumentException(e.getMessage());}
		return vehiculosR;
	}

	@Override
	public int getCantidad() {
		int tamano = 0;
		try {
			String sentenciaStr = "select count(*) from vehiculos";
			Statement sentencia = conexion.createStatement();
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			if (filas.next()) {
				tamano = filas.getInt(1);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return tamano;
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehiculo nulo.");
		}
		String tipo = vehiculo.getClass().getSimpleName();
		String modelo = vehiculo.getModelo();
		String marca = vehiculo.getMarca();
		String matricula = vehiculo.getMatricula();
		int cilindrada = 0;
		int plazas = 0 ;
		int pma = 0;
		try {
			String sentenciaStr = "insert into vehiculos values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			if(tipo.equalsIgnoreCase(TURISMO)) {
				 cilindrada = ((Turismo) vehiculo).getCilindrada();
			}
			if(tipo.equalsIgnoreCase(FURGONETA)) {	
				plazas=((Furgoneta)vehiculo).getPlazas();
				pma = ((Furgoneta)vehiculo).getPma();
			}
			if(tipo.equalsIgnoreCase(AUTOBUS)) {	
				plazas = ((Autobus)vehiculo).getPlazas();
			}
			sentencia.setString(1, marca);
			sentencia.setString(2, modelo);
			sentencia.setString(3, matricula);
			sentencia.setInt(4, cilindrada);
			sentencia.setInt(5, plazas);
			sentencia.setInt(6, pma);
			sentencia.setString(7, tipo);

			sentencia.executeUpdate();
		
		} catch (SQLIntegrityConstraintViolationException e) {e.printStackTrace();
			throw new OperationNotSupportedException("ERROR: Ya existe un vehiculo con esa matricula.");
		} catch (SQLException e) {
			throw new OperationNotSupportedException( e.getMessage());
		}
	

	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehiculo nulo.");
		}

		try {
			String sentenciaStr = "select marca, modelo, cilindrada, plazas, pma, tipo from vehiculos where matricula=?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, vehiculo.getMatricula());
			ResultSet filas = sentencia.executeQuery();
			if (filas.next()) {
				String marca = filas.getString(1);
				String modelo = filas.getNString(2);
				String matricula = vehiculo.getMatricula();
				int cilindrada = filas.getInt(3);
				int plazas = filas.getInt(4);
				int pma =filas.getInt(5);
				String tipo=filas.getString(6);
				
				if(tipo.equalsIgnoreCase(TURISMO)) {
					vehiculo = new Turismo(marca,modelo,cilindrada,matricula);
				}
				if(tipo.equalsIgnoreCase(FURGONETA)) {	
					vehiculo = new Furgoneta(marca,modelo,pma,plazas,matricula);
				}
				if(tipo.equalsIgnoreCase(AUTOBUS)) {	
					vehiculo = new Autobus(marca,modelo,plazas,matricula);
				}
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		return vehiculo;}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehiculo nulo.");
		}
		try {
			String sentenciaStr = "delete from vehiculos where vehiculos.matricula = ? ";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, vehiculo.getMatricula());
		
			if (sentencia.executeUpdate() == 0) {
				throw new OperationNotSupportedException("ERROR: No existe ningún vehiculo con esa matricula.");
			}
		} catch (SQLException e) {
			throw new OperationNotSupportedException( e.toString());
		}}

	protected static IVehiculos getInstancia() {
	return instance ;
	}




	@Override
	public void comenzar() {
		try {
			conexion = MySQL.establecerConexion();
		} catch (Exception e) {
			System.out.println("Algo ha ocurrido leyendo el archivo,puede que no se encuentre o sea nulo");
		}
	}
	@Override
	public void terminar() {
		try {
			MySQL.cerrarConexion();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
