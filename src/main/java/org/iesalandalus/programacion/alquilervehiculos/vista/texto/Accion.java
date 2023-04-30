package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import javax.naming.OperationNotSupportedException;

public enum Accion {
	SALIR("Salir"){@Override
	public void ejecutar() {
					vistaTexto.terminar();
	}},


	INSERTAR_CLIENTE("Insertar cliente"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.insertarCliente();
		}},
	INSERTAR_TURISMO("Insertar vehiculo"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.insertarVehiculo();
		}},
	INSERTAR_ALQUILER("Insertar alquiler"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.insertarAlquiler();
		}},
	BUSCAR_CLIENTE("Buscar cliente"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.buscarCliente();
		}},
	BUSCAR_TURISMO("Buscar vehiculo"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.buscarTurismo();
		}},
	BUSCAR_ALQUILER("Buscar alquiler"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.buscarAlquiler();

		}},
	MODIFICAR_CLIENTE("Modificar cliente"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.modificarCliente();
		}},

	DEVOLVER_ALQUILER_CLIENTE("Devolver alquiler de cliente"){

			@Override
			protected void ejecutar() {
				// TODO Auto-generated method stub
				vistaTexto.devolverAlquilerCliente();
			}},

	DEVOLVER_ALQUILER_VEHICULO("Devolver alquiler de vehiculo"){

				@Override
				protected void ejecutar() {
					// TODO Auto-generated method stub
					vistaTexto.devolverAlquilerVehiculo();
				}},



//	DEVOLVER_ALQUILER("Devolver alquiler"){
//
//		@Override
//		protected void ejecutar() {
//			// TODO Auto-generated method stub
//			vistaTexto.devolverAlquiler();
//		}},
	BORRAR_CLIENTE("Borrar cliente"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.borrarCliente();
		}},
	BORRAR_TURISMO("Borrar vehiculo"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.borrarTurismo();
		}},
	BORRAR_ALQUILER("Borrar alquiler"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.borrarAlquiler();
		}},
	LISTAR_CLIENTES("Listar clientes"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.listarCliente();
		}},
	LISTAR_TURISMOS("Listar vehiculo"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.listarTurismo();
		}},
	LISTAR_ALQUILERES("Listar alquileres"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.listarAlquiler();
		}},
	LISTAR_ALQUILERES_CLIENTES("Listar alquileres de clientes"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.listarAlquileresCliente();
		}},
	LISTAR_ALQUILERES_TURISMO("Listar alquileres de vehiculos"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.listarAlquileresTurismo();
		}},


	MOSTRAR_ESTADITICAS_MENSUALES("Mostrar estadisticas mensuales"){

		@Override
		protected void ejecutar() {
			// TODO Auto-generated method stub
			vistaTexto.mostrarEstadisticasMensualesTipoVehiculo();
		}};


	protected VistaTexto vistaTexto;
	private String cadenaAmostrar;

	Accion(String cadenaAmostrar){this.cadenaAmostrar=cadenaAmostrar;}

	private boolean esOrdinalValido(int ordinal) {
		return(ordinal>=0&&ordinal<Accion.values().length);

	}

	public Accion get(int ordinal) throws OperationNotSupportedException {
		if(esOrdinalValido(ordinal)) {return Accion.values()[ordinal];}else throw new OperationNotSupportedException("No se encuentra el ordinal insertado(fuera de rango)");
	}



	protected void setVista(VistaTexto vistaTexto) {this.vistaTexto=vistaTexto;}

	protected abstract void ejecutar();




	@Override
	public String toString() {return this.cadenaAmostrar;}

}