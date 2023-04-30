package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

 public class  UtilidadesXml {


	private UtilidadesXml() {}


	public static Document xmlToDom(String rutaXml) {

		//se carga en un objeto Document un archivo XML(de la ruta pasada) mediante documentBuilder.parse
	    Document document=null;
	    try {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document=documentBuilder.parse(rutaXml);

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }
    return document;}



	public static boolean domToXml(Document dom, String rutaXml) {
		 try {
	           //Creamos un objeto file, un transformer para adecuar el DOM al formato xml

	            File file=new File(rutaXml);

	            TransformerFactory transformerFactory =TransformerFactory.newInstance();

	            transformerFactory.setAttribute("indent-number", new Integer(4));
	            Transformer transformer = transformerFactory.newTransformer();
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            // abrimos un stream para escribir en el archivo y le añadimos el DOM de origen y la transformacion deseada
	            FileOutputStream fileOutputStream =new FileOutputStream(file);

	            StreamResult result = new StreamResult(new OutputStreamWriter(fileOutputStream,"UTF-8"));

	            DOMSource source = new DOMSource(dom);

	            transformer.transform(source, result);
	            return true;
	        } catch (TransformerException ex) {
	            System.out.println(ex.getMessage());
	        } catch (UnsupportedEncodingException uee){
	            System.out.println(uee.getMessage());
	        } catch (FileNotFoundException fnfe){
	            System.out.println(fnfe.getMessage());
	        }
	        return false;
	}

	public static Document crearDomVacio(String raiz) {
		//crea un documento DOM mediante DocumentBuilder y DocumentBuilderFactory cuya raiz es la String pasada
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document = null ;
        try {
        	documentBuilder = documentBuilderFactory.newDocumentBuilder() ;
            document = documentBuilder.newDocument() ;
            document.appendChild(document.createElement(raiz));
            return document;
        } catch (ParserConfigurationException ex) {

            System.out.println(ex.getMessage());
        }
        return document ;
    }











}
