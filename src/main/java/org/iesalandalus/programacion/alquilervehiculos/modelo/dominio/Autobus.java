package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;


public class Autobus extends Vehiculo {
	private final int FACTOR_PLAZAS = 2;
	private int plazas;


	public Autobus(String marca, String modelo, int plazas, String matricula) {

		super( marca,  modelo,  matricula);

		setPlazas(plazas);

	}

	public Autobus(Autobus autobus) {
		super();
		if (autobus == null) {
			throw new NullPointerException("ERROR: No es posible copiar un turismo nulo.");
		}
		setMarca(autobus.getMarca());
		setModelo(autobus.getModelo());
		setPlazas(autobus.getPlazas());
		setMatricula(autobus.getMatricula());

	}
	private void setPlazas(int plazas) {

		if((plazas<=0)||(plazas>5000)) {throw new IllegalArgumentException("ERROR: La cilindrada no es correcta.");}
		this.plazas=plazas;
	}

	public int getPlazas() {return this.plazas;}

	public static Vehiculo getAutobusConMatricula(String matricula){
		if (matricula==null) if (matricula == null) {throw new NullPointerException("ERROR: La matrícula no puede ser nula.");}
		return new Autobus("Seat","Leon",5,matricula);
	}
	@Override
	public Vehiculo getVehiculoConMatricula(String matricula){
		if (matricula==null) if (matricula == null) {throw new NullPointerException("ERROR: La matrícula no puede ser nula.");}
		return new Autobus("Seat","Leon",6,matricula);
	}
	@Override
	public String toString() {
		return (String.format("%s %s (%s plazas) - %s -Autobus", marca, modelo, plazas, matricula, "disponible"));
	}

	@Override
	protected int getFactorPrecio() {
		// TODO Auto-generated method stub
		return plazas*FACTOR_PLAZAS;
	}



}
