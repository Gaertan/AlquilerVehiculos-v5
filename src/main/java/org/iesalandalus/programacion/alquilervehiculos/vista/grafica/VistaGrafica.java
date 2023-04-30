package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista.ControladorVentanaPrincipal;
import org.iesalandalus.programacion.utilidades.Dialogos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaGrafica extends Vista {


	protected Controlador controller = Controlador.getInstance();
    private List<Cliente> clientes;

	private javafx.stage.Stage stage;

	public VistaGrafica() {

	}

	@Override
	public void start(Stage stage) throws Exception {
		try
		{
			//BorderPane root = new BorderPane();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("./vistas/VentanaPrincipal.fxml"));
			Parent root=loader.load();
			//Parent root = FXMLLoader.load(getClass().getResource("../vistas/VentanaPrincipal.fxml"));
	        ControladorVentanaPrincipal controladorVentanaPrincipal = loader.getController();
	        controladorVentanaPrincipal.setControlador(Controlador.getInstance());
	        controladorVentanaPrincipal.inicializaDatosObservables();
	        controladorVentanaPrincipal.setImage();
//	        controladorVentanaPrincipal.crearListaClientes();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("./vistas/iugventanas.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Escenario Principal");
			stage.setOnCloseRequest(e -> confirmarSalida(stage, e));
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", escenarioPrincipal)) {
			controller.terminar();
			escenarioPrincipal.close();
		}
		else {
			e.consume();
		}
	}



	@Override
	public void comenzar() {
		// TODO Auto-generated method stub

		launch();
		stage = new Stage();
		try {
			start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
