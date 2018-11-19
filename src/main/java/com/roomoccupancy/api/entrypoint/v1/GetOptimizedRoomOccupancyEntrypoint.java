package com.roomoccupancy.api.entrypoint.v1;

import org.springframework.http.ResponseEntity;

import com.roomoccupancy.api.entrypoint.v1.entity.GetOptimizedRoomOccupancyResponse;

/**
 * Contains the API endpoint to get the Optimized Room Occupancy
 * 
 * @author luis
 *
 */
public class GetOptimizedRoomOccupancyEntrypoint {

	public ResponseEntity<GetOptimizedRoomOccupancyResponse> getOptimizedRoomOccupancy(Integer numberOfFreePremiumRooms,
			Integer numberOfFreeEconomyRooms, Integer[] potencialGuests) {

		return null;
	}

}
