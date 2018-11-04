package rest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rest.bean.Vehicle;

@Component
public class FilterManager {
	
	private FilterChain fc;

	@Autowired
	FilterManager(FilterChain fc){
		this.fc = fc;
	}
	
	public void setFilter(Filter<Vehicle> f) {
		fc.addFilter(f);
	}
	
	public List<Vehicle> execute(List<Vehicle> allVehicles){
		return fc.execute(allVehicles);
	}
	
}
