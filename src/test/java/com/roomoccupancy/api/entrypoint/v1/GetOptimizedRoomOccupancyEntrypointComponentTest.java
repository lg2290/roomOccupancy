package com.roomoccupancy.api.entrypoint.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;
import com.roomoccupancy.api.core.entity.RoomCategoryOccupancyEntity;
import com.roomoccupancy.api.entrypoint.v1.entity.GetOptimizedRoomOccupancyResponse;

/**
 * Component tests of the {@link GetOptimizedRoomOccupancyEntrypoint}
 * 
 * @author luis
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetOptimizedRoomOccupancyEntrypointComponentTest {

	private static final String GET_OPTIMIZED_ROOM_OCCUPANCY_URL = "/v1/rooms/optimizeOccupancy";

	private static final String FREE_PREMIUM_ROOMS_PARAMETER_KEY = "freePremiumRooms";

	private static final String FREE_ECONOMY_ROOMS_PARAMETER_KEY = "freeEconomyRooms";

	private static final String POTENTIAL_GUESTS_PARAMETER_KEY = "potentialGuests";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void getOptimizedRoomOccupancy_validParameters_successResponse() throws Exception {
		RoomCategoryOccupancyEntity expectedPremiumOccupancy = new RoomCategoryOccupancyEntity(2, 219);
		RoomCategoryOccupancyEntity expectedEconomyOccupancy = new RoomCategoryOccupancyEntity(1, 70);

		Integer freePremiumRooms = 2;
		Integer freeEconomyRooms = 1;
		Integer[] potentialGuestsOffers = { 120, 40, 70, 99 };

		MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();

		requestParameters.add(FREE_PREMIUM_ROOMS_PARAMETER_KEY, freePremiumRooms.toString());
		requestParameters.add(FREE_ECONOMY_ROOMS_PARAMETER_KEY, freeEconomyRooms.toString());

		for (Integer guestOffer : potentialGuestsOffers) {
			requestParameters.add(POTENTIAL_GUESTS_PARAMETER_KEY, guestOffer.toString());
		}

		ResultActions resultActions = this.mockMvc
				.perform(get(GET_OPTIMIZED_ROOM_OCCUPANCY_URL).params(requestParameters)).andExpect(status().isOk());

		String responseContentAsString = resultActions.andReturn().getResponse().getContentAsString();

		GetOptimizedRoomOccupancyResponse response = objectMapper.readValue(responseContentAsString,
				GetOptimizedRoomOccupancyResponse.class);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRequestTimestamp());
		Assert.assertNotNull(response.getRequestTimestamp().isBefore(Instant.now()));

		Assert.assertNotNull(response.getResult());
		OptimizedRoomOccupancyEntity result = response.getResult();

		Assert.assertNotNull(result.getEconomyOccupancy());
		Assert.assertEquals(expectedEconomyOccupancy.getNumberOfOccupiedRooms(),
				result.getEconomyOccupancy().getNumberOfOccupiedRooms());
		Assert.assertEquals(expectedEconomyOccupancy.getGeneratedIncome(),
				result.getEconomyOccupancy().getGeneratedIncome());

		Assert.assertNotNull(result.getPremiumOccupancy());
		Assert.assertEquals(expectedPremiumOccupancy.getNumberOfOccupiedRooms(),
				result.getPremiumOccupancy().getNumberOfOccupiedRooms());
		Assert.assertEquals(expectedPremiumOccupancy.getGeneratedIncome(),
				result.getPremiumOccupancy().getGeneratedIncome());

	}

	@Test
	public void getOptimizedRoomOccupancy_nullFreePremiumRoomsParameter_badRequest() throws Exception {
		Integer freeEconomyRooms = 1;
		Integer[] potentialGuestsOffers = { 120, 40, 70, 99 };

		MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();

		requestParameters.add(FREE_ECONOMY_ROOMS_PARAMETER_KEY, freeEconomyRooms.toString());

		for (Integer guestOffer : potentialGuestsOffers) {
			requestParameters.add(POTENTIAL_GUESTS_PARAMETER_KEY, guestOffer.toString());
		}

		assertBadRequest(requestParameters);
	}

	@Test
	public void getOptimizedRoomOccupancy_nullFreeEconomyRoomsParameter_badRequest() throws Exception {
		Integer freePremiumRooms = 2;
		Integer[] potentialGuestsOffers = { 120, 40, 70, 99 };

		MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();

		requestParameters.add(FREE_ECONOMY_ROOMS_PARAMETER_KEY, "");
		requestParameters.add(FREE_PREMIUM_ROOMS_PARAMETER_KEY, freePremiumRooms.toString());

		for (Integer guestOffer : potentialGuestsOffers) {
			requestParameters.add(POTENTIAL_GUESTS_PARAMETER_KEY, guestOffer.toString());
		}

		assertBadRequest(requestParameters);
	}

	@Test
	public void getOptimizedRoomOccupancy_nullPotentialGuestsParameter_badRequest() throws Exception {
		Integer freeEconomyRooms = 1;
		Integer freePremiumRooms = 2;

		MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();

		requestParameters.add(FREE_ECONOMY_ROOMS_PARAMETER_KEY, freeEconomyRooms.toString());
		requestParameters.add(FREE_PREMIUM_ROOMS_PARAMETER_KEY, freePremiumRooms.toString());
		requestParameters.add(POTENTIAL_GUESTS_PARAMETER_KEY, null);

		assertBadRequest(requestParameters);
	}

	private void assertBadRequest(MultiValueMap<String, String> requestParameters) throws Exception {
		this.mockMvc.perform(get(GET_OPTIMIZED_ROOM_OCCUPANCY_URL).params(requestParameters)).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").isNotEmpty())
				.andExpect(jsonPath("$.httpStatusCode").value(HttpStatus.BAD_REQUEST.value()));
	}

}
