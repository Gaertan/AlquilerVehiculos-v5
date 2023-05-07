package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorListadoVehiculos implements Initializable{
	@FXML private TableView<Vehiculo> tvVehiculos;
    @FXML private TableColumn<Vehiculo, String> marcaColumn;
    @FXML private TableColumn<Vehiculo, String> modeloColumn;
    @FXML private TableColumn<Vehiculo, String> matriculaColumn;
    @FXML TableColumn<Vehiculo, String> tipoColumn = new TableColumn<>("tipo");

	private	Controlador controller;
//    private ObservableList<Vehiculo> obsVehiculos;
    private ObservableList<Vehiculo> obsVehiculosVisible;
    private String filtro = "";
    private Vehiculo registro;

    @FXML
    private Button btVolver;
    @FXML
    private Button btEdit;
    @FXML
    private Button btAnadir;
    @FXML
    private Button btBorrar;
    @FXML
    private TextField campoBuscar;


    @FXML
    void Seleccionar(MouseEvent event)
    {
    	this.registro = this.tvVehiculos.getSelectionModel().getSelectedItem();
    }
    @FXML
    void deseleccionar(MouseEvent event)
    {	tvVehiculos.getSelectionModel().clearSelection();
    	this.registro = null;
    }
    void deseleccionar()
    {	tvVehiculos.getSelectionModel().clearSelection();
    	this.registro = null;
    }
    @FXML
    void Buscar(KeyEvent event)
    {
    	this.filtro = this.campoBuscar.getText();
    	this.refrescarTabla();
    }
    protected void refrescarTabla()
    {
    	this.registro = null;
    	this.tvVehiculos.getSelectionModel().select(null);
    	this.obsVehiculosVisible.clear();
    	for(Vehiculo v:controller.getVehiculos())
    	{
    		if(this.filtro.isEmpty()|| v.getMatricula().toLowerCase().contains(filtro.toLowerCase())||v.getMarca().toLowerCase().contains(filtro.toLowerCase())|| v.getModelo().toLowerCase().contains(filtro.toLowerCase()))
    		{
    			this.obsVehiculosVisible.add(v);
    		}
    	}
		this.tvVehiculos.setItems(obsVehiculosVisible);
    	this.tvVehiculos.refresh();
    }
	public void ocultarBtnVolver()
	{
		btVolver.setVisible(false);
	}


    @FXML
    void borrarClick(ActionEvent event)
    {
        //Obtenermos los clientes seleccionados
    	 List<Vehiculo> selectedItems=tvVehiculos.getSelectionModel().getSelectedItems();

    	if (Dialogos.mostrarDialogoConfirmacion("Borrar vehiculo", "¿Estás seguro de que quieres borrar el vehiculo?", null))
    	{
    		//Para cada profesor seleccionado lo borramos del conjunto total.
    		for (Vehiculo v:selectedItems)
    		{	try {
				controller.borrar((v));
			} catch (OperationNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		}
    	}refrescarTabla();
    }

    @FXML
    void verDetallesVehiculo(ActionEvent event)
    {
    	try
        {
            //Obtenemos la vista a cargar
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../vistas/AnadirVehiculo.fxml"));

            Parent raiz=loader.load();

            //Creamos la escena
            Scene escena=new Scene(raiz);

            //Obtenermos el profesor seleccionado
            Vehiculo vehiculoSeleccionado=tvVehiculos.getSelectionModel().getSelectedItem();

            if (vehiculoSeleccionado!=null)
            {
                //Pasamos al controlador de la escena el profesor seleccionado
                ControladorAnadirVehiculo cAnadirVehiculo=loader.getController();
                cAnadirVehiculo.cargaDatosVehiculo(vehiculoSeleccionado);
                cAnadirVehiculo.ocultaBotones();

                //Creamos el escenario
                Stage nuevoEscenario=new Stage();
                nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
                nuevoEscenario.setTitle("Datos detallados del vehiculo");
                //Establecemos la escena
                nuevoEscenario.setScene(escena);
                nuevoEscenario.showAndWait();
            }
            else
                Dialogos.mostrarDialogoAdvertencia("ERROR Listado vehiculos", "Debe seleccionar un vehiculo para mostrar toda su información");
        }
        catch (IOException ex)
    	{
        	ex.printStackTrace();
        }
    }


    @FXML
    void AddVehiculo(ActionEvent event)
    {	deseleccionar();
    	try
    	{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vistas/AnadirVehiculo.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

            ControladorAnadirVehiculo cAnadirVehiculo=fxmlLoader.getController();
            cAnadirVehiculo.setControlador(this.controller);
//            cAnadirVehiculo.setListado(this.obsVehiculos);
//            cAnadirVehiculo.setRegistro(this.registro);
			Stage nuevoEscenario=new Stage();
            nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenario.setTitle("Añadir vehiculo...");

            nuevoEscenario.setScene(escena);
            nuevoEscenario.showAndWait();

//            Vehiculo v = cAnadirVehiculo.getRegistro();
//            this.obsVehiculos.add(v);
            this.refrescarTabla();
    	}
    	catch(Exception e)
    	{
    		this.aviso(e.getMessage(), AlertType.ERROR);
    	}
    }

    @FXML
    void volverClick(ActionEvent event)
    {
    	try
    	{
			// Cargamos el fxml y obtenemos el contenedor raíz
			Parent root = FXMLLoader.load(getClass().getResource("../vistas/VentanaPrincipal.fxml"));

			// Creamos la escena
			Scene escena = new Scene(root);

			// Mostrar la escena en el escenario
			// Primero debo obtener el escenario
			Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();

			escenario.setScene(escena);
			escenario.setTitle("Ejemplo en el mismo escenario");
			escenario.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void cargaListadoVehiculos(List<Vehiculo> coleccionVehiculos)
    {
//    	obsVehiculos.setAll(coleccionVehiculos);
		this.tvVehiculos.setItems(obsVehiculosVisible);

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
//			nombreColumn.setCellValueFactory(cliente->new SimpleStringProperty(cliente.getValue().getNombre()));
//			DNIColumn.setCellValueFactory(cliente->new SimpleStringProperty(cliente.getValue().getDni()));
//			telefonoColumn.setCellValueFactory(cliente->new SimpleStringProperty(cliente.getValue().getTelefono()));

			marcaColumn.setCellValueFactory(new PropertyValueFactory<Vehiculo,String>("marca"));
			modeloColumn.setCellValueFactory(new PropertyValueFactory<Vehiculo,String>("modelo"));
			matriculaColumn.setCellValueFactory(new PropertyValueFactory<Vehiculo,String>("matricula"));

			tipoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
			tvVehiculos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			this.filtro = "";
//			obsVehiculos = FXCollections.observableArrayList();
			obsVehiculosVisible = FXCollections.observableArrayList();
			this.tvVehiculos.setItems(obsVehiculosVisible);

//	        tvClientes.setItems(obsClientes);
//			this.refrescarTabla();

		}

    private void aviso(String aviso, AlertType tipo)
    {
    	Alert a = new Alert(tipo);
    	a.setTitle("Aviso...");
    	a.setHeaderText(aviso);
    	a.show();
    }
//    @FXML
//    public void exitApplication(ActionEvent event) {
//    	clientes = obsClientes;
//       Platform.exit();
//    }
//    protected ObservableList<Vehiculo> getObsVehiculos(){return this.obsVehiculos;}
	public void setControlador(Controlador controller) {this.controller=controller;}
}
