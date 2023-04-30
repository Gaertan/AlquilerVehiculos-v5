package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	private final String RUTA_FICHERO ="datos/alquileres.xml";

	private final String RAIZ = "alquileres";
	private final String ALQUILER="alquiler";
					private final String DNI_CLIENTE ="dni";private final String MATRICULA_VEHICULO = "matricula";





		private final String FECHA_ALQUILER="fechaAlquiler";
		private final String FECHA_DEVOLUCION="fechaDevolucion";
						private final String FORMATO ="formato";	private final String FORMATO_FECHA = "dd/MM/yyyy";


	private final String TIPO_DATO="tipodato";

	private static Alquileres instance = new Alquileres();
	private List<Alquiler> coleccionAlquileres= new ArrayList<>();

	private Alquileres() {}

	@Override
	public List<Alquiler> get(){return coleccionAlquileres;}

	@Override
	public List<Alquiler> get(Cliente cliente){
		if(cliente==null) {throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");}
		List<Alquiler> coleccionCliente = new ArrayList<>() ;
		for (Alquiler alquiler : coleccionAlquileres) {
				if(alquiler.getCliente().equals(cliente)) {
					coleccionCliente.add(alquiler);}
			}
		return coleccionCliente;
		}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo){
		if(vehiculo==null) {throw new NullPointerException("ERROR: No se puede buscar un vehiculo nulo.");}
		List<Alquiler> coleccionVehiculo = new ArrayList<>() ;
		for (Alquiler alquiler : coleccionAlquileres) {
				if(alquiler.getVehiculo().equals(vehiculo)) {
					coleccionVehiculo.add(alquiler);}
			}
		return coleccionVehiculo;
		}

	@Override
	public int getCantidad() {
		try{int cantidad = coleccionAlquileres.size();return cantidad;}
		catch(Exception e) {int cantidad=0;return cantidad;}}

	private boolean comprobarAlquiler(Cliente cliente,Vehiculo vehiculo,LocalDate fechaAlquiler) throws OperationNotSupportedException {
		//como llevaba desde las 3 am haciendo cosas hice el codigo para insertarle alquiler como parametros,oops
		//el codigo es optimizable/algo redundante por ello pero bueno, pasas que cosan

		boolean status = true;

		for (Alquiler alquilerB : coleccionAlquileres) {

			if((cliente.equals(alquilerB.getCliente()))&&(alquilerB.getFechaDevolucion()==null)) {
					status = false;throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
			}

			if((vehiculo.equals(alquilerB.getVehiculo()))&&(alquilerB.getFechaDevolucion()==null)) {
				status = false;throw new OperationNotSupportedException("ERROR: El vehiculo está actualmente alquilado.");
			}



			if(cliente.equals(alquilerB.getCliente())&&(alquilerB.getFechaDevolucion().isAfter(fechaAlquiler))) {
					status = false;throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
			}

			if(vehiculo.equals(alquilerB.getVehiculo())&&alquilerB.getFechaDevolucion().isAfter(fechaAlquiler)) {
					status = false;throw new OperationNotSupportedException("ERROR: El vehiculo tiene un alquiler posterior.");
			}

		}


		return status;}

	@Override
	public void insertar(Alquiler alquiler)throws OperationNotSupportedException , NullPointerException {
		if(alquiler==null) {throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");}
		/*try {
			if(comprobarAlquiler(alquiler.getCliente(),alquiler.getVehiculo(),alquiler.getFechaAlquiler())) {coleccionAlquileres.add(alquiler);}
		} catch (OperationNotSupportedException e) {

			e.getMessage();
		}*/
		if(comprobarAlquiler(alquiler.getCliente(),alquiler.getVehiculo(),alquiler.getFechaAlquiler())) {


			coleccionAlquileres.add(alquiler);


		}
	}

	@Override
	public void devolver(Cliente cliente,LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
		if(cliente==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}
//		for (Alquiler alquilerB : coleccionAlquileres) {
//
//
//			if(alquilerB.getCliente().equals(cliente)&&alquilerB.getFechaDevolucion()==null) {
//				/*try {alquilerB.devolver(fechaDevolucion);}
//				catch (OperationNotSupportedException e) {e.getMessage();}}*/
//				alquilerB.devolver(fechaDevolucion);}
//			status = true;
//		}
		Alquiler alquiler = null;alquiler = getAlquilerAbierto(cliente);
		if (alquiler==null)	{throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");}
		alquiler.devolver(fechaDevolucion);
	}
	@Override
	public void devolver(Vehiculo vehiculo,LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
		if(vehiculo==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}
		Alquiler alquiler = null;alquiler = getAlquilerAbierto(vehiculo);
		if (alquiler==null)	{throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");}
		alquiler.devolver(fechaDevolucion);
	}

//	@Override
//	public void devolver(Alquiler alquiler,LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
//		if(alquiler==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}
//		boolean status = false;
//		for (Alquiler alquilerB : coleccionAlquileres) {
//
//
//			if(alquilerB.equals(alquiler)) {
//				/*try {alquilerB.devolver(fechaDevolucion);}
//				catch (OperationNotSupportedException e) {e.getMessage();}}*/
//				alquilerB.devolver(fechaDevolucion);}
//			status = true;
//		}
//		if (!status) {throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");}
//	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}

		if(coleccionAlquileres.contains(alquiler)) {
			Alquiler alquilerB = coleccionAlquileres.get(coleccionAlquileres.indexOf(alquiler));return alquilerB;}
		else {return null;}
		}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}

		if(coleccionAlquileres.contains(alquiler)) {coleccionAlquileres.remove(alquiler);}
		else throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}

	@Override
	public void devolver(Alquiler alquiler, LocalDate fechaDevolucion)
			throws NullPointerException, OperationNotSupportedException {
		// TODO Auto-generated method stub

	}

	public Alquiler getAlquilerAbierto(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler de cliente nulo.");
		}
		Alquiler alquiler = null;
		for(Alquiler alquilerBusca : coleccionAlquileres) {
			if(alquilerBusca.getFechaDevolucion()==null && alquilerBusca.getCliente().equals(cliente)) {alquiler = alquilerBusca;}


		}
		return alquiler;
	}

	public Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler de cliente nulo.");
		}
		Alquiler alquiler = null;
		for(Alquiler alquilerBusca : coleccionAlquileres) {
			if(alquilerBusca.getFechaDevolucion()==null && alquilerBusca.getVehiculo().equals(vehiculo)) {alquiler = alquilerBusca;}


		}
		return alquiler;
	}



	@Override
	public void comenzar() {
//		try {
			leerXml();
//		} catch (Exception e) {
//			System.out.println("Algo ha ocurrido leyendo el archivo,puede que no se encuentre o sea nulo");
//		}
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
		Element listaAlquileres = DOM.getDocumentElement();

		NodeList listaNodos=listaAlquileres.getChildNodes();
		// a partir de la lista de nodos, obtenemos los elementos y atributos y creamos un cliente nuevo
		for (int i=0; i<listaNodos.getLength();i++)
		{
			Node nodo=listaNodos.item(i);
		//tomando tan solo los nodos de tipo elemento(no comentario ni nada) creamos clientes y los insertamos en la arraylist
			if(nodo.getNodeType() == Node.ELEMENT_NODE)
			{
				Alquiler alquiler = elementToAlquiler((Element)nodo);
				try {insertar(alquiler);} catch (OperationNotSupportedException e) {
//					e.printStackTrace();
					}
			}
		}
	}


