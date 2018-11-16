package rest.test.unit.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rest.domain.Vehicle;
import rest.repository.VehicleRepository;
import static rest.test.util.VehicleTestUtil.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleRepositoryTest {
	private static int nonExistId = -1;
	
	@Autowired
	private VehicleRepository vehicleRepo;
	
	@Before
	public void setup() {
		vehicleRepo.initiate();
	}
	
//	================= test get one with inexist ID =================
	
	@Test
	public void testFindNonExistId() throws Exception{
		// since vehicle shouldn't exist, assertFalse on isPresent
		assertNoExistingVehicle();
		Optional<Vehicle> vehicle = vehicleRepo.getById(nonExistId);
		Assert.assertFalse(vehicle.isPresent());
	}
	private void assertNoExistingVehicle() {
		assertExistingVehicleCount(0);
	}
	
	private void assertExistingVehicleCount(int count) {
		Assert.assertEquals(count, vehicleRepo.getElementNums());
	}
	
//	=================== test get one with ID ========================
	
	@Test
	public void testFindExistId() throws Exception{
		Vehicle newVehicle = newVehicle();
		Optional<Vehicle> foundVehicle = vehicleRepo.getById(newVehicle.getId());
		Assert.assertTrue(foundVehicle.isPresent());
	}
	private Vehicle newVehicle() {
		Vehicle vehicle = vehicleRepo.create(generateTestVehicle());
		return vehicle;
	}
	
//	=================== test get correct num of vehicles found=======================
	
	@Test
	public void testFindAllWithNoExsitingAndNoOrdersFound() throws Exception{
		assertFindAllIsCorrectWithVehicleCount(0);
	}
	
	private void assertFindAllIsCorrectWithVehicleCount(int count) {
		injectGivenNumberOfVehicles(count); // new num of count vehicles
		assertExistingVehicleCount(count); // check repo contains num of count vehicles
		List<Vehicle> vehiclesFound = vehicleRepo.getAll(); // get all vehicles from repo
		Assert.assertEquals(count, vehiclesFound.size()); // check retrieved correct num of vehicles
	}
	
	private List<Vehicle> injectGivenNumberOfVehicles(int count) {
		List<Vehicle> injectedVehicles = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			injectedVehicles.add(newVehicle());
		}
		return injectedVehicles;
	}
	
	@Test
	public void testFindAllWithOneExistingOrdersEnsureOneVehiclesFound() throws Exception {
		assertFindAllIsCorrectWithVehicleCount(1);
	}
	
//	===================== test vehicle info correct ===========================
	
	@Test
	public void testVehicleInfoCorrect() throws Exception{
		Vehicle createdVehicle = newVehicle();
		Optional<Vehicle> foundVehicle = vehicleRepo.getById(createdVehicle.getId());
		checkAllInfoMatched(createdVehicle, foundVehicle.get());
	}
	
//	==================== test delete inexist vehicle ==========================
	@Test
	public void testDeleteNonExistVehicleAndNothingDeleted() throws Exception{
		boolean wasDeleted = vehicleRepo.delete(nonExistId);
		Assert.assertFalse(wasDeleted);
	}
	
//	==================== test delete exist vehicle ============================
	@Test
	public void testDeleteExistVehicleAndDeleted() throws Exception{
		Vehicle createdVehicle = newVehicle();
		assertExistingVehicleCount(1);
		boolean wasDeleted = vehicleRepo.delete(createdVehicle.getId());
		Assert.assertTrue(wasDeleted);
		assertNoExistingVehicle();
	}
	
//	=================== test update inexist vehicle ===========================
	@Test
	public void testUpdateNonExistVehicleAndNoUpdate() throws Exception{
		assertNoExistingVehicle();
		Vehicle updateVehicle = newVehicle();
		boolean updated = vehicleRepo.update(nonExistId, updateVehicle);
		Assert.assertFalse(updated);
	}
	
//	=================== test update exist vehicle =============================
	@Test
	public void testUpdateExistVehicleAndUpdated() throws Exception{
		Vehicle original = newVehicle();
		Vehicle updatedVehicle = generateUpdatedTestVehicle(original);
		boolean updated = vehicleRepo.update(original.getId(), updatedVehicle);
		Assert.assertTrue(updated);
	}
	
//	=================== test original vehicle info updated =====================
	@Test
	public void testUpdateExistVehicleInfoUpdated() throws Exception{
		Vehicle original = newVehicle();
		Vehicle updatedVehicle = generateUpdatedTestVehicle(original);
		vehicleRepo.update(original.getId(), updatedVehicle);
		Optional<Vehicle> foundVehicle = vehicleRepo.getById(original.getId());
		checkAllInfoMatched(original, foundVehicle.get());
		
	}
	

	
//	=================== test original vehicle info with null update =============
	@Test
	public void testUpdateExistVehicleWithNullUpdate() throws Exception{
		Vehicle original = newVehicle();
		boolean updated = vehicleRepo.update(original.getId(), null);
		Assert.assertFalse(updated);
	}
}
