package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {


	 @Override
	public void comenzar() {
		Accion accion = null;

		do {

		while (accion==null) {
			Consola.mostrarMenu();
			try {accion = Consola.elegirOpcion();} catch (OperationNotSupportedException e){e.getMessage();}

				if(accion!=null) {
					switch(accion) {
					  case INSERTAR_CLIENTE:
					    insertarCliente();
						  accion = null;break;
					  case INSERTAR_TURISMO:
						 insertarVehiculo();
							  accion = null;break;

					  case INSERTAR_ALQUILER:
						    insertarAlquiler();
							  accion = null;break;

					  case BUSCAR_CLIENTE:
						   buscarCliente();
							  accion = null;break;

					  case BUSCAR_TURISMO:
						    buscarTurismo();
							  accion = null;break;

					  case BUSCAR_ALQUILER:
						   	buscarAlquiler();
							  accion = null;break;

					  case MODIFICAR_CLIENTE:
						    modificarCliente();
							  accion = null;break;

//					  case DEVOLVER_ALQUILER:
//						   	devolverAlquiler();
//							  accion = null;break;
					  case DEVOLVER_ALQUILER_VEHICULO:
						   	devolverAlquilerVehiculo();
							  accion = null;break;
					  case DEVOLVER_ALQUILER_CLIENTE:
						   	devolverAlquilerCliente();
							  accion = null;break;
					  case BORRAR_CLIENTE:
						    borrarCliente();
							  accion = null;break;

					  case BORRAR_TURISMO:
						    borrarTurismo();
							  accion = null;break;

					  case BORRAR_ALQUILER:
						    borrarAlquiler();
							  accion = null;break;
					  case LISTAR_CLIENTES:
						    listarCliente();
							  accion = null;break;
					  case LISTAR_TURISMOS:
						    listarTurismo();
							  accion = null;break;
					  case LISTAR_ALQUILERES:
						    listarAlquiler();
							  accion = null;break;
					  case LISTAR_ALQUILERES_CLIENTES:
						    listarAlquileresCliente();
							  accion = null;break;
					  case LISTAR_ALQUILERES_TURISMO:
						    listarAlquileresTurismo();
							  accion = null;break;
					  case MOSTRAR_ESTADITICAS_MENSUALES:
						  mostrarEstadisticasMensualesTipoVehiculo();
						  accion = null;break;
					  default:
					   accion = Accion.SALIR;
					}
				}
			}





		} while (accion != Accion.SALIR);
		terminar();

	}

	@Override
	public void terminar() {controlador.terminar();System.out.println("Hasta aqui la ejecucion del programa");}


	protected void insertarCliente() {

		Consola.mostrarCabecera("Accion insertar cliente");
		Cliente cliente = Consola.leerCliente();

		try{controlador.insertar(cliente);}catch(Exception e) {System.out.println(e.getMessage());}
	}

	protected void insertarVehiculo() {
		Consola.mostrarCabecera("Accion insertar vehiculo");
		Vehiculo vehiculo = Consola.leerVehiculo();

		try{controlador.insertar(vehiculo);}catch(Exception e) {System.out.println(e.getMessage());}}

	protected void insertarAlquiler() {
		Consola.mostrarCabecera("Accion insertar alquiler");
		Alquiler alquiler = Consola.leerAlquiler();

		try{controlador.insertar(alquiler);}catch(Exception e) {System.out.println(e.getMessage());}}

	protected void buscarCliente() {
		Consola.mostrarCabecera("Accion buscar cliente");
		Cliente cliente = Consola.leerClienteDni();
		try{cliente=controlador.buscar(cliente);System.out.println(cliente);}catch(Exception e) {System.out.println(e.getMessage());}

	}

	protected void buscarTurismo() {
		Consola.mostrarCabecera("Accion buscar vehiculo");
		Vehiculo turismo = Consola.leerTurismoMatricula();
		try{turismo = controlador.buscar(turismo);System.out.println(turismo); }catch(Exception e) {System.out.println(e.getMessage());}
	}

	protected void buscarAlquiler() {
		Consola.mostrarCabecera("Accion buscar alquiler");
		Alquiler alquiler = Consola.leerAlquiler();
		try{alquiler = controlador.buscar(alquiler);System.out.println(alquiler);}catch(Exception e) {System.out.println(e.getMessage());}

	}

	protected void modificarCliente() {
		Consola.mostrarCabecera("Accion modificar cliente");
		Cliente cliente = Consola.leerClienteDni();
		String nombre = Consola.leerNombre();
		String telefono = Consola.leerTelefono();
		try{controlador.modificar(cliente, nombre, telefono);}catch(Exception e) {System.out.println(e.getMessage());}
	}

//	protected void devolverAlquiler() {
//		Consola.mostrarCabecera("Accion devolver alquiler");
//		Alquiler alquiler = Consola.leerAlquiler();
//		LocalDate fechaDevolucion = Consola.leerFechaDevolucion();
//		try{controlador.devolver(alquiler, fechaDevolucion);}catch(Exception e) {System.out.println(e.getMessage());}
//
//	}

	protected void devolverAlquilerCliente() {
		Consola.mostrarCabecera("Accion devolver alquiler por cliente");
		Cliente cliente = Consola.leerClienteDni();
		LocalDate fechaDevolucion = Consola.leerFechaDevolucion();
		try{controlador.devolver(cliente, fechaDevolucion);}catch(Exception e) {System.out.println(e.getMessage());}

	}
	protected void devolverAlquilerVehiculo() {
		Consola.mostrarCabecera("Accion devolver alquiler por cliente");
		Vehiculo vehiculo  = Consola.leerTurismoMatricula();
		LocalDate fechaDevolucion = Consola.leerFechaDevolucion();
		try{controlador.devolver(vehiculo,fechaDevolucion);}catch(Exception e) {System.out.println(e.getMessage());}

	}






	protected void borrarCliente() {
		Consola.mostrarCabecera("Accion borrar cliente");
		Cliente cliente = Consola.leerClienteDni();
		try{controlador.borrar(cliente);}catch(Exception e) {System.out.println(e.getMessage());}
	}

	protected void borrarTurismo() {
		Consola.mostrarCabecera("Accion borrar vehiculo");
		Vehiculo turismo = Consola.leerTurismoMatricula();
		try{controlador.borrar(turismo);}catch(Exception e) {System.out.println(e.getMessage());}
	}

	protected void borrarAlquiler() {
		Consola.mostrarCabecera("Accion borrar alquiler");
		Alquiler alquiler = Consola.leerAlquiler();
		try{controlador.borrar(alquiler);}catch(Exception e) {System.out.println(e.getMessage());}

	}

	protected void listarCliente() {
		Consola.mostrarCabecera("Accion listar clientes");
		//Cliente cliente = Consola.leerCliente();



		List<Cliente> clientes = controlador.getClientes();
		Collections.sort(clientes);
		for (Cliente cliente : clientes) {
            System.out.println(cliente); }

		//try{System.out.println(controlador.getClientes());}catch(Exception e) {System.out.println(e.getMessage());}
	}

	protected void listarTurismo() {
		Consola.mostrarCabecera("Accion listar todos los vehiculos");
		//Turismo turismo = Consola.leerTurismo();

		List<Vehiculo> vehiculos = controlador.getVehiculos();
		Collections.sort(vehiculos);
		for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo); }


		//try{System.out.println(controlador.getVehiculos());}catch(Exception e) {System.out.println(e.getMessage());}
	}

	protected void listarAlquiler() {
		Consola.mostrarCabecera("Accion listar alquileres");

		List<Alquiler> alquileres = controlador.getAlquileres();
		Collections.sort(alquileres);
		for (Alquiler alquiler : alquileres) {
        	System.out.println(alquiler); }


		//try{System.out.println(controlador.getAlquileres());}catch(Exception e) {System.out.println(e.getMessage());}

	}

	protected void listarAlquileresCliente() {
		Consola.mostrarCabecera("Accion listar alquileres de un cliente");
		Cliente cliente = Consola.leerClienteDni();


		List<Alquiler> alquileres = controlador.getAlquileres(cliente);
		Collections.sort(alquileres);
		for (Alquiler alquiler : alquileres) {
        	System.out.println(alquiler); }

	//	try{System.out.println(controlador.getAlquileres(cliente));}catch(Exception e) {System.out.println(e.getMessage());}
	}

	protected void listarAlquileresTurismo() {
		Consola.mostrarCabecera("Accion listar alquileres de un vehiculo");
		Vehiculo turismo = Consola.leerTurismoMatricula();


		List<Alquiler> alquileres = controlador.getAlquileres(turismo);
		Collections.sort(alquileres);
		for (Alquiler alquiler : alquileres) {
        	System.out.println(alquiler); }


		//try{System.out.println(controlador.getAlquileres(turismo));}catch(Exception e) {System.out.println(e.getMessage());}
	}



	private EnumMap<tipoVehiculo,Integer> inicializarEstadisticas() {
		EnumMap<tipoVehiculo,Integer> enumMapVehiculos = new EnumMap<>(tipoVehiculo.class);

		enumMapVehiculos.put(tipoVehiculo.TURISMO, 0);
		enumMapVehiculos.put(tipoVehiculo.FURGONETA, 0);
		enumMapVehiculos.put(tipoVehiculo.AUTOBUS, 0);
		return enumMapVehiculos;


	}


	public void mostrarEstadisticasMensualesTipoVehiculo() {

		EnumMap<tipoVehiculo, Integer> enumMapVehiculos = inicializarEstadisticas();
		Integer mes =Consola.leerMes("Inserte el mes para calcular las estadisticas(del 1 al 12,sin 0s)");
		List<Alquiler> alquileres = controlador.getAlquileres();
		Collections.sort(alquileres);
		//obtiene los valores para el conteo, aunque se inicializa en 0 en este caso
		//podriamos tener una app alternativo que y no se inicializaria en 0
		Integer cuentaTurismo=enumMapVehiculos.get(tipoVehiculo.TURISMO);
		Integer cuentaFurgoneta=enumMapVehiculos.get(tipoVehiculo.FURGONETA);
		Integer cuentaAutobus=enumMapVehiculos.get(tipoVehiculo.AUTOBUS);

		for (Alquiler alquiler : alquileres) {
			//comprueba que el alquiler sea del mes indicado, saca su vehiculo y comprueba el tipo de instancia
			if(alquiler.getFechaAlquiler().getMonthValue()==mes) {
				Vehiculo vehiculo=alquiler.getVehiculo();

			 if(vehiculo instanceof Turismo) {cuentaTurismo++;}
			 if(vehiculo instanceof Furgoneta) {cuentaFurgoneta++;}
			 if(vehiculo instanceof Autobus) {cuentaAutobus++;}}


		  }
		/*si quisieramos tener permanencia de  datos con las estadisticas podriamos hacer
		enumMapVehiculos.put(tipoVehiculo.TURISMO, cuentaTurismo);
		enumMapVehiculos.put(tipoVehiculo.FURGONETA, cuentaTurismo);
		enumMapVehiculos.put(tipoVehiculo.AUTOBUS, cuentaTurismo);

		y hacer un return del enumMapVehiculos para pasarlo a archivo o algo
        */

		Consola.mostrarCabecera("Las estadisticas de cada vehiculo para el mes de"
		+ Month.of(mes).name()+ "son:");
		System.out.println("Turismo: " + cuentaTurismo.toString());
		System.out.println("Furgoneta: " + cuentaFurgoneta.toString());
		System.out.println("Autobus: " + cuentaAutobus.toString());



	}





}
