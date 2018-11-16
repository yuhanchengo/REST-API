package rest.repository;

import java.util.List;

import rest.domain.Identifiable;

public interface Filter<T extends Identifiable> {
	public List<T> execute(List<T> filterList);
	public void setRequest(String request);
}
