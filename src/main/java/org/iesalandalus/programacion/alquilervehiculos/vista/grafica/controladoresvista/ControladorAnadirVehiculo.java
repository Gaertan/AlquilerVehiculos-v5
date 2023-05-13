package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladoresvista;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Dialogos;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ControladorAnadirVehiculo{
	private Controlador controller;
    private static final int CILINDRADA_MAX_VALUE = 1000;
//	private ObservableList<Vehiculo> obsVehiculos;
	private Vehiculo v;

    @FXML
    private Button btAnadir;
    @FXML
    private Button btCancelar;
    @FXML
    private Label lbMarca;
    @FXML
    private Label lbModelo;
    @FXML
    private Label lbMatricula;
    @FXML
    private Label lbCilindrada;
    @FXML
    private Label lbTipo;
    @FXML
    private Label lbPma;
    @FXML
    private Label lbPlazas;
    @FXML
    private Label lbNombre1;
    @FXML
    private Label lbPMA;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfPlazas;
    @FXML
    private TextField tfPlazasFurgoneta;
    @FXML
    private TextField tfPma;
    @FXML
    ChoiceBox<String> cbTipo = new ChoiceBox<>();
    @FXML
    private TabPane tpTipos;
    @FXML
    private Tab tptFurgoneta;
    @FXML
    private Tab tptTurismo;
    @FXML
    private Tab tptAutobus;
    @FXML
    private Slider slideCilindrada;
    @FXML
    private TextField tfCilindrada;




	@FXML
	public void initialize() {
		// TODO Auto-generated method stub
		initializeCilindradaBinding();
		//ya que el scenebuilder no soporta cambiar el evento desde su interfaz hay que vincularlo manualmente
		cbTipo.setOnAction(this::seleccionTipo);
		//si tuvieramos las subclases en una enum o array se podria hacer un bucle, si fueran muchos tipos de vehiculo
		cbTipo.getItems().add("Turismo");
		cbTipo.getItems().add("Furgoneta");
		cbTipo.getItems().add("Autobus");
        cbTipo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Furgoneta":
                        tpTipos.getSelectionModel().select(tptFurgoneta);
                        break;
                    case "Turismo":
                        tpTipos.getSelectionModel().select(tptTurismo);
                        break;
                    case "Autobus":
                        tpTipos.getSelectionModel().select(tptAutobus);
                        break;
                    default:
                        break;
                }
            }});
		tfCilindrada.setDisable(true);slideCilindrada.setDisable(true); 
        
	}

//	public void setVehiculos(ObservableList<Vehiculo> vehiculos)
//	{
//		this.obsVehiculos = vehiculos;
//	}
	public void cargaDatosVehiculo(Vehiculo v)
	//obtiene el vehiculo, dependiendo de la subclase, cambia el CB para activar su pestaña correspondiente.
	{
		if(v!=null) {

			this.v = v;
			tfMarca.setText(v.getMarca());
	        tfModelo.setText(v.getModelo());
	        tfMatricula.setText(v.getMatricula());
	        cbTipo.setValue(v.getClass().getSimpleName());
            tfCilindrada.setDisable(true);
            slideCilindrada.setDisable(true);

	        if (v instanceof Turismo) {
	            tfCilindrada.setText(Integer.toString(((Turismo) v).getCilindrada()));
	            slideCilindrada.setValue(((Turismo) v).getCilindrada());
//	    	    tptTurismo.setDisable(false);

	        } else if (v instanceof Furgoneta) {
	        	tfPma.setText(Integer.toString( ((Furgoneta) v).getPma() ));
	        	tfPlazasFurgoneta.setText(Integer.toString( ((Furgoneta) v).getPlazas() ));


	        } else if (v instanceof Autobus) {
	        	tfPlazas.setText(Integer.toString( ((Autobus) v).getPlazas() ));

	        } else {

	        }
	        cbTipo.setDisable(true);

		}


	}
