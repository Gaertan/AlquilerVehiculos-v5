package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.VistaTexto;
import org.iesalandalus.programacion.utilidades.Entrada;

public class MainApp {

	
	public static void modoGraficoMysql(){	
	Vista vista = new VistaGrafica();
	Modelo modeloCascada = new ModeloCascada(FactoriaFuenteDatos.DATABASE);
	Controlador controlador = Controlador.getInstance(modeloCascada, vista);
	controlador.comenzar();
	}
	
	public static void modoTextoFicheros() {
		
		Vista vistaTexto = new VistaTexto();
		Modelo modeloCascada = new ModeloCascada(FactoriaFuenteDatos.FICHEROS);
		Controlador controlador = Controlador.getInstance(modeloCascada, vistaTexto);
		controlador.comenzar();
	
		
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
		
		int input = 0;
		do {
			System.out.println();
		System.out.println("Escoja el modo de ejecución; ");
		System.out.println("mediante modo textual con archivos(introduzca 1 como input)");
		System.out.println("mediante modo grafico y base de datos(introduzca 2 como input)");
		System.out.println();
		input = Entrada.entero();
		}
		while (input >2||input<1);
		
		if(input==1) {modoTextoFicheros();}
		if(input==2) {modoGraficoMysql();}





	}

}
