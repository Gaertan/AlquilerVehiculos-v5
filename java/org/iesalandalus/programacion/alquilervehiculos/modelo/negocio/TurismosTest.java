package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria.Vehiculos;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TurismosTest {

	private static final String MENSAJE_ERROR_INSERTAR_CLIENTE_NULO = "ERROR: No se puede insertar un turismo nulo.";
	private static final String MENSAJE_ERROR_BORRAR_CLIENTE_NULO = "ERROR: No se puede borrar un turismo nulo.";
	private static final String MENSAJE_ERROR_BUSCAR_CLIENTE_NULO = "ERROR: No se puede buscar un turismo nulo.";
	private static final String MENSAJE_ERROR_CLIENTE_EXISTE = "ERROR: Ya existe un turismo con esa matrícula.";
	private static final String MENSAJE_ERROR_CLIENTE_BORRAR_NO_EXISTE = "ERROR: No existe ningún turismo con esa matrícula.";

	private static Turismo turismo1;
	private static Turismo turismo2;
	private IVehiculos vehiculos;

	@BeforeAll
	public static void setup() {
		turismo1 = mock();
		when(turismo1.getMatricula()).thenReturn("1234BCD");
		turismo2 = mock();
		when(turismo2.getMatricula()).thenReturn("1111BBB");
	}

	@BeforeEach
	void init() {
		vehiculos = new Vehiculos();
	}

	@Test
	void constructorCreaTurismosCorrectamente() {
		assertNotNull(vehiculos);
		assertEquals(0, vehiculos.getCantidad());
	}

	@Test
	void getDevuelveTurismosCorrectamente() {
		assertDoesNotThrow(() -> vehiculos.insertar(turismo1));
		assertDoesNotThrow(() -> vehiculos.insertar(turismo2));
		List<Turismo> copiaTurismos = vehiculos.get();
		assertEquals(2, copiaTurismos.size());
		assertEquals(turismo1, copiaTurismos.get(0));
		assertSame(turismo1, copiaTurismos.get(0));
		assertEquals(turismo2, copiaTurismos.get(1));
		assertSame(turismo2, copiaTurismos.get(1));
	}

	@Test
	void insertarTurismoValidoInsertaCorrectamente() {
		assertDoesNotThrow(() -> vehiculos.insertar(turismo1));
		assertEquals(1, vehiculos.getCantidad());
		assertEquals(turismo1, vehiculos.buscar(turismo1));
		assertSame(turismo1, vehiculos.buscar(turismo1));
	}

	@Test
	void insertarTurismoNuloLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> vehiculos.insertar(null));
		assertEquals(MENSAJE_ERROR_INSERTAR_CLIENTE_NULO, npe.getMessage());
	}

	@Test
	void insertarTurismoRepetidoLanzaExcepcion() {
		assertDoesNotThrow(() -> vehiculos.insertar(turismo1));
		OperationNotSupportedException onse = assertThrows(OperationNotSupportedException.class, () -> vehiculos.insertar(turismo1));
		assertEquals(MENSAJE_ERROR_CLIENTE_EXISTE, onse.getMessage());
	}

	@Test
	void borrarTurismoExistenteBorraTurismoCorrectamente() {
		assertDoesNotThrow(() -> vehiculos.insertar(turismo1));
		assertDoesNotThrow(() -> vehiculos.borrar(turismo1));
		assertEquals(0, vehiculos.getCantidad());
		assertNull(vehiculos.buscar(turismo1));
	}

	@Test
	void borrarTurismoNoExistenteLanzaExcepcion() {
		assertDoesNotThrow(() -> vehiculos.insertar(turismo1));
		OperationNotSupportedException onse = assertThrows(OperationNotSupportedException.class, () -> vehiculos.borrar(turismo2));
		assertEquals(MENSAJE_ERROR_CLIENTE_BORRAR_NO_EXISTE, onse.getMessage());
		assertEquals(1, vehiculos.getCantidad());
	}

	@Test
	void borrarTurismoNuloLanzaExcepcion() {
		assertDoesNotThrow(() -> vehiculos.insertar(turismo1));
		NullPointerException npe = assertThrows(NullPointerException.class, () -> vehiculos.borrar(null));
		assertEquals(MENSAJE_ERROR_BORRAR_CLIENTE_NULO, npe.getMessage());
		assertEquals(1, vehiculos.getCantidad());
	}

	@Test
	void busarTurismoExistenteDevuelveTurismoCorrectamente() {
		assertDoesNotThrow(() -> vehiculos.insertar(turismo1));
		assertEquals(turismo1, vehiculos.buscar(turismo1));
		assertSame(turismo1, vehiculos.buscar(turismo1));
	}

	@Test
	void busarTurismoeNoExistenteDevuelveTurismoNulo() {
		assertNull(vehiculos.buscar(turismo1));
	}

	@Test
	void buscarTurismoNuloLanzaExcepcion() {
		assertDoesNotThrow(() -> vehiculos.insertar(turismo1));
		NullPointerException npe = assertThrows(NullPointerException.class, () -> vehiculos.buscar(null));
		assertEquals(MENSAJE_ERROR_BUSCAR_CLIENTE_NULO, npe.getMessage());
	}

}