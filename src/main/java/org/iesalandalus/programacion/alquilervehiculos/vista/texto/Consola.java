package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	protected static final String PATRON_FECHA = "dd/LL/yyyy" ;
	protected static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);


	private Consola() {}

	public static void mostrarCabecera(String mensaje){
		System.out.println("");System.out.println(mensaje);
		for(int i = 0;i<=mensaje.length();i++) {System.out.print("-");}
		System.out.println("");
		}

	public static void mostrarMenu(){
		mostrarCabecera("Gestión de reservas de vehiculos");
		System.out.println("");
		for (Accion accion: Accion.values()) {
			System.out.print(accion.ordinal());System.out.print("- ");System.out.println(accion);
		}
	}

	private static String leerCadena(String mensaje){
		System.out.println(mensaje);
		String cadena="";
		while(cadena ==""||cadena==null||cadena==" "||cadena.trim().isEmpty()) {cadena = Entrada.cadena();}
		return cadena;
	}

	private static Integer leerEntero(String mensaje){
		System.out.println(mensaje);
		Integer entero = -1;
		while(entero<0) {entero=Entrada.entero();}
		return entero;
	}

	private static LocalDate leerFecha(String mensaje){
		System.out.println(mensaje);
		LocalDate fecha = null;
		while(fecha==null) {
			try { LocalDate localDate = LocalDate.parse(Entrada.cadena(),FORMATO_FECHA);fecha=localDate;}
			catch(Exception e) {System.out.println("algo ha salido mal leyendo la fecha;");System.out.print(e.getMessage());}
		}
		return fecha;
	}
	static Integer leerMes(String mensaje){
		System.out.println(mensaje);
		Integer mes = null;
		while(mes==null||(mes<0||mes>12)) {
			try { mes=Entrada.entero();}
			catch(Exception e) {System.out.println("algo ha salido mal leyendo la fecha;");System.out.print(e.getMessage());}
		}
		return mes;
	}


	public static Accion elegirOpcion() throws OperationNotSupportedException{

		try{Accion opcionR = null;

			while(opcionR==null) {int ordinal = leerEntero("Introduzca el numero de la opción a ejecutar");opcionR = Accion.values()[ordinal];}
			return opcionR;

			}catch(Exception e) {System.out.println("algo ha salido mal al escoger opcion;");System.out.print(e.getMessage());}
		return null;
	}


	public static Cliente leerCliente(){
		 Cliente cliente = null;
		 String nombre ="Andrés García Gaertan";
		 String telefono = "622099498";
		 String dni;

		 do {	nombre = leerNombre();
			 	dni = leerCadena("Introduzca el dni, en mayusculas, sin espacios ni simbolos");

				telefono = leerTelefono();
				try{Cliente clienteR = new Cliente(nombre,dni,telefono);return clienteR;}
				catch(Exception e) {System.out.println("algo ha salido mal leyendo el cliente;");System.out.print(e.getMessage());}
		 }while(cliente == null);
		return cliente;
	}


	public static Cliente leerClienteDni(){
		 Cliente cliente = null;
		 String nombre ="Andres Gaertan";
		 String telefono = "622099498";
		 String dni;
		 do {
			 	dni = leerCadena("Introduzca el dni, en mayusculas, sin espacios ni simbolos");

				try{Cliente clienteR = new Cliente(nombre,dni,telefono);cliente = clienteR;return clienteR;}
				catch(Exception e) {System.out.println("algo ha salido mal leyendo el dni;");System.out.print(e.getMessage());}
		 }while(cliente == null);


		return cliente;


	}


	public static String leerNombre(){
			String nombre;
		do {System.out.print("Introduzca el nombre sin tildes/acentos ; ");
			nombre = Entrada.cadena();}
		while (nombre.equals(""));
	return nombre;
	}

	public static String leerTelefono(){
		String telefono;
	do {System.out.print("Introduzca el telefono ; ");
	telefono = Entrada.cadena();}
	while (telefono.equals(""));
	return telefono;
	}


	public static Turismo leerTurismo(){
		Turismo turismo = null;
		 String marca;
		 String modelo;
		 int cilindrada = -1;
		 String matricula;
		do {
			do {System.out.print("Introduzca el nombre de la marca ; ");
			marca = Entrada.cadena();}
			while (marca.equals(""));

			do {System.out.print("Introduzca el nombre del modelo; ");
			modelo = Entrada.cadena();}
			while (modelo.equals(""));

			do {System.out.print("Introduzca la cilindrada ; ");
			cilindrada = Entrada.entero();}
			while (cilindrada<=0);

			do {System.out.print("Introduzca la matricula (4 números y 3 letras no vocales) ; ");
			matricula = Entrada.cadena();}
			while (matricula.equals(""));

			try{Turismo turismoR = new Turismo(marca,modelo,cilindrada,matricula);turismo = turismoR;return turismo;}
			catch(Exception e) {System.out.println("algo ha salido mal leyendo el turismo;");System.out.print(e.getMessage());}}while(turismo == null);


			return turismo;


	}


	public static Turismo leerTurismoMatricula(){

		Turismo turismo = null;
		 String marca = "Seat";
		 String modelo = "Leon";
		 int cilindrada = 1000;
		 String matricula = "1111BBB";
		do { do {System.out.print("Introduzca la matricula ; ");
			matricula = Entrada.cadena();}
			while (matricula.equals(""));

			try{Turismo turismoR = new Turismo(marca,modelo,cilindrada,matricula);turismo = turismoR;return turismo;}
			catch(Exception e) {System.out.println("algo ha salido mal leyendo la matricula;");System.out.print(e.getMessage());}
		}
		while(turismo == null);



		return turismo;


	}


	public static Alquiler leerAlquiler(){
		Cliente cliente;
		Turismo turismo;
		LocalDate fechaDate;
		Alquiler alquiler = null;

		do {
				cliente = leerClienteDni();
				turismo = leerTurismoMatricula();
				fechaDate = leerFecha("A continuacion se le pide la fecha de alquiler en formato dd/mm/aaaa");


				try {Alquiler alquilerR = new Alquiler(cliente,turismo,fechaDate);alquiler=alquilerR;}
				catch(Exception e) {System.out.println("algo ha salido mal leyendo el alquiler;");System.out.print(e.getMessage());}

		}
		while(alquiler == null);

		return alquiler;

	}


	public static LocalDate leerFechaDevolucion(){
		String fecha;
		boolean status = false;
		do {
			System.out.print("Introduzca la fecha en formato dd/mm/aaaa: ");
			fecha = Entrada.cadena();
			try {LocalDate.parse(fecha, FORMATO_FECHA);status = true;}
			catch (DateTimeParseException e) {status = false;}
		} while (!status);
		return LocalDate.parse(fecha, FORMATO_FECHA);}





	private static void mostrarMenuTiposVehiculos() {
		mostrarCabecera("Estos son los tipos de vehiculos a escoger");
		System.out.println("");
		for (tipoVehiculo tipovehiculo: tipoVehiculo.values()) {
			System.out.print(tipovehiculo.ordinal());System.out.print("- ");System.out.println(tipovehiculo);
		}


	}




	private static tipoVehiculo elegirTipoVehiculo() {
		try{tipoVehiculo opcionR = null;

		while(opcionR==null) {int ordinal = leerEntero("Introduzca el numero del vehiculo a insertar");opcionR = tipoVehiculo.values()[ordinal];}
		return opcionR;

		}catch(Exception e) {System.out.println("algo ha salido mal al escoger opcion;");System.out.print(e.getMessage());}
	return null;


	}




	private static Vehiculo leerVehiculo(tipoVehiculo tipovehiculo) {
		mostrarMenuTiposVehiculos();
		 String marca;
		 String modelo;
		 int cilindrada = -1;
		 int plazas = -1;
		 int pma = -1;
		 String matricula;
		 Vehiculo vehiculo = null;
			do {
				do {System.out.print("Introduzca el nombre de la marca ; ");
				marca = Entrada.cadena();}
				while (marca.equals(""));

				do {System.out.print("Introduzca el nombre del modelo; ");
				modelo = Entrada.cadena();}
				while (modelo.equals(""));

				do {System.out.print("Introduzca la matricula (4 números y 3 letras no vocales) ; ");
				matricula = Entrada.cadena();}
				while (matricula.equals(""));



				switch(tipovehiculo) {
				case TURISMO:
					do {System.out.print("Introduzca la cilindrada en centimetros cubicos(solo el numero de esta) ; ");
					cilindrada = Entrada.entero();}
					while (cilindrada<=0);
					try { vehiculo = new Turismo(marca,modelo,cilindrada,matricula);return vehiculo;}
					catch(Exception e) {System.out.println("algo ha salido mal leyendo el turismo;");System.out.print(e.getMessage());}

					break;

				case AUTOBUS:
					do {System.out.print("Introduzca las plazas ; ");
					plazas = Entrada.entero();}
					while (plazas<=0);
					try { vehiculo = new Autobus(marca,modelo,plazas,matricula);return vehiculo;}
					catch(Exception e) {System.out.println("algo ha salido mal leyendo el autobus;");System.out.print(e.getMessage());}

					break;

				case FURGONETA:
					do {System.out.print("Introduzca las plazas ; ");
					plazas = Entrada.entero();}
					while (plazas<=0);
					do {System.out.print("Introduzca el pma (peso maximo autorizado) ; ");
					pma = Entrada.entero();}
					while (pma<=0||pma>4300);

					try { vehiculo = new Furgoneta(marca,modelo,pma,plazas,matricula);return vehiculo;}
					catch(Exception e) {System.out.println("algo ha salido mal leyendo la furgoneta;");System.out.print(e.getMessage());}

					break;




				default:
				}
			}while(vehiculo==null);
				return vehiculo;


	}

	public static Vehiculo leerVehiculo() {
		Vehiculo vehiculo;
		mostrarMenuTiposVehiculos();
		vehiculo = leerVehiculo(elegirTipoVehiculo());
		// TODO Auto-generated method stub
		return vehiculo;
	}






}
