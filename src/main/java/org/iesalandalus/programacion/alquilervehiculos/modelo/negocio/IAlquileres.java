package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IAlquileres {

	List<Alquiler> get();

	List<Alquiler> get(Cliente cliente);

	List<Alquiler> get(Vehiculo vehiculo);

	int getCantidad();

	void insertar(Alquiler alquiler) throws OperationNotSupportedException, NullPointerException;

	/*	public void devolver(Cliente cliente,LocalDate fechaDevolucion) {
		if(cliente==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}

		for (Alquiler alquilerB : coleccionAlquileres) {

			if(alquilerB.getCliente().equals(cliente)) {
				try {alquilerB.devolver(fechaDevolucion);}
				catch (OperationNotSupportedException e) {e.getMessage();}}

		}
	}*/
	void devolver(Alquiler alquiler, LocalDate fechaDevolucion)
			throws NullPointerException, OperationNotSupportedException;

	Alquiler buscar(Alquiler alquiler);

	void borrar(Alquiler alquiler) throws OperationNotSupportedException;

	void devolver(Cliente cliente, LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException;



	void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException;

	void terminar();

	void comenzar();



}