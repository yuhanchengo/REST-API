package rest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import rest.bean.Identifiable;

public abstract class InMemRepository<T extends Identifiable> {
	
	@Autowired
	private IdGenerator idGenerator;
	private List<T> elements = Collections.synchronizedList(new ArrayList<>());
	
	// call when POST vehicle is called
	public T create(T element) {
		element.setId(idGenerator.getNextId());
		elements.add(element);
		return element;
	}
	
	// call when GET vehicles is called
	public List<T> getAll(){
		return elements;
	}
	
	// call when GET vehicle/{id} is called
	public Optional<T> getById(int id) {
		return elements.stream().filter(e->e.getId().equals(id)).findFirst();
	}
	
	// call when PUT vehicle is called
	// tell if element is successfully updated
	public boolean update(int id, T element) {
		if(element == null) {
			return false;
		}else {
			Optional<T> eleToBeUpdate = getById(id);
			eleToBeUpdate.ifPresent(original -> updateIfExsits(original, element));
			return eleToBeUpdate.isPresent();
		}
	}
	
	// override by orderRepository
	protected abstract void updateIfExsits(T original, T update);
	
	// call when DELETE vehicle/{id} is called
	public boolean delete(int id) {
		// removeIf returns true if element is removed
		return elements.removeIf(e -> e.getId().equals(id));
	}
	
	// clear all in memeory elements
	public void initiate() {
		elements.clear();
	}
	
	public int getElementNums() {
		return elements.size();
	}
}
