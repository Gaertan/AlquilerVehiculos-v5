package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Types;
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
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.database.utilidades.MySQL;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	Connection conexion = null;


	private static Alquileres instance = new Alquileres();


	private Alquileres() {}

	@Override
	public List<Alquiler> get(){
		List<Alquiler> coleccionAlquileres= new ArrayList<>();
		
		
		try {
			String sentenciaStr = "select matricula, dni, fechaAlquiler, fechaDevolucion from alquileres order by fechaAlquiler";
			Statement sentencia = conexion.createStatement();
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			while (filas.next()) {
				String matricula = filas.getString(1);
				String dni = filas.getString(2);
				Date fechaAlquilerDate = filas.getDate(3);LocalDate fechaAlquiler = fechaAlquilerDate.toLocalDate();
				Date fechaDevolucionDate = null;
				LocalDate fechaDevolucion = null;
				try {fechaDevolucionDate = filas.getDate(3); fechaDevolucion = fechaDevolucionDate.toLocalDate();} catch (Exception e) {/*e.printStackTrace();*/}
				Cliente clienteBuscar = new Cliente("Andres Gar",dni,"622099498");
				Cliente cliente = Clientes.getInstancia().buscar(clienteBuscar);
				Vehiculo vehiculoBuscar = new Turismo("Seat","Leon",100,matricula);
				Vehiculo vehiculo = Vehiculos.getInstancia().buscar(vehiculoBuscar);
				Alquiler alquiler = new Alquiler(cliente,vehiculo,fechaAlquiler);
				if (fechaDevolucion!=null) {alquiler.devolver(fechaDevolucion);}
						
				coleccionAlquileres.add(alquiler);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		return coleccionAlquileres;}

	@Override
	public List<Alquiler> get(Cliente clienteP){
		if(clienteP==null) {throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");}
		List<Alquiler> coleccionAlquileres= new ArrayList<>();
		
		
		try {
			String sentenciaStr = "select matricula, dni, fechaAlquiler, fechaDevolucion from alquileres  where dni=? order by fechaAlquiler";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, clienteP.getDni());
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			while (filas.next()) {
				String matricula = filas.getString(1);
				String dni = filas.getString(2);
				Date fechaAlquilerDate = filas.getDate(3);LocalDate fechaAlquiler = fechaAlquilerDate.toLocalDate();
				Date fechaDevolucionDate = null;
				LocalDate fechaDevolucion = null;
				try {fechaDevolucionDate = filas.getDate(3); fechaDevolucion = fechaDevolucionDate.toLocalDate();} catch (Exception e) {/*e.printStackTrace();*/}
				Cliente clienteBuscar = new Cliente("Andres Gar",dni,"622099498");
				Cliente cliente = Clientes.getInstancia().buscar(clienteBuscar);
				Vehiculo vehiculoBuscar = new Turismo("Seat","Leon",100,matricula);
				Vehiculo vehiculo = Vehiculos.getInstancia().buscar(vehiculoBuscar);
				Alquiler alquiler = new Alquiler(cliente,vehiculo,fechaAlquiler);
				if (fechaDevolucion!=null) {alquiler.devolver(fechaDevolucion);}
						
				coleccionAlquileres.add(alquiler);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		return coleccionAlquileres;}

	@Override
	public List<Alquiler> get(Vehiculo vehiculoP){
		if(vehiculoP==null) {throw new NullPointerException("ERROR: No se puede buscar un vehiculo nulo.");}
		List<Alquiler> coleccionAlquileres= new ArrayList<>();
		
		
		try {
			String sentenciaStr = "select matricula, dni, fechaAlquiler, fechaDevolucion from alquileres where matricula like ?";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
			sentencia.setString(1, vehiculoP.getMatricula());
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			while (filas.next()) {
				String matricula = filas.getString(1);
				String dni = filas.getString(2);
				Date fechaAlquilerDate = filas.getDate(3);LocalDate fechaAlquiler = fechaAlquilerDate.toLocalDate();
				Date fechaDevolucionDate = null;
				LocalDate fechaDevolucion = null;
				try {fechaDevolucionDate = filas.getDate(3); fechaDevolucion = fechaDevolucionDate.toLocalDate();} catch (Exception e) {/*e.printStackTrace();*/}
				Cliente clienteBuscar = new Cliente("Andres Gar",dni,"622099498");
				Cliente cliente = Clientes.getInstancia().buscar(clienteBuscar);
				Vehiculo vehiculoBuscar = new Turismo("Seat","Leon",100,matricula);
				Vehiculo vehiculo = Vehiculos.getInstancia().buscar(vehiculoBuscar);
				Alquiler alquiler = new Alquiler(cliente,vehiculo,fechaAlquiler);
				if (fechaDevolucion!=null) {alquiler.devolver(fechaDevolucion);}
						
				coleccionAlquileres.add(alquiler);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		return coleccionAlquileres;}
	@Override
	public int getCantidad() {
		int tamano = 0;
		try {
			String sentenciaStr = "select count(*) from alquileres";
			Statement sentencia = conexion.createStatement();
			ResultSet filas = sentencia.executeQuery(sentenciaStr);
			if (filas.next()) {
				tamano = filas.getInt(1);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return tamano;}

	private boolean comprobarAlquiler(Cliente cliente,Vehiculo vehiculo,LocalDate fechaAlquiler) throws OperationNotSupportedException {
		//como llevaba desde las 3 am haciendo cosas hice el codigo para insertarle alquiler como parametros,oops
		//el codigo es optimizable/algo redundante por ello pero bueno, pasas que cosan

		try {

	        // Comprobar si el cliente tiene otro alquiler sin devolver
	        String consulta = "SELECT * FROM alquileres WHERE dni = ? AND fechaDevolucion IS NULL";
	        PreparedStatement ps = conexion.prepareStatement(consulta);
	        ps.setString(1, cliente.getDni());
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
	        }

	        // Comprobar si el vehículo está actualmente alquilado
	        consulta = "SELECT * FROM alquileres WHERE matricula = ? AND fechaDevolucion IS NULL";
	        ps = conexion.prepareStatement(consulta);
	        ps.setString(1, vehiculo.getMatricula());
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
	        }

	        // Comprobar si el cliente tiene un alquiler posterior
	        consulta = "SELECT * FROM alquileres WHERE dni = ? AND fechaAlquiler > ?";
	        ps = conexion.prepareStatement(consulta);
	        ps.setString(1, cliente.getDni());
	        ps.setDate(2, Date.valueOf(fechaAlquiler));
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
	        }

	        // Comprobar si el vehículo tiene un alquiler posterior
	        consulta = "SELECT * FROM alquileres WHERE matricula = ? AND fechaAlquiler > ?";
	        ps = conexion.prepareStatement(consulta);
	        ps.setString(1, vehiculo.getMatricula());
	        ps.setDate(2, Date.valueOf(fechaAlquiler));
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
	        }

	        // Si no se ha lanzado ninguna excepción, el alquiler se puede realizar
	        return true;

	    } catch (SQLException e) {
	        throw new OperationNotSupportedException("ERROR: No se ha podido realizar la comprobación de alquileres.");
	    }

	}
	

	@Override
	public void insertar(Alquiler alquiler)throws OperationNotSupportedException , NullPointerException {
		if(alquiler==null) {throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");}

		if(comprobarAlquiler(alquiler.getCliente(),alquiler.getVehiculo(),alquiler.getFechaAlquiler())) {

			try {
				String sentenciaStr = "insert into alquileres values (?, ?, ?, null)";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
				sentencia.setString(1, alquiler.getVehiculo().getMatricula());
				sentencia.setString(2, alquiler.getCliente().getDni());
				  Date sqlDateAlquiler = Date.valueOf(alquiler.getFechaAlquiler());
				  sentencia.setDate(3,sqlDateAlquiler);
//				  Date sqlDateDevolucion;
//				 if(alquiler.getFechaDevolucion()!=null) {sqlDateDevolucion = Date.valueOf(alquiler.getFechaDevolucion());sentencia.setDate(4,sqlDateDevolucion);}
//				 else {sentencia.setNull(4, Types.DATE);}


				sentencia.executeUpdate();
			} catch (SQLIntegrityConstraintViolationException e) {
				throw new OperationNotSupportedException("ERROR: Ya existe un Alquiler igual.");
			} catch (SQLException e) {
				throw new OperationNotSupportedException( e.getMessage());
			}
			


		}
	}

	@Override
	public void devolver(Cliente cliente,LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
		if(cliente==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}
       String sentenciaStr = "update alquileres set fechaDevolucion = ? where dni = ? and fechaDevolucion is null";
       PreparedStatement sentencia;
	try {
		sentencia = conexion.prepareStatement(sentenciaStr);
		sentencia.setDate(1, Date.valueOf(fechaDevolucion));
        sentencia.setString(2, cliente.getDni());
        int filasModificadas = sentencia.executeUpdate();
        if (filasModificadas == 0) {throw new OperationNotSupportedException("ERROR: No se ha encontrado ningún alquiler activo para el cliente especificado.");}
	} catch (SQLException e) {
		e.printStackTrace();
	}


        // Comprobamos que se haya modificado algún registro
       
	}
	@Override
	public void devolver(Vehiculo vehiculo,LocalDate fechaDevolucion) throws NullPointerException, OperationNotSupportedException{
		if(vehiculo==null||fechaDevolucion==null) {throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");}
	       String sentenciaStr = "update alquileres set fechaDevolucion = ? where matricula = ? and fechaDevolucion is null";
	       PreparedStatement sentencia;
		try {
			sentencia = conexion.prepareStatement(sentenciaStr);
			sentencia.setDate(1, Date.valueOf(fechaDevolucion));
	        sentencia.setString(2, vehiculo.getMatricula());
	        int filasModificadas = sentencia.executeUpdate();
	        if (filasModificadas == 0) {throw new OperationNotSupportedException("ERROR: No se ha encontrado ningún alquiler activo para el vehiculo especificado.");}
		} catch (SQLException e) {
			e.printStackTrace();}
	}


	@Override
	public Alquiler buscar(Alquiler alquiler) {
		String matricula = alquiler.getVehiculo().getMatricula();
		String dni = alquiler.getCliente().getDni();
		LocalDate fechaAlquiler = alquiler.getFechaAlquiler();
		Date sqlDateAlquiler = Date.valueOf(fechaAlquiler);
		LocalDate fechaDevolucion = alquiler.getFechaDevolucion();
		Alquiler alquilerReturn = null;
		
		try {
			
				if(fechaDevolucion!=null) {String sentenciaStr = "select matricula, dni, fechaAlquiler, fechaDevolucion from alquileres where matricula = ? and dni = ? and fechaAlquiler = ? and fechaDevolucion = ? ";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
				sentencia.setString(1, matricula);
				sentencia.setString(2, dni);
				sentencia.setDate(3, sqlDateAlquiler);
				Date sqlDateDevolucion = Date.valueOf(fechaDevolucion);
				sentencia.setDate(4, sqlDateDevolucion);
				ResultSet filas = sentencia.executeQuery();
				while (filas.next()) {
					Date fechaDevolucionDate = null;
					try {fechaDevolucionDate = filas.getDate(3); fechaDevolucion = fechaDevolucionDate.toLocalDate();} catch (Exception e) {/*e.printStackTrace();*/}
					Cliente clienteBuscar = new Cliente("Andres Gar",dni,"622099498");
					Cliente cliente = Clientes.getInstancia().buscar(clienteBuscar);
					Vehiculo vehiculoBuscar = new Turismo("Seat","Leon",100,matricula);
					Vehiculo vehiculo = Vehiculos.getInstancia().buscar(vehiculoBuscar);
					 alquilerReturn = new Alquiler(cliente,vehiculo,fechaAlquiler);
					if (fechaDevolucion!=null) {alquilerReturn.devolver(fechaDevolucion);}			
				}
				}
				
				if(fechaDevolucion==null) {String sentenciaStr = "select matricula, dni, fechaAlquiler, fechaDevolucion from alquileres where matricula = ? and dni = ? and fechaAlquiler = ? and fechaDevolucion IS NULL ";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaStr);
				sentencia.setString(1, matricula);
				sentencia.setString(2, dni);
				sentencia.setDate(3, sqlDateAlquiler);
				ResultSet filas = sentencia.executeQuery();
				while (filas.next()) {
					String matricula2 = filas.getString(1);
					String dni21 = filas.getString(2);
					Date fechaAlquilerDate = filas.getDate(3);LocalDate fechaAlquiler2 = fechaAlquilerDate.toLocalDate();
					Date fechaDevolucionDate = null;
					LocalDate fechaDevolucion2 = null;
					try {fechaDevolucionDate = filas.getDate(3); fechaDevolucion = fechaDevolucionDate.toLocalDate();} catch (Exception e) {/*e.printStackTrace();*/}
					Cliente clienteBuscar = new Cliente("Andres Gar",dni,"622099498");
					Cliente cliente = Clientes.getInstancia().buscar(clienteBuscar);
					Vehiculo vehiculoBuscar = new Turismo("Seat","Leon",100,matricula);
					Vehiculo vehiculo = Vehiculos.getInstancia().buscar(vehiculoBuscar);
					 alquilerReturn = new Alquiler(cliente,vehiculo,fechaAlquiler);
					if (fechaDevolucion!=null) {alquilerReturn.devolver(fechaDevolucion);}		
				}
				}		
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		return alquilerReturn;
		}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
	    if (alquiler == null) {
	        throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
	    }

	    try 
	    { 
//	    	PreparedStatement pst = conexion.prepareStatement("DELETE FROM alquileres WHERE matricula = ? AND dni = ? AND fechaAlquiler = ? AND fechaDevolucion = ?");
	    	PreparedStatement pst = conexion.prepareStatement("DELETE FROM alquileres WHERE matricula = ? AND dni = ? AND fechaAlquiler = ?");


	        pst.setString(1, alquiler.getVehiculo().getMatricula());
	        pst.setString(2, alquiler.getCliente().getDni());
	        pst.setDate(3, Date.valueOf(alquiler.getFechaAlquiler()));
//	        if (alquiler.getFechaDevolucion() == null) {
//	            pst.setNull(4, Types.DATE);
//	        } else {
//	            pst.setDate(4, Date.valueOf(alquiler.getFechaDevolucion()));
//	        }

	        int filasAfectadas = pst.executeUpdate();

	        if (filasAfectadas == 0) {
	            throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
	        }

	    } 
	    catch (SQLException e) {
	        throw new OperationNotSupportedException("ERROR: Error al borrar el alquiler.");
	    }
	}


	@Override
	public void devolver(Alquiler alquiler, LocalDate fechaDevolucion)
			throws NullPointerException, OperationNotSupportedException {
		  if (alquiler == null) {
		        throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		    }
		    try (
		         PreparedStatement pst = conexion.prepareStatement("UPDATE alquileres SET fechaDevolucion = ? WHERE dni = ? AND matricula = ? AND fechaAlquiler = ? AND fechaDevolucion IS NULL")) {
		        pst.setDate(1, Date.valueOf(fechaDevolucion));
		        pst.setString(2, alquiler.getCliente().getDni());
		        pst.setString(3, alquiler.getVehiculo().getMatricula());
		        pst.setDate(4, Date.valueOf(alquiler.getFechaAlquiler()));
		        int filasAfectadas = pst.executeUpdate();
		        if (filasAfectadas == 0) {
		            throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo y cliente.");
		        }
		    } catch (SQLException e) {
		        throw new RuntimeException("ERROR: Error en la operación de devolución de alquileres en la base de datos.", e);
		    }
	}

	public Alquiler getAlquilerAbierto(Cliente cliente) {
	    try (
	            PreparedStatement pst = conexion.prepareStatement("SELECT matricula, fechaAlquiler FROM alquileres WHERE dni = ? AND fechaDevolucion IS NULL")) {
	           pst.setString(1, cliente.getDni());
	           try (ResultSet rs = pst.executeQuery()) {
	               if (rs.next()) {
	            	   String matricula = rs.getString(1);
	      
	            	   Vehiculo vehiculoBuscar = new Turismo("Seat","Leon",100,matricula);
	                   Vehiculo vehiculo = Vehiculos.getInstancia().buscar(vehiculoBuscar);
	                   LocalDate fechaAlquiler = rs.getDate("fechaAlquiler").toLocalDate();
	                   return new Alquiler(cliente, vehiculo, fechaAlquiler);
	               } else {
	                   return null;
	               }
	           }
	       } catch (SQLException e) {
	           throw new RuntimeException("ERROR: Error en la operación de búsqueda de alquiler abierto por cliente en la base de datos.", e);
	       }
	}

	public Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
	    try (
	            PreparedStatement pst = conexion.prepareStatement("SELECT dni, fechaAlquiler FROM alquileres WHERE matricula = ? AND fechaDevolucion IS NULL")) {
	           pst.setString(1, vehiculo.getMatricula());
	           try (ResultSet rs = pst.executeQuery()) {
	               if (rs.next()) {
	            	   String dni = rs.getString(2);
	            		Cliente clienteBuscar = new Cliente("Andres Gar",dni,"622099498");
						Cliente cliente = Clientes.getInstancia().buscar(clienteBuscar);
	                   LocalDate fechaAlquiler = rs.getDate("fechaAlquiler").toLocalDate();
	                   return new Alquiler(cliente, vehiculo, fechaAlquiler);
	               } else {
	                   return null;
	               }
	           }
	       } catch (SQLException e) {
	           throw new RuntimeException("ERROR: Error en la operación de búsqueda de alquiler abierto por vehículo en la base de datos.", e);
	       }
	}



	@Override
	public void comenzar() {
		conexion = MySQL.establecerConexion();
	}
	@Override
	public void terminar() {
		MySQL.cerrarConexion();
	}
	
	public static IAlquileres getInstancia() {

		return instance;
	}



















	}
