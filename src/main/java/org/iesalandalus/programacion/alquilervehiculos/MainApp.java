package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;

public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		/*ya que aun no tenemos el resto de opciones del patron factoria se pasarça directamente la vista texto y el modelo de memoria*/
		Vista vistaTexto = new VistaGrafica();
		Modelo modeloCascada = new ModeloCascada(FactoriaFuenteDatos.FICHEROS);
//		Controlador controlador = new Controlador(modeloCascada, vistaTexto);
		Controlador controlador = Controlador.getInstance(modeloCascada, vistaTexto);
		controlador.comenzar();






	}

}
