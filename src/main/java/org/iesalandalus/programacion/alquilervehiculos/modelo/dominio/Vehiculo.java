package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;

import javax.naming.OperationNotSupportedException;

public abstract class Vehiculo implements Comparable<Vehiculo>{

	private static final String ER_MARCA = "[A-Z][a-z]*(-|\\s|)?[a-zA-ZáéíóúüñÁÉÍÓÚÑ]+";
	private static final String ER_MATRICULA = "\\d{4}\s{0,1}([B-D]|[F-H]|[J-N]|[P-T]|[V-Z]){3}";
	protected String marca;
	protected String modelo;
	protected String matricula;

	protected Vehiculo() {

	}

	protected Vehiculo(String marca, String modelo, String matricula) {
		setMarca(marca);
		setModelo(modelo);
		setMatricula(matricula);
	}

	protected Vehiculo(Vehiculo vehiculo) {
		setMarca(vehiculo.getMarca());
		setModelo(vehiculo.getModelo());
		setMatricula(vehiculo.getMatricula());

	}
	public Vehiculo copiar(Vehiculo vehiculo) throws OperationNotSupportedException {

		if(vehiculo instanceof Turismo) {return new Turismo ((Turismo)vehiculo);}
		else if(vehiculo instanceof Autobus) {return new Autobus ((Autobus)vehiculo);}
		else if(vehiculo instanceof Furgoneta) {return new Furgoneta ((Furgoneta)vehiculo);}
		else throw new OperationNotSupportedException("ERROR; El tipo de vehículo no se encuentra en nuestra lista");
	}

	public abstract Vehiculo getVehiculoConMatricula(String matricula);

	protected abstract int getFactorPrecio();

	protected void setMarca(String marca) {
	if (marca == null) {throw new NullPointerException("ERROR: La marca no puede ser nula.");}
	marca.trim();
	if(marca==""||marca==null||marca==" ") {throw new IllegalArgumentException("ERROR: La marca no tiene un formato válido.");}
	if(!marca.matches(ER_MARCA)) {throw new IllegalArgumentException("ERROR: La marca no tiene un formato válido.");}
	else{this.marca=marca;}}

	protected void setModelo(String modelo) {
	if (modelo == null) {throw new NullPointerException("ERROR: El modelo no puede ser nulo.");}
	modelo.trim();
	if(modelo==""||modelo==null||modelo==" "||modelo=="[ \\t]+"||modelo== "	") {throw new IllegalArgumentException("ERROR: El modelo no puede estar en blanco.");}
	else{this.modelo=modelo;}}

	protected void setMatricula(String matricula) {
		if (matricula == null) {throw new NullPointerException("ERROR: La matrícula no puede ser nula.");}
		matricula.replaceAll(" ", "");
		matricula.toUpperCase();
		if(matricula.isEmpty() || !matricula.matches(ER_MATRICULA)) {throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido.");}
		this.matricula=matricula;
	}

	public String getMarca() {return this.marca;}

	public String getModelo() {return this.modelo;}

	public String getMatricula() {return this.matricula;}

	@Override
	public int hashCode() {
		return Objects.hash(matricula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null))
			return false;
		Vehiculo other = (Vehiculo) obj;
		return Objects.equals(matricula, other.matricula);
	}

    @Override
    public int compareTo(Vehiculo vehiculo) {
       String a=new String( this.getMarca()+this.getModelo()+this.getMatricula());
       String b=new String(vehiculo.getMarca()+vehiculo.getModelo()+vehiculo.getMatricula());
       int compara = a.compareTo(b);

       return compara;


   }

}