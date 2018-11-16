package rest.test.util;

import org.junit.Assert;

import rest.domain.Vehicle;

public class VehicleTestUtil {
	
	public static void checkAllInfoMatched(Vehicle original, Vehicle newVehicle) {
		Assert.assertEquals(original.getMake(), newVehicle.getMake());
		Assert.assertEquals(original.getModel(), newVehicle.getModel());
		Assert.assertEquals(original.getYear(), newVehicle.getYear());
	}
	
	public static Vehicle generateTestVehicle() {
		Vehicle v = new Vehicle();
		v.setMake("Honda");
		v.setModel("M3");
		v.setYear(1990);
		return v;
	}
	
	public static Vehicle generateUpdatedTestVehicle(Vehicle v) {
		v.setMake("New Honda");
		v.setModel("updated M3");
		v.setYear(1990);
		return v;
	}
}
