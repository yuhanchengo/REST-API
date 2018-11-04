package rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

import rest.bean.Vehicle;

// turn Vehicle to JSON object to user
public class VehicleResource extends ResourceSupport{
	
	private final int id;
	private final int year;
	private final String make;
	private final String model;
	
	public VehicleResource(Vehicle vehicle) {
		id = vehicle.getId();
		year = vehicle.getYear();
		make = vehicle.getMake();
		model = vehicle.getModel();
	}

	@JsonProperty("id")
	public Integer getResourceId() {
		return id;
	}
	public int getYear() {
		return year;
	}
	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}
	
	
	
}
