package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class  Controlador {
    private static Controlador instancia = null;
	private Vista vistaTexto;
	private Modelo modeloCascada;
    private Controlador(Modelo modeloCascada, Vista vistaTexto) {
        if(modeloCascada != null && vistaTexto != null) {
            this.modeloCascada = modeloCascada;
            this.vistaTexto = vistaTexto;
        }
        vistaTexto.setControlador(this);
    }



	public void  comenzar(){modeloCascada.comenzar();vistaTexto.comenzar();}

	public void  terminar(){modeloCascada.terminar();vistaTexto.terminar();}

	public void  insertar(Cliente cliente) throws OperationNotSupportedException{modeloCascada.insertar(cliente);}

	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException{modeloCascada.insertar(vehiculo);}

	public void  insertar(Alquiler alquiler) throws OperationNotSupportedException{modeloCascada.insertar(alquiler);}

	public Cliente buscar(Cliente cliente) throws OperationNotSupportedException{return modeloCascada.buscar(cliente);}

	public Vehiculo buscar(Vehiculo vehiculo) throws OperationNotSupportedException{return modeloCascada.buscar(vehiculo);}

	public Alquiler buscar(Alquiler alquiler) throws OperationNotSupportedException{return modeloCascada.buscar(alquiler);}

	public void  modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException{modeloCascada.modificar(cliente, nombre, telefono);}

//	public void  devolver(Alquiler alquiler, LocalDate fechaDevolucion){try {
//		modeloCascada.devolver(alquiler, fechaDevolucion);
//	} catch (NullPointerException e) {
//		// TODO Auto-generated catch block
//		System.out.println(e.getMessage());
//	} catch (OperationNotSupportedException e) {
//		// TODO Auto-generated catch block
//		System.out.println(e.getMessage());
//	}}
	public void  devolver(Cliente cliente, LocalDate fechaDevolucion){try {
		modeloCascada.devolver(cliente, fechaDevolucion);
	} catch (NullPointerException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	} catch (OperationNotSupportedException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}}
	public void  devolver(Vehiculo vehiculo , LocalDate fechaDevolucion){try {
		modeloCascada.devolver(vehiculo, fechaDevolucion);
	} catch (NullPointerException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	} catch (OperationNotSupportedException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}}
	public void  borrar(Cliente cliente) throws OperationNotSupportedException{modeloCascada.borrar(cliente);}

	public void  borrar(Vehiculo vehiculo) throws OperationNotSupportedException{modeloCascada.borrar(vehiculo);}

	public void  borrar(Alquiler alquiler) throws OperationNotSupportedException{modeloCascada.borrar(alquiler);}

	public List<Cliente> getClientes(){return modeloCascada.getClientes();}

	public List<Vehiculo> getVehiculos(){return modeloCascada.getVehiculos();}

	public List<Alquiler> getAlquileres(){return modeloCascada.getAlquileres();}

	public List<Alquiler> getAlquileres(Cliente cliente){return modeloCascada.getAlquileres(cliente);}

	public List<Alquiler> getAlquileres(Vehiculo vehiculo){return modeloCascada.getAlquileres(vehiculo);}



    public static Controlador getInstance(Modelo modeloCascada, Vista vistaTexto) {
        if(instancia == null) {instancia = new Controlador(modeloCascada, vistaTexto);}
		return instancia;

    }
    public static Controlador getInstance() {
		return instancia;

    }

}
