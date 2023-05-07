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

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database.utilidades.MySQL;

public class Clientes implements IClientes {


	private Connection conexion;










	private static Clientes instance = new Clientes();
	private Clientes() {}

	@Override
	public List<Cliente>  get() {
		ArrayList<Cliente> clientesR = new ArrayList<>();
		
		try {
			String sentenciaStr = "select nombre, dni, telefono from clientes order by nombre";
			Statement sentencia = conexion.createStatement();
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			while (filas.next()) {
				String nombre = filas.getString(1);
				String dni = filas.getString(2);
				String telefono = filas.getString(3);
				Cliente cliente = new Cliente(nombre, dni, telefono);
				clientesR.add(cliente);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return clientesR ;
	}

	@Override
	public int getCantidad() {
		int tamano = 0;
		try {
			String sentenciaStr = "select count(*) from clientes";
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
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");}
			try {
				String sentenciaStr = "insert into clientes values (?, ?, ?)";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
				sentencia.setString(1, cliente.getNombre());
				sentencia.setString(2, cliente.getDni());
				sentencia.setString(3, cliente.getTelefono());
				sentencia.executeUpdate();
			} catch (SQLIntegrityConstraintViolationException e) {
				throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese dni.");
			} catch (SQLException e) {
				throw new OperationNotSupportedException( e.getMessage());
			}
		
	}
	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un cliente nulo.");
		}
		try {
			String sentenciaStr = "select nombre, dni, telefono from clientes where dni=?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, cliente.getDni());
			ResultSet filas = sentencia.executeQuery();
			if (filas.next()) {
				String nombre = filas.getString(1);
				String dni = filas.getNString(2);
				String telefono = filas.getString(3);
				cliente = new Cliente(nombre, dni, telefono);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return cliente;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un cliente nulo.");
		}
		try {
			String sentenciaStr = "delete from clientes where dni = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, cliente.getDni());
			if (sentencia.executeUpdate() == 0) {
				throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese dni.");
			}
		} catch (SQLException e) {
			throw new OperationNotSupportedException( e.toString());
		}
	}
	

	@Override
	public void modificar(Cliente cliente,String nombre,String telefono) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}

		try {
			String sentenciaStr = "UPDATE clientes SET nombre = ?, telefono = ? WHERE dni = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, nombre);
			sentencia.setString(2, telefono);
			sentencia.setString(3, cliente.getDni());
			if (sentencia.executeUpdate() == 0) {
				throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
			}
		} catch (SQLException e) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

		

	}

	protected static IClientes getInstancia() {

		return instance;

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
		try {MySQL.cerrarConexion();
		} catch (Exception e) {
		}
	}





}