//	@FXML
	private void seleccionTipo(ActionEvent event) {
	    String tipo = cbTipo.getValue();
	    if(v==null) {tfMarca.setDisable(false);tfModelo.setDisable(false);tfMatricula.setDisable(false);}
	    if (tipo.equalsIgnoreCase("Turismo")) {
	        tptTurismo.setDisable(false);
	        tptFurgoneta.setDisable(true);
	        tptAutobus.setDisable(true);
	        if(v==null) {tfCilindrada.setDisable(false);slideCilindrada.setDisable(false);}
	    } else if (tipo.equalsIgnoreCase("Furgoneta")) {
	        tptTurismo.setDisable(true);
	        tptFurgoneta.setDisable(false);
	        tptAutobus.setDisable(true);
	        if(v==null) {tfPlazasFurgoneta.setDisable(false);}
	        if(v==null) {tfPma.setDisable(false);}
	    } else if (tipo.equalsIgnoreCase("Autobus")) {
	        tptTurismo.setDisable(true);
	        tptFurgoneta.setDisable(true);
	        tptAutobus.setDisable(false);
	        if(v==null) {tfPlazas.setDisable(false);}
	    }
	    else {
	    tptTurismo.setDisable(true);
        tptFurgoneta.setDisable(true);
        tptAutobus.setDisable(true);}
	}






	public void inicializaCampos()
	{
//		//Habilitamos los campos comunes

        slideCilindrada.setMax(CILINDRADA_MAX_VALUE);
        //bindeamos la choicebox con las tabs
        tptTurismo.disableProperty().bind(Bindings.notEqual(cbTipo.valueProperty(), "Turismo"));
        tptFurgoneta.disableProperty().bind(Bindings.notEqual(cbTipo.valueProperty(), "Furgoneta"));
        tptAutobus.disableProperty().bind(Bindings.notEqual(cbTipo.valueProperty(), "Autobus"));



        //Cargamos los valores en el control lista del Ciclo Formativo
        //cbCicloFormativo.setItems(FXCollections.observableArrayList(Ciclo.values()));
	}


	@FXML
    void anadirClick(ActionEvent event)
	{
		try
        {    String tipoVehiculo = cbTipo.getValue();
        	switch(tipoVehiculo) {
        case "Turismo":
            Turismo turismo = new Turismo(tfMarca.getText(), tfModelo.getText(),(int)Math.floor(Double.parseDouble(tfCilindrada.getText())), tfMatricula.getText());
            // Configura las propiedades del objeto Turismo según la entrada del usuario
            // ...
            controller.insertar(turismo);
            break;
        case "Furgoneta":
            Furgoneta furgoneta = new Furgoneta(tfMarca.getText(), tfModelo.getText(),Integer.parseInt(tfPma.getText()),Integer.parseInt(tfPlazasFurgoneta.getText()),tfMatricula.getText());
            // Configura las propiedades del objeto Furgoneta según la entrada del usuario
            // ...
            controller.insertar(furgoneta);
            break;
        case "Autobus":
            Autobus autobus = new Autobus(tfMarca.getText(), tfModelo.getText(),Integer.parseInt(tfPlazas.getText()),tfMatricula.getText());
            // Configura las propiedades del objeto Autobus según la entrada del usuario
            // ...
           	controller.insertar(autobus);
            break;
        default:
            // Maneja el caso en el que el valor de la choiceBoxTipoVehiculo no coincide con ninguna subclase conocida.
            System.out.println("Error: tipo de vehículo desconocido");
            break;
    }

//			try {
//				controller.insertar(v);
//			} catch (OperationNotSupportedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			//Cerramos el escenario actual
			Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	escenario.close();
        }
        catch (IllegalArgumentException | NullPointerException |OperationNotSupportedException e)
        {
            //VentanaAlerta.mostrar("ERROR", e.getMessage());
            Dialogos.mostrarDialogoError("ERROR AÑADIR VEHICULO", e.getMessage());
		}


    }

    @FXML
    void cerrar(ActionEvent event)
    {
    	Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	escenario.close();
    }
    private void initializeCilindradaBinding() {
        slideCilindrada.setMax(CILINDRADA_MAX_VALUE);
        StringProperty cilindradaString = tfCilindrada.textProperty();
        DoubleProperty  cilindradaDouble = slideCilindrada.valueProperty();


 
        Bindings.bindBidirectional(cilindradaString, cilindradaDouble, new StringConverter<Number>() {
            @Override
            public Number fromString(String s) {
                return Double.parseDouble(s);
            }
            @Override
            public String toString(Number number) {
                return String.valueOf(number);
            }
        });
    }

    public void ocultaBotones()
    {
    	btAnadir.setVisible(false);
    	btCancelar.setVisible(false);
    }
	public void setControlador(Controlador controller) {this.controller=controller;}

	public Vehiculo getRegistro() {
		return v;
	}

//	public void setListado(ObservableList<Vehiculo> obsVehiculos2) {
//		this.obsVehiculos=obsVehiculos2;
//	}

	public void setRegistro(Vehiculo registro) {
		v=registro;
	}
}
