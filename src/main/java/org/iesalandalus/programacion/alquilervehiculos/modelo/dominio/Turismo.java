package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;


public class Turismo extends Vehiculo {
	private final int FACTOR_CILINDRADA = 10;
	private int cilindrada;


	public Turismo(String marca, String modelo, int cilindrada, String matricula) {

		super( marca,  modelo,  matricula);

		setCilindrada(cilindrada);

	}

	public Turismo(Turismo vehiculo) {
		super();
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No es posible copiar un turismo nulo.");
		}
		setMarca(vehiculo.getMarca());
		setModelo(vehiculo.getModelo());
		setCilindrada(vehiculo.getCilindrada());
		setMatricula(vehiculo.getMatricula());

	}
	private void setCilindrada(int cilindrada) {

		if((cilindrada<=0)||(cilindrada>5000)) {throw new IllegalArgumentException("ERROR: La cilindrada no es correcta.");}
		this.cilindrada=cilindrada;
	}

	public int getCilindrada() {return this.cilindrada;}


	@Override
	public Vehiculo getVehiculoConMatricula(String matricula){
		if (matricula==null) if (matricula == null) {throw new NullPointerException("ERROR: La matrícula no puede ser nula.");}
		return new Turismo("Seat","Leon",90,matricula);
	}

	@Override
	public String toString() {

		/*PARA REALIZAR EL CAMBIO CORRECTO DE CV A CC NOS FALTAN DATOS;
		 * Casi todos los motores de los automóviles de carreras no tienen una relación de cc a hp
		 *  en una gama de 13 de 25:1 (es decir, algunos motores pueden producir 100 hp por cada 1300 cc
		 *  , pero otros requieren hasta 2500 cc para producir los mismos 100 hp).
		 *   El sitio web Simetric (ver Recursos) ofrece una carta con una extensa lista de relaciones de cc a hp de varios tipos de motores.

		 *ES DECIR SIN SABER LA RELACION DE CADA MOTOR NO PODEMOS HACER LA CONVERSION DE CV A CC
		 *por tanto se pide solo que se introduzca directamente en cc
		 *
		 */
		return (String.format("%s %s (%sCC) - %s -turismo", marca, modelo, cilindrada, matricula, "disponible"));
	}

	@Override
	protected int getFactorPrecio() {

		return cilindrada/FACTOR_CILINDRADA;
	}



}
