
package com.roomoccupancy.api.entrypoint.v1;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;
import com.roomoccupancy.api.core.entity.RoomCategoryOccupancyEntity;
import com.roomoccupancy.api.core.usecase.GetOptimizedRoomOccupancyUseCase;
import com.roomoccupancy.api.entrypoint.v1.entity.GetOptimizedRoomOccupancyResponse;

/**
 * Unit tests for the {@link GetOptimizedRoomOccupancyEntrypoint}
 * 
 * @author luis
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GetOptimizedRoomOccupancyEntrypointTest {

	@Mock
	GetOptimizedRoomOccupancyUseCase occupancyUseCase;

	@InjectMocks
	GetOptimizedRoomOccupancyEntrypoint occupancyEntrypoint;

	@Test
	public void getOptimizedRoomOccupancy_callWithNotNullArguments_useCaseCalledWithSameArguments() {
		Integer numberOfFreePremiumRooms = 8752;
		Integer numberOfFreeEconomyRooms = 432;
		Integer[] potencialGuests = { 132, 543, 981, 1 };

		RoomCategoryOccupancyEntity premiumOccupancy = new RoomCategoryOccupancyEntity(11, 1234);
		RoomCategoryOccupancyEntity economyOccupancy = new RoomCategoryOccupancyEntity(4, 712);
		OptimizedRoomOccupancyEntity optimizedRoomOccupancy = new OptimizedRoomOccupancyEntity(premiumOccupancy,
				economyOccupancy);

		Mockito.when(occupancyUseCase.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms,
				potencialGuests)).thenReturn(optimizedRoomOccupancy);

		ResponseEntity<GetOptimizedRoomOccupancyResponse> entrypointResponse = occupancyEntrypoint
				.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms, potencialGuests);

		Assert.assertNotNull(entrypointResponse);
		Assert.assertNotNull(entrypointResponse.getBody());
		Assert.assertThat(entrypointResponse.getBody().getResult(),
				Matchers.samePropertyValuesAs(optimizedRoomOccupancy));

		Integer oneTimeCall = 1;
		Mockito.verify(occupancyUseCase, Mockito.times(oneTimeCall)).getOptimizedRoomOccupancy(numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms, potencialGuests);
	}
}
