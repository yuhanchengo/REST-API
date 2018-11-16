package rest.test.integration.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static rest.test.integration.controller.util.VehicleControllerTestUtil.vehicleAtIndexIsCorrect;
import static rest.test.integration.controller.util.VehicleControllerTestUtil.vehicleIsCorrect;
import static rest.test.integration.controller.util.VehicleControllerTestUtil.vehicleLinksAreCorrect;
import static rest.test.integration.controller.util.VehicleControllerTestUtil.vehicleLinksAtIndexAreCorrect;
import static rest.test.util.VehicleTestUtil.checkAllInfoMatched;
import static rest.test.util.VehicleTestUtil.generateTestVehicle;
import static rest.test.util.VehicleTestUtil.*;
import static rest.test.integration.controller.util.VehicleControllerTestUtil.*;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityLinks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import rest.domain.Vehicle;
import rest.repository.VehicleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleControllerTest extends ControllerIntegrationTest{
	
	private static final String EMPTY_TEST_VEHICLE = "";
	private static final String TEST_VEHICLE = "{\"model\": \"x5\", \"make\": \"bmw\", \"year\": 1990}";
	
	@Autowired
	private VehicleRepository vehicleRepo;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Before
	public void setUp() {
		vehicleRepo.initiate();
	}
	
//	================= test GET /vehicles when no vehicles ======================
//	========== return 200, [] ================
	@Test
	public void testGetAllVehiclesWhenNoVehicle() throws Exception{
		// no vehicles in repo now
		assertNoVehicles();
		getVehicles().andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));
	}
	
	private void assertNoVehicles() {
		assertVehicleCount(0);
	}
	
	private void assertVehicleCount(int count) {
		Assert.assertEquals(count, vehicleRepo.getElementNums());
	}
	
	private ResultActions getVehicles() throws Exception{
		return get("/vehicles");
	}
	
//	================ test GET /vehicles when one vehicle ===================
//	============= return 200, created vehicle ============
	@Test
	public void testGetAllVehiclesWhenOneVehicle() throws Exception{
		Vehicle createdVehicle = newVehicle();
		assertVehicleCount(1);
		getVehicles().andExpect(status().isOk()).andExpect(vehicleAtIndexIsCorrect(0, createdVehicle));
	}
	
	private Vehicle newVehicle() {
    	Vehicle vehicle = new Vehicle();
    	vehicle.setMake("Toyota");;
    	vehicle.setModel("Modle");
    	vehicle.setYear(1990);
    	return vehicleRepo.create(vehicle);
    }
	
	@Test
	public void testGetAllVehiclesWhenOneVehicleCorrectLink() throws Exception{
		Vehicle createdVehicle = newVehicle();
		assertVehicleCount(1);
		getVehicles().andExpect(status().isOk())
		.andExpect(vehicleLinksAtIndexAreCorrect(0, createdVehicle, entityLinks));
	}
	
//	================ test GET /vehicles/{id} when vehicle inexist =============
//	============= return 400 NOT FOUND ==============
	@Test
	public void testGetByIdWhenInexist() throws Exception{
		assertNoVehicles();
		// try to get id = 1 when no vehicles
		getVehicles(1).andExpect(status().isNotFound());
	}
	private ResultActions getVehicles(int id) throws Exception{
		return get("/vehicles/{id}", id);  
	}
	
//	================ test GET /vehicles/{id} when vehicle exist =============
//	============ return 200 and body =============
	@Test
    public void testGetExistingVehicleWhenExist() throws Exception {
    	Vehicle createdVehicle = newVehicle();
    	assertVehicleCount(1);
        getVehicles(createdVehicle.getId())
        	.andExpect(status().isOk())
        	.andExpect(vehicleIsCorrect(createdVehicle));
    }
    
    @Test
    public void testGetExistingVehiclesWithCorrectLinks() throws Exception {
    	Vehicle createdVehicle = newVehicle();
    	assertVehicleCount(1);
    	getVehicles(createdVehicle.getId())
        	.andExpect(status().isOk())
        	.andExpect(vehicleLinksAreCorrect(createdVehicle, entityLinks));
    }
    
