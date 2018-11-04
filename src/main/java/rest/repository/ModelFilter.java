package rest.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import rest.bean.Vehicle;

@Component(value = "mdf")
public class ModelFilter implements Filter<Vehicle>{

	private String request;
	
	@Override
	public List<Vehicle> execute(List<Vehicle> filterList) {
		return filterList.stream().filter(e -> e.getModel().equals(request)).collect(Collectors.toList());
	}
	
	public void setRequest(String request) {
		this.request = request;
	}

}
