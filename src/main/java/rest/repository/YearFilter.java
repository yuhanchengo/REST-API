package rest.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import rest.domain.Vehicle;

@Component(value = "yf")
public class YearFilter implements Filter<Vehicle>{

	private Integer request;

	@Override
	public List<Vehicle> execute(List<Vehicle> filterList) {
		return filterList.stream().filter(e -> e.getYear() == request).collect(Collectors.toList());
	}
	
	public void setRequest(String request) {
		this.request = Integer.valueOf(request);
	}
}