//    ================= test CREATE /vehicles successfully created =================

    @Test
    public void testCreateNewVehicleAndCreated() throws Exception{
    	assertNoVehicles();
    	Vehicle createdVehicle = generateTestVehicle();
    	createVehicles(toJsonString(createdVehicle));
    	assertVehicleCount(1);
    	checkAllInfoMatched(createdVehicle, getCreatedVehicle());
    }
    
    private ResultActions createVehicles(String body) throws Exception{
    	return post("/vehicles", body);
    }
    
    private Vehicle getCreatedVehicle() {
    	List<Vehicle> vehicles = vehicleRepo.getAll();
    	return vehicles.get(vehicles.size() - 1);
    }
    
//    ======= test CREATE /vehicles correct response ===========
    
    @Test
    public void testCreateNewVehicleCorrectResponse() throws Exception {
    	assertNoVehicles();
    	createVehicles(TEST_VEHICLE)
    		.andExpect(status().isCreated())
    		.andExpect(vehicleIsCorrect(getCreatedVehicle()));
    }
    
    @Test
    public void testCreateNewVehicleHaveCorrectLinks() throws Exception {
    	assertNoVehicles();
    	createVehicles(TEST_VEHICLE)
    		.andExpect(status().isCreated())
    		.andExpect(vehicleLinksAreCorrect(getCreatedVehicle(), entityLinks));
    }
    
    @Test
    public void testCreateInvalidNewVehicleCorrectResponse() throws Exception {
    	assertNoVehicles();
    	createVehicles(EMPTY_TEST_VEHICLE)
    		.andExpect(status().isBadRequest());
    }
//    =========== test DELETE /vehicles/{id} ======================
    
    @Test
    public void testDeleteNonexistVehicleCorrectResponse() throws Exception{
    	assertNoVehicles();
    	deleteVehicles(1).andExpect(status().isNotFound());
    }
    
    private ResultActions deleteVehicles(int id) throws Exception{
    	return delete("/vehicles/{id}", id);
    }
    
    @Test
    public void testDeleteExsitingVehicleCorrectResponse() throws Exception{
    	Vehicle createdVehicle = newVehicle();
    	assertVehicleCount(1);
    	deleteVehicles(createdVehicle.getId()).andExpect(status().isNoContent());
    }
    
    @Test
    public void testDeleteExistingVehicleDeleted() throws Exception{
    	Vehicle createdVehicle = newVehicle();
    	assertVehicleCount(1);
    	deleteVehicles(createdVehicle.getId());
    	assertNoVehicles();
    }
    
//    ============ test UPDATE /vehicles/{id} ====================
    @Test
    public void testUpdateNonexistVehicleCorrectResponse() throws Exception{
    	assertNoVehicles();
    	updateVehicle(1, newVehicle()).andExpect(status().isNotFound());
    	
    }
    private ResultActions updateVehicle(int id, Vehicle updatedVehicle) throws Exception{
    	return put("/vehicles/{id}", updatedVehicle, id);
    }
    
    @Test
    public void testUpdateExistingVehicleCorrectResponse() throws Exception{
    	Vehicle originalVehicle = newVehicle();
    	assertVehicleCount(1);
    	Vehicle updatedVehicle = generateUpdatedTestVehicle(originalVehicle);
    	updateVehicle(originalVehicle.getId(), updatedVehicle).andExpect(status().isOk())
    	.andExpect(updatedVehicleIsCorrect(originalVehicle.getId(), updatedVehicle));
    }
    
    @Test
    public void testUpdateExistingVehicleUpdated() throws Exception{
    	Vehicle originalVehicle = newVehicle();
    	assertVehicleCount(1);
    	Vehicle updatedVehicle = generateUpdatedTestVehicle(originalVehicle);
    	checkAllInfoMatched(updatedVehicle, originalVehicle);
    }

    
    
}