//	private void escribirXml() {
//
//		Document DOM = UtilidadesXml.crearDomVacio(RAIZ);
//		Element listaAlquileres = DOM.getDocumentElement();
//
//
//		for(Alquiler a:coleccionAlquileres) {
//
//		    	try {
//					Element alquilerDOM = alquilerToElement(DOM,a);
//					listaAlquileres.appendChild(alquilerDOM);
//				} catch (DOMException e) {// TODO Auto-generated catch blocke.printStackTrace();}
//			}
//
//		UtilidadesXml.domToXml(DOM, RUTA_FICHERO);
//		}
//	}

	private void escribirXml() {
	    Document DOM = UtilidadesXml.crearDomVacio(RAIZ);
	    Element listaAlquileres = DOM.getDocumentElement();

	    for (Alquiler a : coleccionAlquileres) {
	        try {
	            Element alquilerDOM = alquilerToElement(DOM, a);
	            listaAlquileres.appendChild(alquilerDOM);
	        } catch (DOMException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	    try {
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	        DOMSource source = new DOMSource(DOM);
	        StreamResult result = new StreamResult(new File(RUTA_FICHERO));

	        transformer.transform(source, result);
	    } catch (TransformerException e) {

	    }
	}


	private Alquiler elementToAlquiler(Element element) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
		//definimos los elementos a obtener del DOM y los objetos a crear para instanciar el alquiler

		Cliente cliente = null;
		Vehiculo vehiculo = null;


		Element alquilerDOM =  element;
		String matriculaAtr = alquilerDOM.getAttribute(MATRICULA_VEHICULO);
		String dniAtr = alquilerDOM.getAttribute(DNI_CLIENTE);

		Element fechaAlquiler = (Element) alquilerDOM.getElementsByTagName(FECHA_ALQUILER).item(0);
		Element fechaDevolucion = (Element) alquilerDOM.getElementsByTagName(FECHA_DEVOLUCION).item(0);


		//obtenemos el cliente y vehiculo asociados en el XML mediante sus ID buscandolos en sus correspondientes arraylist
		List<Cliente> listaClientes = Clientes.getInstancia().get();
		for(Cliente clienteS:listaClientes) {if(clienteS.getDni().equalsIgnoreCase(dniAtr)) {cliente=clienteS;}}
		List<Vehiculo> listaVehiculos = Vehiculos.getInstancia().get();
		for(Vehiculo vehiculoS:listaVehiculos) {if(vehiculoS.getMatricula().equalsIgnoreCase(matriculaAtr)) {vehiculo=vehiculoS;}}
		//creamos el alquiler con el cliente y vehiculos dados si se han encontrado
		Alquiler alquiler = new Alquiler(cliente, vehiculo, LocalDate.parse(fechaAlquiler.getTextContent(), formatter));

		//si el XML contiene una fecha de devolucion,devolvemos el alquiler para asignarsela antes de retornarlo
		if(fechaDevolucion.getTextContent()!=null&&fechaDevolucion.getTextContent()!="") {
			try {
				alquiler.devolver(LocalDate.parse(fechaDevolucion.getTextContent(), formatter));
			} catch (OperationNotSupportedException | DOMException e) {
				e.printStackTrace();
			}
			}



		return alquiler;
	}


	private Element alquilerToElement(Document DOM,Alquiler a) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
//		LocalDate fechaA = a.getFechaAlquiler();
//		fechaA.format(formatter);
////		fechaA.toString();
//		LocalDate fechaD = a.getFechaDevolucion();
//		if(fechaD!=null) {
//			fechaD.format(formatter);
////			fechaD.toString();
//			}
	    LocalDate fechaA = a.getFechaAlquiler();
		LocalDate fechaD = a.getFechaDevolucion();
		String fechaDString = null;
	    String fechaAString = fechaA.format(formatter);
		if(fechaD!=null) {
		     fechaDString = fechaD.format(formatter);

		}
		Element alquilerDOM = DOM.createElement(ALQUILER);
			alquilerDOM.setAttribute(DNI_CLIENTE, a.getCliente().getDni());
			alquilerDOM.setAttribute(MATRICULA_VEHICULO, a.getVehiculo().getMatricula());

		Element fechaAlquilerD = DOM.createElement(FECHA_ALQUILER);
		fechaAlquilerD.setAttribute(FORMATO, FORMATO_FECHA);
		fechaAlquilerD.setAttribute(TIPO_DATO, "LocalDate");
		fechaAlquilerD.setTextContent(fechaAString.toString());

		alquilerDOM.appendChild(fechaAlquilerD);

		Element fechaDevolucionD = DOM.createElement(FECHA_DEVOLUCION);
		if(a.getFechaDevolucion()!=null) {fechaDevolucionD.setTextContent(fechaDString.toString());}
		else{fechaDevolucionD.setTextContent("");}

		fechaDevolucionD.setAttribute(FORMATO, FORMATO_FECHA);
		fechaDevolucionD.setAttribute(TIPO_DATO, "LocalDate");
		alquilerDOM.appendChild(fechaDevolucionD);

		return alquilerDOM;

	}

	public static IAlquileres getInstancia() {

		return instance;
	}



















	}
