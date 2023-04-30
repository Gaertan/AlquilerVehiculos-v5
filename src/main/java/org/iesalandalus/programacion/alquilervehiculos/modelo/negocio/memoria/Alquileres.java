package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres {

	private List<Alquiler> coleccionAlquileres;

	public Alquileres() {coleccionAlquileres = new ArrayList<>();}

	@Override
	public List<Alquiler> get(){return coleccionAlquileres;}

	@Override
	public List<Alquiler> get(Cliente cliente){
		if(cliente==null) {throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");}
		List<Alquiler> coleccionCliente = new ArrayList<>() ;
		for (Alquiler alquiler : coleccionAlquileres) {
				if(alquiler.getCliente().equals(cliente)) {
					coleccionCliente.add(alquiler);}
			}
		return coleccionCliente;
		}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo){
		if(vehiculo==null) {throw new NullPointerException("ERROR: No se puede buscar un vehiculo nulo.");}
		List<Alquiler> coleccionVehiculo = new ArrayList<>() ;
		for (Alquiler alquiler : coleccionAlquileres) {
				if(alquiler.getVehiculo().equals(vehiculo)) {
					coleccionVehiculo.add(alquiler);}
			}
		return coleccionVehiculo;
		}

	@Override
	public int getCantidad() {
		try{int cantidad = coleccionAlquileres.size();return cantidad;}
		catch(Exception e) {int cantidad=0;return cantidad;}}

	private boolean comprobarAlquiler(Cliente cliente,Vehiculo vehiculo,LocalDate fechaAlquiler) throws OperationNotSupportedException {
		//como llevaba desde las 3 am haciendo cosas hice el codigo para insertarle alquiler como parametros,oops
		//el codigo es optimizable/algo redundante por ello pero bueno, pasas que cosan

		boolean status = true;

		for (Alquiler alquilerB : coleccionAlquileres) {

			if((cliente.equals(alquilerB.getCliente()))&&(alquilerB.getFechaDevolucion()==null)) {
					status = false;throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
			}

			if((vehiculo.equals(alquilerB.getVehiculo()))&&(alquilerB.getFechaDevolucion()==null)) {
				status = false;throw new OperationNotSupportedException("ERROR: El vehiculo está actualmente alquilado.");
			}



			if(cliente.equals(alquilerB.getCliente())&&(alquilerB.getFechaDevolucion().isAfter(fechaAlquiler))) {
					status = false;throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
			}

			if(vehiculo.equals(alquilerB.getVehiculo())&&alquilerB.getFechaDevolucion().isAfter(fechaAlquiler)) {
					status = false;throw new OperationNotSupportedException("ERROR: El vehiculo tiene un alquiler posterior.");
			}

		}


		return status;}

	@Override
	public void insertar(Alquiler alquiler)throws OperationNotSupportedException , NullPointerException {
		if(alquiler==null) {throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");}
		/*try {
			if(comprobarAlquiler(alquiler.getCliente(),alquiler.getVehiculo(),alquiler.getFechaAlquiler())) {coleccionAlquileres.add(alquiler);}
		} catch (OperationNotSupportedException e) {

			e.getMessage();
		}*/
		if(comprobarAlquiler(alquiler.getCliente(),alquiler.getVehiculo(),alquiler.getFechaAlquiler())) {


			coleccionAlquileres.add(new Alquiler(alquiler));


		}
	}

	@Override
	public void devolver(Cliente cliente,LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
		if(cliente==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}
//		for (Alquiler alquilerB : coleccionAlquileres) {
//
//
//			if(alquilerB.getCliente().equals(cliente)&&alquilerB.getFechaDevolucion()==null) {
//				/*try {alquilerB.devolver(fechaDevolucion);}
//				catch (OperationNotSupportedException e) {e.getMessage();}}*/
//				alquilerB.devolver(fechaDevolucion);}
//			status = true;
//		}
		Alquiler alquiler = null;alquiler = getAlquilerAbierto(cliente);
		if (alquiler==null)	{throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");}
		alquiler.devolver(fechaDevolucion);
	}
	@Override
	public void devolver(Vehiculo vehiculo,LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
		if(vehiculo==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}
		Alquiler alquiler = null;alquiler = getAlquilerAbierto(vehiculo);
		if (alquiler==null)	{throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");}
		alquiler.devolver(fechaDevolucion);
	}

//	@Override
//	public void devolver(Alquiler alquiler,LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
//		if(alquiler==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}
//		boolean status = false;
//		for (Alquiler alquilerB : coleccionAlquileres) {
//
//
//			if(alquilerB.equals(alquiler)) {
//				/*try {alquilerB.devolver(fechaDevolucion);}
//				catch (OperationNotSupportedException e) {e.getMessage();}}*/
//				alquilerB.devolver(fechaDevolucion);}
//			status = true;
//		}
//		if (!status) {throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");}
//	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}

		if(coleccionAlquileres.contains(alquiler)) {
			Alquiler alquilerB = coleccionAlquileres.get(coleccionAlquileres.indexOf(alquiler));return alquilerB;}
		else {return null;}
		}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}

		if(coleccionAlquileres.contains(alquiler)) {coleccionAlquileres.remove(alquiler);}
		else throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}

	@Override
	public void devolver(Alquiler alquiler, LocalDate fechaDevolucion)
			throws NullPointerException, OperationNotSupportedException {
		// TODO Auto-generated method stub

	}

	public Alquiler getAlquilerAbierto(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler de cliente nulo.");
		}
		Alquiler alquiler = null;
		for(Alquiler alquilerBusca : coleccionAlquileres) {
			if(alquilerBusca.getFechaDevolucion()==null && alquilerBusca.getCliente().equals(cliente)) {alquiler = alquilerBusca;}


		}
		return alquiler;
	}

	public Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler de cliente nulo.");
		}
		Alquiler alquiler = null;
		for(Alquiler alquilerBusca : coleccionAlquileres) {
			if(alquilerBusca.getFechaDevolucion()==null && alquilerBusca.getVehiculo().equals(vehiculo)) {alquiler = alquilerBusca;}


		}
		return alquiler;
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
