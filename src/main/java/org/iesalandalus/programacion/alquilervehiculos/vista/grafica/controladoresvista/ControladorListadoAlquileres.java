package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
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

public class ControladorListadoAlquileres implements Initializable{
	@FXML private TableView<Alquiler> tvAlquileres;
    @FXML private TableColumn<Alquiler, String> tcDevuelto;
    @FXML private TableColumn<Alquiler,String> tcDni;
    @FXML private TableColumn<Alquiler, LocalDate> tcFecha;
    @FXML private TableColumn<Alquiler, String> tcMatricula;
    @FXML private TableColumn<Alquiler, String> tcNombre;
    @FXML private TableColumn<Alquiler, String> tcTipo;

	private	Controlador controller;
    private List<Alquiler> alquileres;
    private ObservableList<Alquiler>obsAlquileres = FXCollections.observableArrayList();
    private ObservableList<Alquiler>obsAlquileresVisible = FXCollections.observableArrayList();
    private String filtro = "";
    private Alquiler registro;

    @FXML
    private Button btVolver;
    @FXML
    private Button btEdit;
    @FXML
    private Button btAnadir;
    @FXML
    private Button btBorrar;
    @FXML
    private Button btVerAlquiler;
    @FXML
    private TextField campoBuscar;


    @FXML
    void Seleccionar(MouseEvent event)
    {
    	this.registro = this.tvAlquileres.getSelectionModel().getSelectedItem();
    }
    @FXML
    void deseleccionar(MouseEvent event)
    {	tvAlquileres.getSelectionModel().clearSelection();
    	this.registro = null;
    }
    @FXML
    void Buscar(KeyEvent event)
    {
    	this.filtro = this.campoBuscar.getText();
    	this.refrescarTabla();
    }
    public void refrescarTabla()
    {
    	this.registro = null;
    	this.tvAlquileres.getSelectionModel().select(null);
    	this.obsAlquileresVisible.clear();
    	for(Alquiler v:obsAlquileres)
    	{
    		if(this.filtro.isEmpty()||this.filtro.equals("")||this.filtro==null||
    				v.getFechaAlquiler().toString().toLowerCase().contains(filtro.toLowerCase())||
    				v.getVehiculo().getMatricula().toLowerCase().contains(filtro.toLowerCase())||
    				v.getVehiculo().getMarca().toLowerCase().contains(filtro.toLowerCase())||
    				v.getCliente().getNombre().toLowerCase().contains(filtro.toLowerCase())||
    				v.getCliente().getDni().toLowerCase().contains(filtro.toLowerCase())||
    				v.getVehiculo().getClass().getSimpleName().contains(filtro.toLowerCase()))

    		{
    			this.obsAlquileresVisible.add(v);
    		}
    		if((this.filtro.equalsIgnoreCase("no devuelto")||this.filtro.contains("no devuelt")) &&v.getFechaDevolucion()==null) {this.obsAlquileresVisible.add(v);}
    	}

    	this.tvAlquileres.refresh();
    }
	public void ocultarBtnVolver()
	{
		btVolver.setVisible(false);
	}

	public void ocultarBtnAdd()
	{
		btAnadir.setVisible(false);
	}

