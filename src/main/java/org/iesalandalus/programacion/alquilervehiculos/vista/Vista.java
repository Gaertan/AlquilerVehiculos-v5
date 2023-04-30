package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;

import javafx.application.Application;
import javafx.stage.Stage;

public abstract class Vista extends Application {

	protected Controlador controlador;

	public Vista() {
		super();
	}

	public void setControlador(Controlador controlador) {
		if (controlador == null) {throw new NullPointerException("ERROR: El controlador no puede ser nulo.");}
		this.controlador = controlador;
	}

	 public abstract void comenzar();

	public void terminar() {System.out.println("Hasta aqui la ejecucion del programa");}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

	}

}