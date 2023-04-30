package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;


public class Furgoneta extends Vehiculo {
	private final int FACTOR_PLAZAS = 1;
	private final int FACTOR_PMA = 100;
	private int plazas = 5;
	private int pma = 3500;


	public int getPma() {
		return pma;
	}

	private void setPma(int pma) {
		this.pma = pma;
	}

	public Furgoneta(String marca, String modelo,int pma, int plazas, String matricula) {

		super( marca,  modelo,  matricula);

		setPlazas(plazas);
		setPma(pma);

	}

	public Furgoneta(Furgoneta furgoneta) {
		super();
		if (furgoneta == null) {
			throw new NullPointerException("ERROR: No es posible copiar un turismo nulo.");
		}
		setMarca(furgoneta.getMarca());
		setModelo(furgoneta.getModelo());
		setPlazas(furgoneta.getPlazas());
		setMatricula(furgoneta.getMatricula());

	}
	private void setPlazas(int plazas) {

		if((plazas<=0)||(plazas>5000)) {throw new IllegalArgumentException("ERROR: La cilindrada no es correcta.");}
		this.plazas=plazas;
	}

	public int getPlazas() {return this.plazas;}

	public static Vehiculo getAutobusConMatricula(String matricula){
		if (matricula==null) if (matricula == null) {throw new NullPointerException("ERROR: La matrícula no puede ser nula.");}
		return new Furgoneta("Seat","Leon",5,2900, matricula);
	}

	@Override
	public String toString() {
		return (String.format("%s %s (%s plazas) %s pma - %s -Furgoneta", marca, modelo, plazas,pma, matricula, "disponible"));
	}

	@Override
	protected int getFactorPrecio() {
		// TODO Auto-generated method stub
		return ((pma / FACTOR_PMA) + (plazas * FACTOR_PLAZAS));
	}
	@Override
	public Vehiculo getVehiculoConMatricula(String matricula){
		if (matricula==null) if (matricula == null) {throw new NullPointerException("ERROR: La matrícula no puede ser nula.");}
		return new Furgoneta("Seat","Leon",2900,6,matricula);
		}



}