    @FXML
    void borrarClick(ActionEvent event)
    {
    	ObservableList<Alquiler> obsTodoVehiculos,obsSeleccionVehiculos;

        //Obtenemos todos los clientes
    	obsTodoVehiculos=tvAlquileres.getItems();

        //Obtenermos los clientes seleccionados
    	obsSeleccionVehiculos=tvAlquileres.getSelectionModel().getSelectedItems();

    	if (Dialogos.mostrarDialogoConfirmacion("Borrar Alquiler", "¿Estás seguro de que quieres borrar el alquiler?", null))
    	{
    		//Para cada profesor seleccionado lo borramos del conjunto total.
    		for (Alquiler v:obsSeleccionVehiculos)
    		{	try {
				controller.borrar(v);
			} catch (OperationNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}try {
				controller.borrar(v);
			} catch (OperationNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    			obsTodoVehiculos.remove(v);

    		}
    	}
    	this.refrescarTabla();
    }

    @FXML
    void verDetallesAlquiler(ActionEvent event)
    {
    	try
        {
            //Obtenemos la vista a cargar
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../vistas/AnadirAlquiler.fxml"));

            Parent raiz=loader.load();

            //Creamos la escena
            Scene escena=new Scene(raiz);

            //Obtenermos el profesor seleccionado
            Alquiler vehiculoSeleccionado=tvAlquileres.getSelectionModel().getSelectedItem();

            if (vehiculoSeleccionado!=null)
            {
                //Pasamos al controlador de la escena el profesor seleccionado
                ControladorAnadirAlquiler cAnadirAlquiler=loader.getController();
                cAnadirAlquiler.setControlador(this.controller);
                cAnadirAlquiler.cargaDatosAlquiler(vehiculoSeleccionado);
                cAnadirAlquiler.ocultaBotones();

                //Creamos el escenario
                Stage nuevoEscenario=new Stage();
                nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
                nuevoEscenario.setTitle("Datos detallados del alquiler");
                //Establecemos la escena
                nuevoEscenario.setScene(escena);
                this.refrescarTabla();
                nuevoEscenario.showAndWait();
            }
            else
                Dialogos.mostrarDialogoAdvertencia("ERROR Listado alquileres", "Debe seleccionar un alquiler para mostrar toda su información");
        }
        catch (IOException ex)
    	{
        	ex.printStackTrace();
        }
    }


    @FXML
    void AddAlquiler(ActionEvent event)
    {
    	try
    	{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vistas/AnadirAlquiler.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

            ControladorAnadirAlquiler cAnadirAlquiler=fxmlLoader.getController();
            cAnadirAlquiler.setControlador(this.controller);
            cAnadirAlquiler.setControladorListado(this);
            cAnadirAlquiler.setListado(this.obsAlquileres);
            cAnadirAlquiler.setRegistro(this.registro);
			Stage nuevoEscenario=new Stage();
            nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenario.setTitle("Añadir alquiler...");

            nuevoEscenario.setScene(escena);
            nuevoEscenario.showAndWait();

            Alquiler v = cAnadirAlquiler.getRegistro();
//            controller.insertar(v);
            this.obsAlquileres.add(v);
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

    public void cargaListadoAlquileres(List<Alquiler> coleccionAlquileres)
    {
    	alquileres=coleccionAlquileres;
    	obsAlquileres.setAll(coleccionAlquileres);
    	obsAlquileresVisible.setAll(coleccionAlquileres);
        this.refrescarTabla();

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	    tcDevuelto.setText("Devuelto");
	    // value factories:
	    //pone devuelto si la fecha devol. es nula
	    tcDevuelto.setCellValueFactory(cellData -> {
	        Alquiler alquiler = cellData.getValue();
	        if (alquiler.getFechaDevolucion() != null) {
	            return new SimpleStringProperty("devuelto");
	        } else {
	            return new SimpleStringProperty("no devuelto");
	        }
	    });
	    // obtiene nombre y dnis del cliente
	    tcDni.setCellValueFactory(cellData -> {
	        Cliente cliente = cellData.getValue().getCliente();
	        return new SimpleStringProperty(cliente.getDni());
	    });
	    tcNombre.setCellValueFactory(cellData -> {
	        Cliente cliente = cellData.getValue().getCliente();
	        return new SimpleStringProperty(cliente.getNombre());
	    });
	    //obtiene matricula y clase del vehiculo(tipo)
	    tcMatricula.setCellValueFactory(cellData -> {
	        Vehiculo vehiculo = cellData.getValue().getVehiculo();
	        return new SimpleStringProperty(vehiculo.getMatricula());
	    });
	    tcTipo.setCellValueFactory(cellData -> {
	        Vehiculo vehiculo = cellData.getValue().getVehiculo();
	        return new SimpleStringProperty(vehiculo.getClass().getSimpleName());
	    });

	    tcFecha.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
			tvAlquileres.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			this.filtro = "";

			this.tvAlquileres.setItems(obsAlquileresVisible);

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
    protected ObservableList<Alquiler> getObsVehiculos(){return this.obsAlquileres;}
	public void setControlador(Controlador controller) {this.controller=controller;}
}
