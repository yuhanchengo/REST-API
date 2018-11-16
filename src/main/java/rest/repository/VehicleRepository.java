package rest.repository;

import org.springframework.stereotype.Repository;

import rest.domain.Vehicle;

@Repository
public class VehicleRepository extends InMemRepository<Vehicle>{

	protected void updateIfExsits(Vehicle original, Vehicle update) {
		original.setYear(update.getYear());
		original.setMake(update.getMake());
		original.setModel(update.getModel());
	}

}
