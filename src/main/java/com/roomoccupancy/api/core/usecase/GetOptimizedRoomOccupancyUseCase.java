package com.roomoccupancy.api.core.usecase;

import org.springframework.stereotype.Service;

import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;

/**
 * Contains the business logic to make the best division of the rooms between
 * the possible guests
 * 
 * @author luis
 *
 */
@Service
public class GetOptimizedRoomOccupancyUseCase {

	public OptimizedRoomOccupancyEntity getOptimizedRoomOccupancy(Integer numberOfFreePremiumRooms,
			Integer numberOfFreeEconomyRooms, Integer[] potencialGuests) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
