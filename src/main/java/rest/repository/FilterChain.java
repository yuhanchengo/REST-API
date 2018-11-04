package rest.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import rest.bean.Vehicle;

@Component
public class FilterChain {
	private List<Filter<Vehicle>> filters = new ArrayList<>();
	
	public void addFilter(Filter<Vehicle> f) {
		filters.add(f);
	}
	
	public List<Vehicle> execute(List<Vehicle> vc){
		List<Vehicle> res = new ArrayList<>(vc);
		for(Filter<Vehicle> f : filters) {
			res = f.execute(res);
		}
		filters.clear();
		return res;
	}
}
