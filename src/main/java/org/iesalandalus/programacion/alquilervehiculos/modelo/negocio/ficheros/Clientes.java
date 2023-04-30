package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class Clientes implements IClientes {

	private final String RUTA_FICHERO ="datos/clientes.xml";
	private final String RAIZ = "clientes";
	private final String CLIENTE = "cliente";
	private final String NOMBRE ="nombre";
	private final String DNI = "dni";
	private final String TELEFONO = "telefono";
	private final String TIPO_DATO = "tipodato";









	private static Clientes instance = new Clientes();
	private List<Cliente>coleccionClientes = new ArrayList<>();
	private Clientes() {}

	@Override
	public List<Cliente>  get() {
		ArrayList<Cliente> clientesR = new ArrayList<>(coleccionClientes);
		return clientesR ;
	}

	@Override
	public int getCantidad() {

		try{return coleccionClientes.size();}
		catch(Exception e) {
			int cantidad = 0;
			return  cantidad;}
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
	//indexof devuelve -1 si no se encuentra, por tanto, si no devuelve -1 no inserta y lanza excepcion
		if (coleccionClientes.indexOf(cliente) == -1) {coleccionClientes.add(new Cliente(cliente));}
		else {throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");}
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		if(coleccionClientes.indexOf(cliente)!=-1) {
			return(coleccionClientes.get(coleccionClientes.indexOf(cliente)));
		}
		else return null;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}

		if(coleccionClientes.indexOf(cliente)!=-1) {
			coleccionClientes.remove(coleccionClientes.indexOf(cliente));
		}
		else throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
	}

	@Override
	public void modificar(Cliente cliente,String nombre,String telefono) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}


		if(coleccionClientes.contains(cliente)) {
			try {
				if(nombre==null||nombre.trim() ==null||nombre=="") {nombre = cliente.getNombre();}
				if(telefono==null||telefono.trim()==null||telefono=="") {telefono = cliente.getTelefono();}
		/*		Cliente clienteInsert = new Cliente(nombre,cliente.getDni(),telefono);
				coleccionClientes.set(coleccionClientes.indexOf(cliente), clienteInsert);*/
			coleccionClientes.get(coleccionClientes.indexOf(cliente)).setNombre(nombre);
			coleccionClientes.get(coleccionClientes.indexOf(cliente)).setTelefono(telefono);
			} catch (Exception e) {System.out.println("ERROR: No se pudo insertar el cliente");}

		}
		else throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
	}

	protected static IClientes getInstancia() {

		return instance;

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
		}
	}

	private void leerXml() {
		//creamos el documento y cargamos sus nodos

		Document DOM = UtilidadesXml.xmlToDom(RUTA_FICHERO);
		Element listaClientes = DOM.getDocumentElement();

		NodeList listaNodos=listaClientes.getChildNodes();
		// a partir de la lista de nodos, obtenemos los elementos y atributos y creamos un cliente nuevo
		for (int i=0; i<listaNodos.getLength();i++)
		{
			Node nodo=listaNodos.item(i);
		//tomando tan solo los nodos de tipo elemento(no comentario ni nada) creamos clientes y los insertamos en la arraylist
			if(nodo.getNodeType() == Node.ELEMENT_NODE)
			{
				Cliente cliente = elementToCliente((Element)nodo);
				try {insertar(cliente);} catch (OperationNotSupportedException e) {}
			}
		}
	}


	private void escribirXml() {

		Document DOM = UtilidadesXml.crearDomVacio(RAIZ);
		Element listaClientes = DOM.getDocumentElement();


		for(Cliente c:coleccionClientes) {

		    	try {
					Element clienteDOM = clienteToElement(DOM,c);
					listaClientes.appendChild(clienteDOM);
				} catch (DOMException e) {// TODO Auto-generated catch blocke.printStackTrace();}
			}

		UtilidadesXml.domToXml(DOM, RUTA_FICHERO);
		}
	}
	private void escribirXml(List<Cliente>clientes) {

		Document DOM = UtilidadesXml.crearDomVacio(RAIZ);
		Element listaClientes = DOM.getDocumentElement();


		for(Cliente c:clientes) {

		    	try {
					Element clienteDOM = clienteToElement(DOM,c);
					listaClientes.appendChild(clienteDOM);
				} catch (DOMException e) {// TODO Auto-generated catch blocke.printStackTrace();}
			}

		UtilidadesXml.domToXml(DOM, RUTA_FICHERO);
		}
	}
public void saveData(List<Cliente>clientes) {escribirXml(clientes);}



	private Cliente elementToCliente(Element element) {

		Element clienteDOM =  element;
		String dniAtr = clienteDOM.getAttribute("dni");
		Element nombre = (Element) clienteDOM.getElementsByTagName(NOMBRE).item(0);
		Element telefono = (Element) clienteDOM.getElementsByTagName(TELEFONO).item(0);
		Cliente cliente = new Cliente(nombre.getTextContent(), dniAtr,telefono.getTextContent());
		return cliente;
	}


	private Element clienteToElement(Document DOM,Cliente c) {

		Element clienteDOM = DOM.createElement(CLIENTE);
		clienteDOM.setAttribute(DNI, c.getDni());

		Element nombreE = DOM.createElement(NOMBRE);
		nombreE.setTextContent(c.getNombre());
		nombreE.setAttribute(TIPO_DATO, "String");
		clienteDOM.appendChild(nombreE);

		Element telefonoE = DOM.createElement(TELEFONO);
		telefonoE.setTextContent(c.getTelefono());
		telefonoE.setAttribute(TIPO_DATO, "String");
		clienteDOM.appendChild(telefonoE);

		return clienteDOM;

	}























}
