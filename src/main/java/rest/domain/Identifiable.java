package rest.domain;

// interface for domain object
public interface Identifiable extends org.springframework.hateoas.Identifiable<Integer>{
	public void setId(int id);

}
