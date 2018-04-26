package model;

public class Person {
	private String call_number;
	private String surname;
	Functions function;
	Unit_types unit_type;
	boolean aGT;
	boolean kfCe;
	boolean kfBRmG;
	boolean bedMotSag;
	public Person(String call_number, String surname, Functions function,  Unit_types unit_type) {
		this.setCall_number(call_number);
		this.setSurname(surname);
		this.setfunction(function);
		this.setunit_type(unit_type);
	}

	public boolean isaGT() {
		return aGT;
	}

	public void setaGT(boolean aGT) {
		this.aGT = aGT;
	}

	public boolean isKfCe() {
		return kfCe;
	}

	public void setKfCe(boolean kfCe) {
		this.kfCe = kfCe;
	}

	public boolean isKfBRmG() {
		return kfBRmG;
	}

	public void setKfBRmG(boolean kfBRmG) {
		this.kfBRmG = kfBRmG;
	}

	public boolean isBedMotSag() {
		return bedMotSag;
	}

	public void setBedMotSag(boolean bedMotSag) {
		this.bedMotSag = bedMotSag;
	}

	public String getCall_number() {
		return call_number;
	}

	public void setCall_number(String call_number) {
		this.call_number = call_number;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	

	public Functions getfunction() {
		return this.function;
	}

	public void setfunction(Functions function) {
		this.function = function;
	}
	
	public Unit_types getunit_type() {
		return this.unit_type;
	}

	public void setunit_type(Unit_types unit_type) {
		this.unit_type = unit_type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if((obj != null) && (obj instanceof Person)) 
		{
			Person otherPerson = (Person) obj;
			return call_number.equals(otherPerson.call_number);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return call_number.hashCode();
	}
	
	@Override
	public String toString() {
		return call_number + " " + surname;
	}
}