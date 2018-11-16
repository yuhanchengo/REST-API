package rest.test.integration.controller.util;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static rest.test.integration.controller.util.ControllerTestUtil.*;

import org.springframework.hateoas.EntityLinks;
import org.springframework.test.web.servlet.ResultMatcher;

import rest.domain.Vehicle;


public class VehicleControllerTestUtil {
	public static ResultMatcher vehicleAtIndexIsCorrect(int index, Vehicle expected) {
		return new CompositeResultMatcher().addMatcher(jsonPath("$.[" + index + "].id").value(expected.getId()))
			.addMatcher(jsonPath("$.[" + index + "].year").value(expected.getYear()))
			.addMatcher(jsonPath("$.[" + index + "].make").value(expected.getMake()))
			.addMatcher(jsonPath("$.[" + index + "].model").value(expected.getModel()));
	}
	
	public static ResultMatcher vehicleIsCorrect(Vehicle expected) {
		return vehicleIsCorrect(expected.getId(), expected);
	}
	
	private static ResultMatcher vehicleIsCorrect(Integer expectedId, Vehicle expected) {
		return new CompositeResultMatcher().addMatcher(jsonPath("$.id").value(expectedId)).addMatcher(jsonPath("$.year").value(expected.getYear()))
			.addMatcher(jsonPath("$.make").value(expected.getMake()))
			.addMatcher(jsonPath("$.model").value(expected.getModel()));
	}
	
	public static ResultMatcher updatedVehicleIsCorrect(Integer originalId, Vehicle expected) {
		return vehicleIsCorrect(originalId, expected);
	}
	
	public static ResultMatcher vehicleLinksAtIndexAreCorrect(int index, Vehicle expected, EntityLinks entityLinks) {
		final String selfReference = entityLinks.linkForSingleResource(expected).toString();
		return new CompositeResultMatcher().addMatcher(selfLinkAtIndexIs(index, selfReference))
			.addMatcher(updateLinkAtIndexIs(index, selfReference))
			.addMatcher(deleteLinkAtIndexIs(index, selfReference));
	}
	
	public static ResultMatcher vehicleLinksAreCorrect(Vehicle expected, EntityLinks entityLinks) {
		final String selfReference = entityLinks.linkForSingleResource(expected).toString();
		return new CompositeResultMatcher().addMatcher(selfLinkIs(selfReference))
			.addMatcher(updateLinkIs(selfReference))
			.addMatcher(deleteLinkIs(selfReference));
	}
}
