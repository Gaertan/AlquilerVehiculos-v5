package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista;

import java.io.IOException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal {
	Controlador controller;

	ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList();
    ObservableList<Alquiler> alquileres = FXCollections.observableArrayList();

    @FXML
    private ImageView imgAlquileres = new ImageView();
    @FXML
    private ImageView imgVehiculos = new ImageView();
    @FXML
    private ImageView imgClientes = new ImageView();
	@FXML
	private Button btListadoClientes;

	@FXML
	private Button btListadoVehiculos;

	@FXML
	private Button btListadoAlquileres;

	@FXML
	public void initialize() {

		// TODO Auto-generated method stub
//		crearListaClientes();
	}

	@FXML
	void salir(ActionEvent event) {
    	Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", escenarioActual)) {
			controller.terminar();
	    	escenarioActual.close();
		}
		else {
			event.consume();
		}

	}

    @FXML
    public void mostrarAcercaDe() {
    	//aparentemente hay un error de runtime por javafx 19 vs javafx 17


//    	try {
//    		FXMLLoader loaderFxml = new FXMLLoader(getClass().getResource("../vistas/About.fxml"));
//            Parent root = loaderFxml.load();
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
	@FXML
	void listadoClientes(ActionEvent event) {
		// Cargamos el fxml y obtenemos el contenedor raíz
		try {

			FXMLLoader loaderFxml = new FXMLLoader(getClass().getResource("../vistas/ListadoClientes.fxml"));
			Parent root = loaderFxml.load();
//cargamos controlador y añadimos clientes.
			ControladorListadoClientes cListadoClientes = loaderFxml.getController();
	        cListadoClientes.setControlador(this.controller);
			cListadoClientes.cargaListadoClientes(controller.getClientes());
			cListadoClientes.ocultarBtnVolver();
			cListadoClientes.refrescarTabla();

			// Creamos la escena
			Scene escena = new Scene(root);

			// Creamos un nuevo escenario
			Stage escenarioNuevo = new Stage();
			escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
			escenarioNuevo.setTitle("ListadoClientes");
			// Le asignamos la escena y mostramos el escenario
			escenarioNuevo.setScene(escena);
			escenarioNuevo.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@FXML
	void listadoVehiculos(ActionEvent event) {
		try {

			FXMLLoader loaderFxml = new FXMLLoader(getClass().getResource("../vistas/listadoVehiculos.fxml"));
			Parent root = loaderFxml.load();

			ControladorListadoVehiculos cListadoVehiculos = loaderFxml.getController();
	        cListadoVehiculos.setControlador(this.controller);
			cListadoVehiculos.cargaListadoVehiculos(controller.getVehiculos());
			cListadoVehiculos.ocultarBtnVolver();
			cListadoVehiculos.refrescarTabla();

			// Creamos la escena
			Scene escena = new Scene(root);

			// Creamos un nuevo escenario
			Stage escenarioNuevo = new Stage();
			escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
			escenarioNuevo.setTitle("ListadoVehiculos");
			// Le asignamos la escena y mostramos el escenario
			escenarioNuevo.setScene(escena);
			escenarioNuevo.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@FXML
	void listadoAlquileres(ActionEvent event) {
		// Cargamos el fxml y obtenemos el contenedor raíz
		try {
			FXMLLoader loaderFxml = new FXMLLoader(getClass().getResource("../vistas/ListadoAlquileres.fxml"));
			Parent root = loaderFxml.load();

			ControladorListadoAlquileres cListadoAlquileres = loaderFxml.getController();
	        cListadoAlquileres.setControlador(this.controller);
			cListadoAlquileres.cargaListadoAlquileres(controller.getAlquileres());
			cListadoAlquileres.ocultarBtnVolver();
			cListadoAlquileres.refrescarTabla();

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

	public void inicializaDatosObservables() {
    	clientes.setAll(controller.getClientes());
    	vehiculos.setAll(controller.getVehiculos());
    	alquileres.setAll(controller.getAlquileres());
	}

	public void setControlador(Controlador controller) {this.controller=controller;}

	public void setImage() {
//		Image imageAlquiler = new Image(getClass().getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/imagenes/alquiler.png").toString());
//		imgAlquileres.setImage(imageAlquiler);
//		Image imageCliente = new Image("file:/AlquilerVehiculos-v0/src/main/java/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/resources/cliente.png");
//		Image imageCliente = new Image(getClass().getResourceAsStream("../resources/cliente.png"));
//
//		imgClientes.setImage(imageCliente);
//		imgClientes.setVisible(true);
//		imgClientes.setCache(true);
//		Image imageVehiculo = new Image("file:/AlquilerVehiculos-v0/src/main/java/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/resources/alquiler.png");
//		imgVehiculo.setImage(imageVehiculo);
	}





}
