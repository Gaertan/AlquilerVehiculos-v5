package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database.Alquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database.utilidades.MySQL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class ControladorAnadirAlquiler implements Initializable {
    private Alquiler registro;
	private	Controlador controller;
	private ControladorListadoAlquileres controllerListado;
    @FXML private Button btAnadir;
    @FXML private Button btCancelar;
    @FXML private Button btDevolver;
    @FXML private CheckBox cbSus;
    @FXML private DatePicker dpFechaAlquiler;
    @FXML private DatePicker dpFechaDevolucion;

    @FXML private Label lbDni;
    @FXML private Label lbFechaAlquiler;
    @FXML private Label lbFechaDevolucion;
    @FXML private Label lbTelefono;

    @FXML private ListView<Cliente> lvListadoClientes;
    @FXML private ListView<Vehiculo> lvListadoVehiculos;

    @FXML private ScrollPane spListadoClientes;
    @FXML private ScrollPane spListadoVehiculos;

    @FXML private ToggleButton tbCambiarListado;

    @FXML private TextField textoSus;
    @FXML private TextField tfDni;
    @FXML private TextField tfMatricula;
	private ObservableList<Cliente> obsClientes = FXCollections.observableArrayList();
	private ObservableList<Vehiculo> obsVehiculos = FXCollections.observableArrayList();

    @FXML
    void anadirClick(ActionEvent event) {
//    	Cliente selectedCliente = lvListadoClientes.getSelectionModel().getSelectedItem();
//    	Vehiculo selectedVehiculo = lvListadoVehiculos.getSelectionModel().getSelectedItem();
    	Cliente clienteDum = new Cliente("Andres G", tfDni.getText(),"622222222");
    	Vehiculo vehiculoDum = new Turismo("Seat","Leon",123,tfMatricula.getText());
    	Alquiler alquiler = null;
//    	Alquiler alquiler = new Alquiler(selectedCliente, selectedVehiculo, dpFechaAlquiler.getValue());

		try {
			alquiler = new Alquiler(controller.buscar(clienteDum),controller.buscar(vehiculoDum),dpFechaAlquiler.getValue());
		}
			catch (Exception e) {aviso(e.getMessage(), AlertType.ERROR);e.printStackTrace();}
    	try {
			controller.insertar(alquiler);}
			catch (Exception e) {aviso(e.getMessage(), AlertType.ERROR);e.printStackTrace();}
    	controllerListado.refrescarTabla();
    	}

    @FXML
    void buscarCliente(ActionEvent event) {
    	String dniBuscado = tfDni.getText();
    	for (Cliente cliente : lvListadoClientes.getItems()) {
    	   if (cliente.getDni().equals(dniBuscado)) {lvListadoClientes.getSelectionModel().select(cliente);
    	        break;}
    	}
    }

    @FXML
    void buscarVehiculo(ActionEvent event) {
    	String matriculaBuscada = tfMatricula.getText();
    	for (Vehiculo vehiculo : lvListadoVehiculos.getItems()) {
    	   if (vehiculo.getMatricula().equals(matriculaBuscada)) {lvListadoVehiculos.getSelectionModel().select(vehiculo);
    	        break;}
    	}
    }

    @FXML
    void cambiarListado(ActionEvent event) {
    		if(!spListadoClientes.visibleProperty().getValue()) {spListadoClientes.setVisible(true);lvListadoClientes.setVisible(true);spListadoVehiculos.setVisible(false);lvListadoVehiculos.setVisible(false);}
    		else {spListadoClientes.setVisible(false);lvListadoClientes.setVisible(false);spListadoVehiculos.setVisible(true);lvListadoVehiculos.setVisible(true);}
    }

    @FXML
    void cerrar(ActionEvent event) {
    Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();
	escenario.close();

    }

    @FXML
    void devolverAlquiler(ActionEvent event) {
    	Cliente clienteDummy = new Cliente("Andres G",tfDni.getText(),"622222222");
    	try {
			controller.devolver(clienteDummy, dpFechaDevolucion.getValue());
		} catch (Exception e) {aviso(e.getMessage(), AlertType.ERROR);}

    }

    @FXML
    void memeSus(ActionEvent event) {
    	if(textoSus.getText()==null||textoSus.getText()=="") {textoSus.setVisible(true);textoSus.setText("ඞ");}
    	else {textoSus.setVisible(false);textoSus.setText("");}


    }
	public void setControlador(Controlador controller) {this.controller=controller;}
	public void setControladorListado(ControladorListadoAlquileres controller) {this.controllerListado=controller;}

    private void aviso(String aviso, AlertType tipo)
    {
    	Alert a = new Alert(tipo);
    	a.setTitle("Aviso...");
    	a.setHeaderText(aviso);
    	a.show();
    }
    public void ocultaBotones()
    {
    	btAnadir.setVisible(false);
    	btCancelar.setVisible(false);
		spListadoClientes.setVisible(false);
		lvListadoClientes.setVisible(false);
		spListadoVehiculos.setVisible(false);
		lvListadoVehiculos.setVisible(false);
		tbCambiarListado.setVisible(false);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	tfDni.setDisable(false);
	tfMatricula.setDisable(false);
	btDevolver.setDisable(true);
	dpFechaDevolucion.setDisable(true);
		}
	private List<Cliente> getClientesLibres(){
//		List<Cliente> clientesAlquilados = new ArrayList<>();
//			for(Alquiler a:controller.getAlquileres()) {
//				if (a.getFechaDevolucion()==null)clientesAlquilados.add(a.getCliente());
//
//			}
//		List<Cliente> clientes = controller.getClientes();
//	clientes.removeAll(clientesAlquilados);
//		return clientes;
//		
	    ArrayList<Cliente> clientes = new ArrayList<>();
	    try {
	        Connection conexion = MySQL.establecerConexion(); // Método que obtiene la conexión a la base de datos
	        String sentenciaStr = "SELECT c.nombre, c.dni, c.telefono FROM clientes c " +
	                "LEFT JOIN alquileres a ON c.dni = a.dni " +
	                "WHERE a.dni IS NULL OR a.fechaDevolucion IS NOT NULL";
	        PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
	        ResultSet resultado = sentencia.executeQuery();
	        while (resultado.next()) {
	            String nombre = resultado.getString("nombre");
	            String dni = resultado.getString("dni");
	            String telefono = resultado.getString("telefono");
	            Cliente cliente = new Cliente(nombre, dni, telefono);
	            clientes.add(cliente);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return clientes;
	}
	private List<Vehiculo> getVehiculosLibres(){
//		List<Vehiculo> vehiculosAlquilados = new ArrayList<>();
//			for(Alquiler a:controller.getAlquileres()) {
//				if (a.getFechaDevolucion()==null)vehiculosAlquilados.add(a.getVehiculo());
//
//			}
//		List<Vehiculo> vehiculos = controller.getVehiculos();
//		vehiculos.removeAll(vehiculosAlquilados);
//		return vehiculos;

		
	    ArrayList<Vehiculo> vehiculos = new ArrayList<>();
	    try {
	        Connection conexion = MySQL.establecerConexion(); // Método que obtiene la conexión a la base de datos
	        String sentenciaStr = "SELECT v.modelo, v.marca, v.matricula FROM vehiculos v " +
	                "LEFT JOIN alquileres a ON v.matricula = a.matricula " +
	                "WHERE a.matricula IS NULL OR a.fechaDevolucion IS NOT NULL";
	        PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
	        ResultSet resultado = sentencia.executeQuery();
	        while (resultado.next()) {

	        	
	        	try {
					Vehiculo vehiculo = controller.buscar(new Turismo(resultado.getString("marca"),resultado.getString("modelo"),123,resultado.getString("matricula")));
					vehiculos.add(vehiculo);
				} catch (OperationNotSupportedException e) {
					e.printStackTrace();
				}
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return vehiculos;
		
		
		
		
		
		
		
		
	}



public void setListado() {
	this.obsClientes.setAll(getClientesLibres());
	this.obsVehiculos.setAll(getVehiculosLibres());

	lvListadoClientes.setItems(obsClientes);
	lvListadoVehiculos.setItems(obsVehiculos);

    lvListadoClientes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    lvListadoVehiculos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    lvListadoClientes.setCellFactory(param -> new ListCell<>() {
        @Override
        protected void updateItem(Cliente cliente, boolean empty) {
            super.updateItem(cliente, empty);
            if (empty || cliente == null) {
                setText(null);
            } else {
                setText(cliente.getNombre() + " - " + cliente.getDni());
            }
        }
    });



    //listeners en ambos listados para que cuando se seleccione un objeto, se obtenga su dni y/o matricula si no es nulo
    //el valor se asigna a los textfields

   lvListadoClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cliente>() {
        @Override
        public void changed(ObservableValue<? extends Cliente> observable, Cliente oldValue, Cliente newValue) {
            if (newValue != null) { tfDni.setText(newValue.getDni()); }
        }
    });
   lvListadoVehiculos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vehiculo>() {
       @Override
       public void changed(ObservableValue<? extends Vehiculo> observable, Vehiculo oldValue, Vehiculo newValue) {
           if (newValue != null) { tfMatricula.setText(newValue.getMatricula()); }
        }
    });



    spListadoClientes.setContent(lvListadoClientes);
    spListadoVehiculos.setContent(lvListadoVehiculos);



}
//class ClienteListCell extends javafx.scene.control.ListCell<Cliente> {
//    @Override
//    protected void updateItem(Cliente cliente, boolean empty) {
//        super.updateItem(cliente, empty);
//        if (empty || cliente == null) {setText(null);}
//        else {setText(cliente.getNombre() + " (" + cliente.getDni() + ")");}
//    }
//}
//class VehiculoListCell extends javafx.scene.control.ListCell<Vehiculo> {
//    @Override
//    protected void updateItem(Vehiculo vehiculo, boolean empty) {
//        super.updateItem(vehiculo, empty);
//        if (empty || vehiculo == null) {setText(null);}
//        else {setText(vehiculo.getMarca() + " (" + vehiculo.getMatricula() + ")");}
//    }
//}
public void setRegistro(Alquiler registro) {
	this.registro=registro;
	// TODO Auto-generated method stub
	if(registro!=null) {tfDni.setDisable(true);tfMatricula.setDisable(true);dpFechaAlquiler.setDisable(true);
		lvListadoClientes.setDisable(true);lvListadoVehiculos.setDisable(true);
		if(registro.getFechaDevolucion()!=null) {dpFechaDevolucion.setDisable(true);}

	}

}
public Alquiler getRegistro() {
	return this.registro;
	// TODO Auto-generated method stub

}
public void cargaDatosAlquiler(Alquiler v)
	{
		if(v!=null) {

			this.registro = v;
	        tfMatricula.setText(v.getVehiculo().getMatricula());
	        tfDni.setText(v.getCliente().getDni());
	        dpFechaAlquiler.setValue(v.getFechaAlquiler());
	        if(v.getFechaDevolucion()!=null) {dpFechaDevolucion.setValue(v.getFechaDevolucion());dpFechaDevolucion.setDisable(true);}
			}
        if(v.getFechaDevolucion()==null)
        {dpFechaDevolucion.setDisable(false);btDevolver.setDisable(false);btDevolver.setVisible(true);}


	}



}
