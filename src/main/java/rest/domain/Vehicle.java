package rest.domain;

public class Vehicle implements Identifiable{
	
	private int id;
	private int year;
	private String make;
	private String model;
	
	@Override
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "Vehicle{ id = " + this.getId() + " year = " + this.getYear() + " make: " + this.getMake()
		+ " model: " + this.getModel();
	}
	
	
}
