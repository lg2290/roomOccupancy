package com.roomoccupancy.api.core.usecase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;
import com.roomoccupancy.api.core.entity.RoomCategoryOccupancyEntity;
import com.roomoccupancy.api.core.exception.BusinessException;

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

	private static final String ERROR_NULL_POTENTIAL_GUESTS_ARRAY = "The potential guests array is required.";

	private static final String ERROR_NULL_FREE_ECONOMIC_ROOMS = "The number of free Economic rooms is required.";

	private static final String ERROR_NULL_FREE_PREMIUM_ROOMS = "The number of free Premium rooms is required.";

	/**
	 * 
	 * @param numberOfFreePremiumRooms
	 * @param numberOfFreeEconomyRooms
	 * @param potencialGuests
	 * @return
	 * @throws BusinessException
	 */
	public OptimizedRoomOccupancyEntity getOptimizedRoomOccupancy(Integer numberOfFreePremiumRooms,
			Integer numberOfFreeEconomyRooms, Integer[] potencialGuests) {

		validateFreeRoomsParameters(numberOfFreePremiumRooms, numberOfFreeEconomyRooms);

		List<Integer> orderedPotencialGuests = validatePotentialGuestsAndGetAsOrderedList(potencialGuests);

		RoomCategoryOccupancyEntity premiumRoomsOccupancy = getRoomOccupancyFromPotencialGuests(orderedPotencialGuests,
				numberOfFreePremiumRooms);

		RoomCategoryOccupancyEntity economyRoomsOccupancy = getRoomOccupancyForEconomyGuests(orderedPotencialGuests,
				numberOfFreeEconomyRooms, premiumRoomsOccupancy.getNumberOfOccupiedRooms());

		return new OptimizedRoomOccupancyEntity(premiumRoomsOccupancy, economyRoomsOccupancy);
	}

	private void validateFreeRoomsParameters(Integer numberOfFreePremiumRooms, Integer numberOfFreeEconomyRooms) {
		if (Objects.isNull(numberOfFreeEconomyRooms)) {
			throw new BusinessException(ERROR_NULL_FREE_ECONOMIC_ROOMS);
		}

		if (Objects.isNull(numberOfFreePremiumRooms)) {
			throw new BusinessException(ERROR_NULL_FREE_PREMIUM_ROOMS);
		}
	}

	private List<Integer> validatePotentialGuestsAndGetAsOrderedList(Integer[] potencialGuests) {
		if (Objects.isNull(potencialGuests)) {
			throw new BusinessException(ERROR_NULL_POTENTIAL_GUESTS_ARRAY);
		}

		List<Integer> potencialGuestsList = Arrays.asList(potencialGuests);
		potencialGuestsList.sort(Collections.reverseOrder());

		return potencialGuestsList;
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
