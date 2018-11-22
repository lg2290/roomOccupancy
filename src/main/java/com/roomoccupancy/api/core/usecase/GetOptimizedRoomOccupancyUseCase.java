package com.roomoccupancy.api.core.usecase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

	private static final String ERROR_NEGATIVE_FREE_ECONOMIC_ROOMS = "The number of free Economic rooms must be zero or greater.";

	private static final String ERROR_NULL_FREE_PREMIUM_ROOMS = "The number of free Premium rooms is required.";

	private static final String ERROR_NEGATIVE_FREE_PREMIUM_ROOMS = "The number of free Premium rooms must be zero or greater.";

	private static final String ERROR_NULL_OR_NEGATIVE_GUEST_OFFER = "The value that a guest is willing to pay must be a valid positive Integer";

	/**
	 * Method that optimizes the division of potential guests on the free rooms
	 * available, taking into account the amount of money that they are willing to
	 * pay for a night, and the category of the rooms.
	 * 
	 * @param numberOfFreePremiumRooms
	 *            Free Premium Rooms
	 * @param numberOfFreeEconomyRooms
	 *            Free Economy Rooms
	 * @param potencialGuests
	 *            Array of prices that potential guests are willing to pay for a
	 *            night
	 * @return Number of economy and premium rooms that will be occupied, and the
	 *         amount of money that it will generate
	 * @throws BusinessException
	 */
	public OptimizedRoomOccupancyEntity getOptimizedRoomOccupancy(Integer numberOfFreePremiumRooms,
			Integer numberOfFreeEconomyRooms, Integer[] potencialGuests) {

		validateFreeRoomsParameters(numberOfFreePremiumRooms, numberOfFreeEconomyRooms);

		List<Integer> orderedPotencialGuests = validatePotentialGuestsAndGetAsOrderedList(potencialGuests);

		Integer numberOfPotentialEconomyGuests = ((Long) orderedPotencialGuests.stream()
				.filter(this::isPotentialGuestForEconomyRoom).count()).intValue();

		Integer numberOfEconomyGuests = Math.min(numberOfFreeEconomyRooms, numberOfPotentialEconomyGuests);

		Integer numberOfPotentialPremiumGuests = potencialGuests.length - numberOfPotentialEconomyGuests;

		Integer numberOfPotentialEconomyGuestsWithoutRoom = numberOfPotentialEconomyGuests - numberOfEconomyGuests;

		Integer numberOfPremiumGuests = getNumberOfPremiumGuests(numberOfFreePremiumRooms,
				numberOfPotentialPremiumGuests, numberOfPotentialEconomyGuestsWithoutRoom);

		RoomCategoryOccupancyEntity premiumRoomsOccupancy = getRoomOccupancyForPremiumGuests(orderedPotencialGuests,
				numberOfPremiumGuests.intValue());

		Integer numberOfGuestsForPremiumRooms = Math.max(numberOfPremiumGuests, numberOfPotentialPremiumGuests);

		RoomCategoryOccupancyEntity economyRoomsOccupancy = getRoomOccupancyForEconomyGuests(orderedPotencialGuests,
				numberOfEconomyGuests, numberOfGuestsForPremiumRooms);

		return new OptimizedRoomOccupancyEntity(premiumRoomsOccupancy, economyRoomsOccupancy);
	}

	/**
	 * Validate if the free rooms parameters are not null nor negative
	 * 
	 * @param numberOfFreePremiumRooms
	 * @param numberOfFreeEconomyRooms
	 */
	private void validateFreeRoomsParameters(Integer numberOfFreePremiumRooms, Integer numberOfFreeEconomyRooms) {
		if (Objects.isNull(numberOfFreeEconomyRooms)) {
			throw new BusinessException(ERROR_NULL_FREE_ECONOMIC_ROOMS);
		} else if (numberOfFreeEconomyRooms < ZERO) {
			throw new BusinessException(ERROR_NEGATIVE_FREE_ECONOMIC_ROOMS);
		}

		if (Objects.isNull(numberOfFreePremiumRooms)) {
			throw new BusinessException(ERROR_NULL_FREE_PREMIUM_ROOMS);
		} else if (numberOfFreePremiumRooms < ZERO) {
			throw new BusinessException(ERROR_NEGATIVE_FREE_PREMIUM_ROOMS);
		}
	}

	private List<Integer> validatePotentialGuestsAndGetAsOrderedList(Integer[] potencialGuests) {
		if (Objects.isNull(potencialGuests)) {
			throw new BusinessException(ERROR_NULL_POTENTIAL_GUESTS_ARRAY);
		}

		List<Integer> potencialGuestsList = Arrays.asList(potencialGuests);

		if (potencialGuestsList.stream().anyMatch(g -> (Objects.isNull(g) || ZERO.compareTo(g) > ZERO))) {
			throw new BusinessException(ERROR_NULL_OR_NEGATIVE_GUEST_OFFER);
		}

		potencialGuestsList.sort(Collections.reverseOrder());

		return potencialGuestsList;
	}

	private boolean isPotentialGuestForEconomyRoom(Integer guestOffer) {
		return PREMIUM_ROOM_MINIMUN_NIGHT_VALUE.compareTo(guestOffer) > 0;
	}

	private RoomCategoryOccupancyEntity getRoomOccupancyForPremiumGuests(List<Integer> orderedPotencialGuests,
			Integer numberOfPremiumGuests) {
		List<Integer> premiumRoomsGuests = orderedPotencialGuests.subList(0, numberOfPremiumGuests);

		return getRoomCategoryOccupancyFromGuests(premiumRoomsGuests);
	}

	private RoomCategoryOccupancyEntity getRoomOccupancyForEconomyGuests(List<Integer> orderedPotencialGuests,
			Integer numberOfEconomyGuests, Integer numberOfGuestsForPremiumRooms) {

		List<Integer> economyRoomsGuests = orderedPotencialGuests.subList(numberOfGuestsForPremiumRooms,
				numberOfGuestsForPremiumRooms + numberOfEconomyGuests);

		return getRoomCategoryOccupancyFromGuests(economyRoomsGuests);
	}

	private RoomCategoryOccupancyEntity getRoomCategoryOccupancyFromGuests(List<Integer> guests) {
		return new RoomCategoryOccupancyEntity(guests.size(), guests.stream().mapToInt(Integer::intValue).sum());
	}

	private Integer getNumberOfPremiumGuests(Integer numberOfFreePremiumRooms, Integer numberOfPotentialPremiumGuests,
			Integer numberOfPotentialEconomyGuestsWithoutRoom) {
		Integer numberOfPremiumGuests = Math.min(numberOfFreePremiumRooms, numberOfPotentialPremiumGuests);

		Integer numberOfPremiumRoomsAvailableForEconomyGuests = numberOfFreePremiumRooms - numberOfPremiumGuests;

		if ((numberOfPotentialEconomyGuestsWithoutRoom > 0) && (numberOfPremiumRoomsAvailableForEconomyGuests > 0)) {
			numberOfPremiumGuests += Math.min(numberOfPotentialEconomyGuestsWithoutRoom,
					numberOfPremiumRoomsAvailableForEconomyGuests);
		}

		return numberOfPremiumGuests;
	}

}
