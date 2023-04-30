package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

public class Clientes implements IClientes {

	private List<Cliente> coleccionClientes;
	public Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	@Override
	public List<Cliente>  get() {
		ArrayList<Cliente> clientesR = new ArrayList<>(coleccionClientes);
		return clientesR ;
	}

	@Override
	public int getCantidad() {

		try{return coleccionClientes.size();}
		catch(Exception e) {
			int cantidad = 0;
			return  cantidad;}
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
	//indexof devuelve -1 si no se encuentra, por tanto, si no devuelve -1 no inserta y lanza excepcion
		if (coleccionClientes.indexOf(cliente) == -1) {coleccionClientes.add(new Cliente(cliente));}
		else {throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");}
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		if(coleccionClientes.indexOf(cliente)!=-1) {
			return(coleccionClientes.get(coleccionClientes.indexOf(cliente)));
		}
		else return null;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}

		if(coleccionClientes.indexOf(cliente)!=-1) {
			coleccionClientes.remove(coleccionClientes.indexOf(cliente));
		}
		else throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
	}

	@Override
	public void modificar(Cliente cliente,String nombre,String telefono) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}


		if(coleccionClientes.contains(cliente)) {
			try {
				if(nombre==null||nombre.trim() ==null||nombre=="") {nombre = cliente.getNombre();}
				if(telefono==null||telefono.trim()==null||telefono=="") {telefono = cliente.getTelefono();}
		/*		Cliente clienteInsert = new Cliente(nombre,cliente.getDni(),telefono);
				coleccionClientes.set(coleccionClientes.indexOf(cliente), clienteInsert);*/
			coleccionClientes.get(coleccionClientes.indexOf(cliente)).setNombre(nombre);
			coleccionClientes.get(coleccionClientes.indexOf(cliente)).setTelefono(telefono);
			} catch (Exception e) {System.out.println("ERROR: No se pudo insertar el cliente");}

		}
		else throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
	}

	@Override
	public void terminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void comenzar() {
		// TODO Auto-generated method stub

	}


}
