package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public enum tipoVehiculo {TURISMO("turismo"),AUTOBUS("autobus"),FURGONETA("furgoneta");

	String nombre;

	tipoVehiculo(String nombre){this.nombre = nombre;}







	private boolean esOrdinalValido(int ordinal) {
		return(ordinal>=0&&ordinal<tipoVehiculo.values().length);

	}

	public tipoVehiculo get(int ordinal) throws OperationNotSupportedException {
		if(esOrdinalValido(ordinal)) {return tipoVehiculo.values()[ordinal];}else throw new OperationNotSupportedException("No se encuentra el ordinal insertado(fuera de rango)");
	}


	public tipoVehiculo get(Vehiculo vehiculo) throws OperationNotSupportedException {
		if(vehiculo instanceof Turismo) {return tipoVehiculo.TURISMO;}
		else if(vehiculo instanceof Autobus) {return tipoVehiculo.AUTOBUS;}
		else if(vehiculo instanceof Furgoneta) {return tipoVehiculo.FURGONETA;}
		else throw new OperationNotSupportedException("No se encuentra el tipo de vehiculo insertado en nuestros tipos");
	}



	@Override
	public String toString() {return this.nombre;}


}
