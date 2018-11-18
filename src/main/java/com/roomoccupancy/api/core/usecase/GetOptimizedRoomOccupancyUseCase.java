package com.roomoccupancy.api.core.usecase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;
import com.roomoccupancy.api.core.entity.RoomCategoryOccupancyEntity;

/**
 * Contains the business logic to make the best division of the rooms between
 * the possible guests
 * 
 * @author luis
 *
 */
@Service
public class GetOptimizedRoomOccupancyUseCase {

	private static final Integer PREMIUM_ROOM_MINIMUN_NIGHT_VALUE = 100;

	private static final Integer ZERO = 0;

	public OptimizedRoomOccupancyEntity getOptimizedRoomOccupancy(Integer numberOfFreePremiumRooms,
			Integer numberOfFreeEconomyRooms, Integer[] potencialGuests) {
		List<Integer> orderedPotencialGuests = Arrays.asList(potencialGuests);
		orderedPotencialGuests.sort(Collections.reverseOrder());

		RoomCategoryOccupancyEntity premiumRoomsOccupancy = getRoomOccupancyFromPotencialGuests(orderedPotencialGuests,
				numberOfFreePremiumRooms);

		RoomCategoryOccupancyEntity economyRoomsOccupancy = getRoomOccupancyForEconomyGuests(orderedPotencialGuests,
				numberOfFreeEconomyRooms, premiumRoomsOccupancy.getNumberOfOccupiedRooms());

		return new OptimizedRoomOccupancyEntity(premiumRoomsOccupancy, economyRoomsOccupancy);
	}

	private RoomCategoryOccupancyEntity getRoomOccupancyForEconomyGuests(List<Integer> orderedPotencialGuests,
			Integer numberOfFreeEconomyRooms, Integer numberOfOccupiedPremiumRooms) {
		List<Integer> potencialEconomyGuests = Collections.emptyList();

		if (orderedPotencialGuests.size() > numberOfOccupiedPremiumRooms) {
			potencialEconomyGuests = orderedPotencialGuests
					.subList(numberOfFreeEconomyRooms, orderedPotencialGuests.size()).stream()
					.filter(g -> PREMIUM_ROOM_MINIMUN_NIGHT_VALUE.compareTo(g) > ZERO).collect(Collectors.toList());
		}

		return getRoomOccupancyFromPotencialGuests(potencialEconomyGuests, numberOfFreeEconomyRooms);
	}

	private RoomCategoryOccupancyEntity getRoomOccupancyFromPotencialGuests(List<Integer> potencialGuestsValue,
			Integer numberOfFreeRooms) {
		if (potencialGuestsValue.size() > numberOfFreeRooms) {
			potencialGuestsValue = potencialGuestsValue.subList(ZERO, numberOfFreeRooms);
		}

		return new RoomCategoryOccupancyEntity(potencialGuestsValue.size(),
				potencialGuestsValue.stream().mapToInt(Integer::intValue).sum());
	}

}
