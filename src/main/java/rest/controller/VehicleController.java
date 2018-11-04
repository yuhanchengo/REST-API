package rest.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.bean.Vehicle;
import rest.repository.Filter;
import rest.repository.FilterManager;
import rest.repository.VehicleRepository;
import rest.resource.VehicleResource;
import rest.resource.VehicleResourceAssembler;

@CrossOrigin(origins = "*")
@RestController
@ExposesResourceFor(Vehicle.class)
@RequestMapping(value = "/vehicles", produces = "application/json")
public class VehicleController {
	
	@Autowired
	private VehicleRepository vehicleRepo;
	
	@Autowired
	private VehicleResourceAssembler vra;
	
	@Autowired
	@Qualifier("maf") 
	private  Filter<Vehicle> makef;
	
	@Autowired
	@Qualifier("mdf")
	private Filter<Vehicle> modelf;
	
	@Autowired 
	@Qualifier("yf")
	private Filter<Vehicle> yearf;
	
	@Autowired
	private FilterManager fm;
	
	
	// POST vehicles
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<VehicleResource> createVehicle(@RequestBody Vehicle newVehicle){
		if(!checkValidity(newVehicle)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Vehicle createdVehicle = vehicleRepo.create(newVehicle);
		return new ResponseEntity<>(vra.toResource(createdVehicle), HttpStatus.CREATED);
	}
	
	
	// GET vehicles
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<VehicleResource>> getAllVehicles(@RequestParam(value="year", required = false) String year,
																	  @RequestParam(value="make", required = false) String make,
																	  @RequestParam(value="model", required = false) String model){
		List<Vehicle> allVehicles  = vehicleRepo.getAll();
		if(year != null || model != null || make != null) {
			List<Vehicle> filteredVehicles = applyFilter(year, make, model, allVehicles);
			if(filteredVehicles.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(vra.allToResource(filteredVehicles), HttpStatus.OK);
		}
		return new ResponseEntity<>(vra.allToResource(allVehicles), HttpStatus.OK);
	}
	
	// GET vehicles/id
	@RequestMapping( value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<VehicleResource> getVehicleById(@PathVariable int id){
		Optional<Vehicle> vehicle = vehicleRepo.getById(id);
		if(vehicle.isPresent()) {
			return new ResponseEntity<>(vra.toResource(vehicle.get()), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// PUT vehicles/id
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<VehicleResource> updateVehicle(@PathVariable int id, @RequestBody Vehicle updatedVehicle){
		if(!checkValidity(updatedVehicle))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		boolean updateSuc = vehicleRepo.update(id, updatedVehicle);
		if(updateSuc) {
			return getVehicleById(id);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// DELETE vehicles/id
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<VehicleResource> deleteVehicle(@PathVariable int id){
		if(vehicleRepo.delete(id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private boolean checkValidity(Vehicle vehicle) {
		return ((vehicle.getMake().isEmpty() || vehicle.getModel().isEmpty() || 
				   vehicle.getYear() < 1950 || vehicle.getYear() > 2050) ? false : true);
	}
	
	private List<Vehicle> applyFilter(String yearVal, String makeVal, String modelVal, List<Vehicle> allVehicles){
		if(yearVal != null) {
			yearf.setRequest(yearVal);
			fm.setFilter(yearf);
		}
		if(makeVal != null) {
			makef.setRequest(makeVal);
			fm.setFilter(makef);
		}
		if(modelVal != null) {
			modelf.setRequest(modelVal);
			fm.setFilter(modelf);
		}
		return fm.execute(allVehicles);
	}
	
	
}
