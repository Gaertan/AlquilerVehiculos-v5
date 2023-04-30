package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormularioClienteController {

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField campoDni;

    @FXML
    private TextField campoTelefono;

    @FXML
    private TextField campoNombre;

    @FXML
    private Label labelTitulo;

    private ObservableList<Cliente> listaPersonas;

    private Cliente registro;

	private Controlador controller;

    public void setListado(ObservableList<Cliente> listaPersonas)
    {
    	this.listaPersonas = listaPersonas;
    }

    public void setRegistro(Cliente p)
    {
    	this.registro = p;

    	if(this.registro!=null)
    	{
    		labelTitulo.setText("Editar persona");
    		this.campoNombre.setText(this.registro.getNombre());
    		this.campoDni.setText(this.registro.getDni());
    		this.campoTelefono.setText(this.registro.getTelefono());
    	}
    	else
    	{
    		labelTitulo.setText("Añadir persona");
    	}
    }

    public Cliente getRegistro()
    {
    	return this.registro;
    }

    @FXML
    void Guardar(ActionEvent event)
    {
    	try
    	{
	    	String nombre = this.campoNombre.getText();
	    	String dni = this.campoDni.getText();
	    	String telefono = this.campoTelefono.getText();

	    	if(this.registro == null) // Añadimos una nueva persona
	    	{
	    		Cliente p = new Cliente(nombre, dni, telefono);

		    	if(!this.controller.getClientes().contains(p))
		    	{   controller.insertar(p);
		    		this.aviso("Cliente añadido correctamente", AlertType.CONFIRMATION);
		        	this.registro = p;

		        	Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
			    	escenarioActual.close();
		    	}
		    	else
		    	{
		    		this.aviso("La persona ya existe.", AlertType.ERROR);
		    	}
	    	}
	    	else // Modificamos una persona existente
	    	{
	    		this.registro.setNombre(nombre);
	        	this.registro.setDni(dni);
	        	this.registro.setTelefono(telefono);
	        	try {
					controller.modificar(registro, nombre, telefono);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	this.aviso("Persona modificada correctamente.", AlertType.CONFIRMATION);
	        	Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    	escenarioActual.close();
	        }
    	}
    	catch(NumberFormatException e)
    	{
    		this.aviso("La edad tiene que ser un número.", AlertType.ERROR);
    	}
    	catch(Exception e)
    	{
    		this.aviso(e.getMessage(), AlertType.ERROR);
    	}
    }

    private void aviso(String aviso, AlertType tipo)
    {
    	Alert a = new Alert(tipo);
    	a.setTitle("Aviso...");
    	a.setHeaderText(aviso);
    	a.show();
    }
	public void setControlador(Controlador controller) {this.controller=controller;}
}
