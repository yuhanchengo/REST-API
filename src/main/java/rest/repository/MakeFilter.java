package rest.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import rest.domain.Vehicle;

@Component(value = "maf")
public class MakeFilter implements Filter<Vehicle>{

	private String request;
	
	@Override
	public List<Vehicle> execute(List<Vehicle> filterList) {
		return filterList.stream().filter(e -> e.getMake().equals(request)).collect(Collectors.toList());
	}
	
	public void setRequest(String request) {
		this.request = request;
	}
	
}
