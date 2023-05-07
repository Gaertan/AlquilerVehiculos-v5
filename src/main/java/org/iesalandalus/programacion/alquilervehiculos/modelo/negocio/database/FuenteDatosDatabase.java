package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class FuenteDatosDatabase implements IFuenteDatos {

	@Override
	public IClientes crearClientes() {
		// TODO Auto-generated method stub
		return Clientes.getInstancia();
	}

	@Override
	public IVehiculos crearVehiculos() {
		// TODO Auto-generated method stub
		return Vehiculos.getInstancia();
	}

	@Override
	public IAlquileres crearAlquileres() {
		return Alquileres.getInstancia();
	}

}
