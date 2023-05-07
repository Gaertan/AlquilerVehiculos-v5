package org.iesalandalus.programacion.alquilervehiculos.modelo;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database.FuenteDatosDatabase;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.FuenteDatosFicheros;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria.FuenteDatosMemoria;

public enum FactoriaFuenteDatos {

	MEMORIA{

			@Override
			public IFuenteDatos crear() {
				return new FuenteDatosMemoria();
			}
	},
	FICHEROS{

		@Override
		public IFuenteDatos crear() {
			return new FuenteDatosFicheros();
		}
	},


	DATABASE{

		@Override
		public IFuenteDatos crear() {
			return new FuenteDatosDatabase();
		}
};
	protected abstract IFuenteDatos crear();



}
