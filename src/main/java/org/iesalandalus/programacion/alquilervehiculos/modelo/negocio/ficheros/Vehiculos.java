

package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class Vehiculos implements IVehiculos {

	private final String RUTA_FICHERO ="datos/vehiculos.xml";
	private final String RAIZ = "vehiculos";
		private final String VEHICULO = "vehiculo";
				private final String MATRICULA = "matricula";	private final String TIPO="tipo";
			private final String MARCA ="marca";
			private final String MODELO = "modelo";

			private final String TURISMO="turismo";
			private final String AUTOBUS="autobus";
			private final String FURGONETA="furgoneta";
				private final String CILINDRADA ="cilindrada";
				private final String PLAZAS="plazas";
				private final String PMA="pma";

	private final String TIPO_DATO="tipodato";







	private static Vehiculos instance = new Vehiculos();
	private List<Vehiculo> coleccionVehiculos = new ArrayList<>();

	private Vehiculos() {}

	@Override
	public List<Vehiculo> get(){
		ArrayList<Vehiculo> vehiculosR = new ArrayList<>(coleccionVehiculos);
		return vehiculosR;
		}

	@Override
	public int getCantidad() {
		int cantidad = 0;
	try {cantidad = coleccionVehiculos.size();}catch(Exception e) {}
		return cantidad;
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehiculo nulo.");
		}
		if (coleccionVehiculos.indexOf(vehiculo) == -1) {
			coleccionVehiculos.add(vehiculo);
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehiculo con esa matrícula.");
		}


	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehiculo nulo.");
		}
		Vehiculo vehiculoB = null;
		if(coleccionVehiculos.contains(vehiculo)) {
			vehiculoB =(coleccionVehiculos.get(coleccionVehiculos.indexOf(vehiculo)));}
		return vehiculoB;}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehiculo nulo.");
		}

		if(coleccionVehiculos.contains(vehiculo)) {coleccionVehiculos.remove(vehiculo);}
		else throw new OperationNotSupportedException("ERROR: No existe ningún vehiculo con esa matrícula.");
	}

	protected static IVehiculos getInstancia() {
	return instance ;
	}




	@Override
	public void comenzar() {
		try {
			leerXml();
		} catch (Exception e) {
			System.out.println("Algo ha ocurrido leyendo el archivo,puede que no se encuentre o sea nulo");
		}
	}
	@Override
	public void terminar() {
		try {
			escribirXml();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void leerXml() {
		//creamos el documento y cargamos sus nodos

		Document DOM = UtilidadesXml.xmlToDom(RUTA_FICHERO);
		Element listaVehiculos = DOM.getDocumentElement();

		NodeList listaNodos=listaVehiculos.getChildNodes();
		// a partir de la lista de nodos, obtenemos los elementos y atributos y creamos un cliente nuevo
		for (int i=0; i<listaNodos.getLength();i++)
		{
			Node nodo=listaNodos.item(i);
		//tomando tan solo los nodos de tipo elemento(no comentario ni nada) creamos clientes y los insertamos en la arraylist
			if(nodo.getNodeType() == Node.ELEMENT_NODE)
			{
				Vehiculo vehiculo = elementToVehiculo((Element)nodo);
				try {insertar(vehiculo);} catch (OperationNotSupportedException e) {}
			}
		}
	}


	private void escribirXml() {

		Document DOM = UtilidadesXml.crearDomVacio(RAIZ);
		Element listaVehiculos = DOM.getDocumentElement();


		for(Vehiculo v:coleccionVehiculos) {

		    	try {
					Element vehiculoDOM = vehiculoToElement(DOM,v);
					listaVehiculos.appendChild(vehiculoDOM);
				} catch (DOMException e) {// TODO Auto-generated catch blocke.printStackTrace();}
			}

		UtilidadesXml.domToXml(DOM, RUTA_FICHERO);
		}
	}




	private Vehiculo elementToVehiculo(Element element) {
		Vehiculo vehiculo = null;
		Element vehiculoDOM =  element;
		String matriculaAtr = vehiculoDOM.getAttribute(MATRICULA);
		String tipoAtr = vehiculoDOM.getAttribute(TIPO);
		Element marca = (Element) vehiculoDOM.getElementsByTagName(MARCA).item(0);
		Element modelo = (Element) vehiculoDOM.getElementsByTagName(MODELO).item(0);
//		Vehiculo vehiculo = new Vehiculo.(nombre.getTextContent(), dniAtr,telefono.getTextContent());
		if(tipoAtr.equalsIgnoreCase(TURISMO)) {
			Element turismoDOM = (Element) vehiculoDOM.getElementsByTagName(TURISMO).item(0);
			Element cilindrada = (Element) turismoDOM.getElementsByTagName(CILINDRADA).item(0);
			vehiculo = new Turismo(marca.getTextContent(),modelo.getTextContent(),Integer.parseInt(cilindrada.getTextContent()),matriculaAtr);
		}
		if(tipoAtr.equalsIgnoreCase(FURGONETA)) {
			Element furgonetaDOM = (Element) vehiculoDOM.getElementsByTagName(FURGONETA).item(0);

			Element pma = (Element) furgonetaDOM.getElementsByTagName(PMA).item(0);
			Element plazas=(Element) furgonetaDOM.getElementsByTagName(PLAZAS).item(0);
			vehiculo = new Furgoneta(marca.getTextContent(), modelo.getTextContent(), Integer.parseInt(pma.getTextContent()),Integer.parseInt( plazas.getTextContent()), matriculaAtr);
		}
		if(tipoAtr.equalsIgnoreCase(AUTOBUS)) {
			Element autobusDOM = (Element) vehiculoDOM.getElementsByTagName(AUTOBUS).item(0);
			Element plazas=(Element) autobusDOM.getElementsByTagName(PLAZAS).item(0);
			vehiculo = new Autobus(marca.getTextContent(), modelo.getTextContent(),Integer.parseInt( plazas.getTextContent()), matriculaAtr);
		}



		return vehiculo;
	}


	private Element vehiculoToElement(Document DOM,Vehiculo v) {
		String tipoV = null;
		if(v instanceof Turismo) {tipoV=TURISMO;}
		if(v instanceof Autobus) {tipoV=AUTOBUS;}
		if(v instanceof Furgoneta) {tipoV=FURGONETA;}



		Element vehiculoDOM = DOM.createElement(VEHICULO);
		vehiculoDOM.setAttribute(MATRICULA, v.getMatricula());
		vehiculoDOM.setAttribute(TIPO, tipoV);



		Element marcaE = DOM.createElement(MARCA);
		marcaE.setTextContent(v.getMarca());
		marcaE.setAttribute(TIPO_DATO, "String");
		vehiculoDOM.appendChild(marcaE);

		Element modeloE = DOM.createElement(MODELO);
		modeloE.setTextContent(v.getModelo());
		modeloE.setAttribute(TIPO_DATO, "String");
		vehiculoDOM.appendChild(modeloE);

		if(v instanceof Turismo) {
			Element turismoE = DOM.createElement(TURISMO);
			vehiculoDOM.appendChild(turismoE);

			Element cilindradaE = DOM.createElement(CILINDRADA);
			cilindradaE.setTextContent( Integer. toString(((Turismo) v).getCilindrada()));
			cilindradaE.setAttribute(TIPO_DATO, "Integer");
			turismoE.appendChild(cilindradaE);


		}

		if(v instanceof Autobus) {
			Element autobusE = DOM.createElement(AUTOBUS);
			vehiculoDOM.appendChild(autobusE);

			Element plazasE = DOM.createElement(PLAZAS);
			plazasE.setTextContent( Integer. toString(((Autobus) v).getPlazas()));
			plazasE.setAttribute(TIPO_DATO, "Integer");
			autobusE.appendChild(plazasE);

		}

		if(v instanceof Furgoneta) {
			Element furgonetaE = DOM.createElement(FURGONETA);
			vehiculoDOM.appendChild(furgonetaE);

			Element plazasE = DOM.createElement(PLAZAS);
			plazasE.setTextContent( Integer. toString(((Furgoneta) v).getPlazas()));
			plazasE.setAttribute(TIPO_DATO, "Integer");
			furgonetaE.appendChild(plazasE);
			Element pmaE = DOM.createElement(PMA);
			pmaE.setTextContent( Integer. toString(((Furgoneta) v).getPma()));
			pmaE.setAttribute(TIPO_DATO, "Integer");
			furgonetaE.appendChild(pmaE);

		}



		return vehiculoDOM;

	}







}
