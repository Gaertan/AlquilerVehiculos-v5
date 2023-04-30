package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.utilidades.Dialogos;

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

public class ControladorListadoClientes implements Initializable{
	@FXML private TableView<Cliente> tvClientes;
    @FXML private TableColumn<Cliente, String> nombreColumn;
    @FXML private TableColumn<Cliente, String> DNIColumn;
    @FXML private TableColumn<Cliente, String> telefonoColumn;

	private	Controlador controller;
    private List<Cliente> clientes;
    private ObservableList<Cliente> obsClientes;
    private ObservableList<Cliente> obsClientesVisible;
    private String filtro = "";
    private Cliente registro;

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
    	this.registro = this.tvClientes.getSelectionModel().getSelectedItem();
    }
    @FXML
    void deseleccionar(MouseEvent event)
    {	tvClientes.getSelectionModel().clearSelection();
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
    	this.tvClientes.getSelectionModel().select(null);
    	this.obsClientesVisible.clear();
    	for(Cliente p:controller.getClientes())
    	{
    		if(this.filtro.isEmpty() || p.getNombre().toLowerCase().contains(filtro.toLowerCase()))
    		{
    			this.obsClientesVisible.add(p);
    		}
    	}

    	this.tvClientes.refresh();
    }
	public void ocultarBtnVolver()
	{
		btVolver.setVisible(false);
	}


    @FXML
    void borrarClick(ActionEvent event)
    {
    	ObservableList<Cliente> obsTodoProfesores,obsSeleccionProfesores;

        //Obtenemos todos los clientes
    	obsTodoProfesores=tvClientes.getItems();

        //Obtenermos los clientes seleccionados
    	obsSeleccionProfesores=tvClientes.getSelectionModel().getSelectedItems();

    	if (Dialogos.mostrarDialogoConfirmacion("Borrar cliente", "¿Estás seguro de que quieres borrar el cliente?", null))
    	{
    		//Para cada profesor seleccionado lo borramos del conjunto total.
    		for (Cliente p:obsSeleccionProfesores)
    		{	try {
				controller.borrar(p);
			} catch (OperationNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    			obsTodoProfesores.remove(p);
    			clientes.remove(p);
    		}
    	}
    }

    @FXML
    void verDetallesClienteClick(ActionEvent event)
    {
    	try
        {
            //Obtenemos la vista a cargar
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../vistas/AnadirCliente.fxml"));

            Parent raiz=loader.load();

            //Creamos la escena
            Scene escena=new Scene(raiz);

            //Obtenermos el profesor seleccionado
            Cliente clienteSeleccionado=tvClientes.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado!=null)
            {
                //Pasamos al controlador de la escena el profesor seleccionado
                ControladorAnadirClienteDEPRECATED cAC=loader.getController();
                cAC.setControlador(controller);
                cAC.cargaDatosCliente(clienteSeleccionado);
                cAC.ocultaBotones();
                cAC.setRegistro(this.registro);

                //Creamos el escenario
                Stage nuevoEscenario=new Stage();
                nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
                nuevoEscenario.setTitle("Datos detallados del cliente");
                //Establecemos la escena
                nuevoEscenario.setScene(escena);
                nuevoEscenario.showAndWait();
            }
            else
                Dialogos.mostrarDialogoAdvertencia("ERROR Listado clientes", "Debe seleccionar un cliente para mostrar toda su información");
        }
        catch (IOException ex)
    	{
        	ex.printStackTrace();
        }
    }
    @FXML
    void EditCliente(ActionEvent event)
    {
    	if(this.registro == null)
    	{
    		this.aviso("No has seleccionado ninguna persona.", AlertType.ERROR);
    	}
    	else
    	{
    		try
        	{
        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vistas/formularioClienteView.fxml"));
    			Parent raiz = fxmlLoader.load();
    			Scene escena = new Scene(raiz);

    			FormularioClienteController fcc = fxmlLoader.getController();
    			fcc.setControlador(controller);
    			fcc.setListado(this.obsClientes);
    			fcc.setRegistro(this.registro);

    			Stage nuevoEscenario=new Stage();
                nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
                nuevoEscenario.setTitle("Editar Cliente...");

                nuevoEscenario.setScene(escena);
                nuevoEscenario.showAndWait();

                this.refrescarTabla();
        	}
        	catch(Exception e)
        	{
        		this.aviso(e.getMessage(), AlertType.ERROR);
        	}
    	}
    }

    @FXML
    void AddCliente(ActionEvent event)
    {
    	try
    	{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vistas/formularioClienteView.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

			FormularioClienteController fcc = fxmlLoader.getController();
			fcc.setControlador(this.controller);
			fcc.setListado(this.obsClientes);
			fcc.setRegistro(this.registro);

			Stage nuevoEscenario=new Stage();
            nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenario.setTitle("Añadir cliente...");

            nuevoEscenario.setScene(escena);
            nuevoEscenario.showAndWait();

            Cliente p = fcc.getRegistro();
            this.obsClientes.add(p);
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

    public void cargaListadoClientes(List<Cliente> coleccionClientes)
    {
    	clientes=coleccionClientes;
    	obsClientes.setAll(coleccionClientes);

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
//			nombreColumn.setCellValueFactory(cliente->new SimpleStringProperty(cliente.getValue().getNombre()));
//			DNIColumn.setCellValueFactory(cliente->new SimpleStringProperty(cliente.getValue().getDni()));
//			telefonoColumn.setCellValueFactory(cliente->new SimpleStringProperty(cliente.getValue().getTelefono()));

			nombreColumn.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombre"));
			DNIColumn.setCellValueFactory(new PropertyValueFactory<Cliente,String>("dni"));
			telefonoColumn.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefono"));
			tvClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			this.filtro = "";
			obsClientes = FXCollections.observableArrayList();
			obsClientesVisible = FXCollections.observableArrayList();
			this.tvClientes.setItems(obsClientesVisible);

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
    protected ObservableList<Cliente> getObsClientes(){return this.obsClientes;}
	public void setControlador(Controlador controller) {this.controller=controller;}
}
