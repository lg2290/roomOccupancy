package com.roomoccupancy.api.entrypoint.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;
import com.roomoccupancy.api.core.usecase.GetOptimizedRoomOccupancyUseCase;
import com.roomoccupancy.api.entrypoint.v1.entity.GetOptimizedRoomOccupancyResponse;

/**
 * Contains the API endpoint to get the Optimized Room Occupancy
 * 
 * @author luis
 *
 */
@RestController
public class GetOptimizedRoomOccupancyEntrypoint {

	@Autowired
	GetOptimizedRoomOccupancyUseCase occupancyUseCase;

	@GetMapping("/v1/rooms/optimizeOccupancy")
	public ResponseEntity<GetOptimizedRoomOccupancyResponse> getOptimizedRoomOccupancy(
			@RequestParam(name = "freePremiumRooms") Integer numberOfFreePremiumRooms,
			@RequestParam(name = "freeEconomyRooms") Integer numberOfFreeEconomyRooms, Integer[] potencialGuests) {

		OptimizedRoomOccupancyEntity optimizedOccupancy = occupancyUseCase
				.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms, potencialGuests);

		return ResponseEntity.ok(GetOptimizedRoomOccupancyResponse.of(optimizedOccupancy));
	}

}
