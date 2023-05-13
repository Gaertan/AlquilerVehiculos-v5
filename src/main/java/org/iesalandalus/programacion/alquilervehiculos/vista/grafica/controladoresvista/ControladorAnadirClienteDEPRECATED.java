package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista;

import java.io.IOException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorAnadirClienteDEPRECATED{

	private ObservableList<Cliente> obsClientes;

    @FXML
    private Button btAlquileres;
    @FXML
    private Button btAnadir;

    @FXML
    private Button btCancelar;

    @FXML
    private Label lbDni;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbTelefono;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

	private Controlador controller;
    private Cliente registro;


	@FXML
	public void initialize() {
		// TODO Auto-generated method stub

	}

	public void setClientes(ObservableList<Cliente> clientes)
	{
		this.obsClientes = clientes;
	}
    public void setRegistro(Cliente p)
    {
    	this.registro = p;
    }
	public void cargaDatosCliente(Cliente p)
	{
		tfNombre.setText(p.getNombre());

        tfDni.setText(p.getDni());

        tfTelefono.setText(p.getTelefono());


	}

	public void inicializaCampos()
	{
		//Habilitamos los campos
		tfNombre.setDisable(false);
        tfDni.setDisable(false);
        tfTelefono.setDisable(false);


        //Cargamos los valores en el control lista del Ciclo Formativo
        //cbCicloFormativo.setItems(FXCollections.observableArrayList(Ciclo.values()));
	}


	@FXML
    void anadirClick(ActionEvent event)
	{
    }
    @FXML
    void verAlquileres(ActionEvent event) {
		// Cargamos el fxml y obtenemos el contenedor raíz
		try {

			// Parent
			// root=FXMLLoader.load(getClass().getResource("../vista/ListadoProfesores.fxml"));
			FXMLLoader loaderFxml = new FXMLLoader(getClass().getResource("../vistas/ListadoAlquileres.fxml"));
			Parent root = loaderFxml.load();

			ControladorListadoAlquileres cListadoAlquileres = loaderFxml.getController();
	        cListadoAlquileres.setControlador(this.controller);
			cListadoAlquileres.cargaListadoAlquileres(controller.getAlquileres(registro));
			cListadoAlquileres.ocultarBtnVolver();
			cListadoAlquileres.ocultarBtnAdd();
//			cListadoAlquileres.refrescarTabla();
			cListadoAlquileres.setBuscar(registro.getDni());
			// Creamos la escena
			Scene escena = new Scene(root);

			// Creamos un nuevo escenario
			Stage escenarioNuevo = new Stage();
			escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
			escenarioNuevo.setTitle("Listado Alquileres");
			// Le asignamos la escena y mostramos el escenario
			escenarioNuevo.setScene(escena);
			escenarioNuevo.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void cerrar(ActionEvent event)
    {
    	Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	escenario.close();
    }

    public void ocultaBotones()
    {
    	btAnadir.setVisible(false);
    	btCancelar.setVisible(false);
    }
	public void setControlador(Controlador controller) {this.controller=controller;}
}
