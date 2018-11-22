package com.roomoccupancy.api.core.usecase;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;
import com.roomoccupancy.api.core.exception.BusinessException;

/**
 * Unit tests for {@link GetOptimizedRoomOccupancyUseCase}
 * 
 * @author luis
 *
 */
public class GetOptimizedRoomOccupancyUseCaseTest {

	private static final Integer[] POTENTIAL_GUESTS = { 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 };

	private GetOptimizedRoomOccupancyUseCase occupancyUseCase = new GetOptimizedRoomOccupancyUseCase();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void getOptimizedRoomOccupancy_nullPotentialGuestsArray_throwBusinessException() {
		Integer numberOfFreePremiumRooms = 1;
		Integer numberOfFreeEconomyRooms = 1;
		Integer[] potencialGuests = null;

		callOptimizedRoomOccupancyAndAssertBusinessException(numberOfFreePremiumRooms, numberOfFreeEconomyRooms,
				potencialGuests, "The potential guests array is required.");
	}

	@Test
	public void getOptimizedRoomOccupancy_potentialGuestsArrayWithNullItem_throwBusinessException() {
		Integer numberOfFreePremiumRooms = 1;
		Integer numberOfFreeEconomyRooms = 1;
		Integer[] potencialGuests = { 100, 541, 43, null, 124 };

		callOptimizedRoomOccupancyAndAssertBusinessException(numberOfFreePremiumRooms, numberOfFreeEconomyRooms,
				potencialGuests, "The value that a guest is willing to pay must be a valid positive Integer");
	}

	@Test
	public void getOptimizedRoomOccupancy_potentialGuestsArrayWithNegativeItem_throwBusinessException() {
		Integer numberOfFreePremiumRooms = 1;
		Integer numberOfFreeEconomyRooms = 1;
		Integer[] potencialGuests = { 100, 541, 43, -1, 124 };

		callOptimizedRoomOccupancyAndAssertBusinessException(numberOfFreePremiumRooms, numberOfFreeEconomyRooms,
				potencialGuests, "The value that a guest is willing to pay must be a valid positive Integer");
	}

	@Test
	public void getOptimizedRoomOccupancy_nullNumberOfFreeEconomyRooms_throwBusinessException() {
		Integer numberOfFreePremiumRooms = 1;
		Integer numberOfFreeEconomyRooms = null;

		callOptimizedRoomOccupancyAndAssertBusinessException(numberOfFreePremiumRooms, numberOfFreeEconomyRooms,
				POTENTIAL_GUESTS, "The number of free Economic rooms is required.");

	}

	@Test
	public void getOptimizedRoomOccupancy_nullNumberOfFreePremiumRooms_throwBusinessException() {
		Integer numberOfFreePremiumRooms = null;
		Integer numberOfFreeEconomyRooms = 1;

		callOptimizedRoomOccupancyAndAssertBusinessException(numberOfFreePremiumRooms, numberOfFreeEconomyRooms,
				POTENTIAL_GUESTS, "The number of free Premium rooms is required.");

	}

	@Test
	public void getOptimizedRoomOccupancy_negativeNumberOfFreeEconomyRooms_throwBusinessException() {
		Integer numberOfFreePremiumRooms = 1;
		Integer numberOfFreeEconomyRooms = -1;

		callOptimizedRoomOccupancyAndAssertBusinessException(numberOfFreePremiumRooms, numberOfFreeEconomyRooms,
				POTENTIAL_GUESTS, "The number of free Economic rooms must be zero or greater.");

	}

	@Test
	public void getOptimizedRoomOccupancy_negativeNumberOfFreePremiumRooms_throwBusinessException() {
		Integer numberOfFreePremiumRooms = -1;
		Integer numberOfFreeEconomyRooms = 1;

		callOptimizedRoomOccupancyAndAssertBusinessException(numberOfFreePremiumRooms, numberOfFreeEconomyRooms,
				POTENTIAL_GUESTS, "The number of free Premium rooms must be zero or greater.");
	}

	@Test
	public void getOptimizedRoomOccupancy_emptyPotentialGuestsArray_0PremiunAnd0EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 0;
		Integer expectedPremiumRoomsIncome = 0;

		Integer expectedEconomyRoomsOccupied = 0;
		Integer expectedEconomyRoomsIncome = 0;

		Integer numberOfFreePremiumRooms = 3;
		Integer numberOfFreeEconomyRooms = 3;
		Integer[] potentialGuests = {};

		callOptimizedRoomOccupancyAndAssertResult(expectedPremiumRoomsOccupied, expectedPremiumRoomsIncome,
				expectedEconomyRoomsOccupied, expectedEconomyRoomsIncome, numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms, potentialGuests);
	}

	@Test
	public void getOptimizedRoomOccupancy_0PremiumAnd3EconomyFree_0PremiunAnd3EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 0;
		Integer expectedPremiumRoomsIncome = 0;

		Integer expectedEconomyRoomsOccupied = 3;
		Integer expectedEconomyRoomsIncome = 167;

		Integer numberOfFreePremiumRooms = 0;
		Integer numberOfFreeEconomyRooms = 3;

		callOptimizedRoomOccupancyAndAssertResult(expectedPremiumRoomsOccupied, expectedPremiumRoomsIncome,
				expectedEconomyRoomsOccupied, expectedEconomyRoomsIncome, numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms);
	}

	@Test
	public void getOptimizedRoomOccupancy_3PremiumAnd0EconomyFree_3PremiumAnd0EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 3;
		Integer expectedPremiumRoomsIncome = 738;

		Integer expectedEconomyRoomsOccupied = 0;
		Integer expectedEconomyRoomsIncome = 0;

		Integer numberOfFreePremiumRooms = 3;
		Integer numberOfFreeEconomyRooms = 0;

		callOptimizedRoomOccupancyAndAssertResult(expectedPremiumRoomsOccupied, expectedPremiumRoomsIncome,
				expectedEconomyRoomsOccupied, expectedEconomyRoomsIncome, numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms);
	}

	@Test
	public void getOptimizedRoomOccupancy_3PremiumAnd3EconomyFree_3PremiunAnd3EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 3;
		Integer expectedPremiumRoomsIncome = 738;

		Integer expectedEconomyRoomsOccupied = 3;
		Integer expectedEconomyRoomsIncome = 167;

		Integer numberOfFreePremiumRooms = 3;
		Integer numberOfFreeEconomyRooms = 3;

		callOptimizedRoomOccupancyAndAssertResult(expectedPremiumRoomsOccupied, expectedPremiumRoomsIncome,
				expectedEconomyRoomsOccupied, expectedEconomyRoomsIncome, numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms);
	}

	@Test
	public void getOptimizedRoomOccupancy_7PremiunAnd5EconomyFree_6PremiunAnd4EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 6;
		Integer expectedPremiumRoomsIncome = 1054;

		Integer expectedEconomyRoomsOccupied = 4;
		Integer expectedEconomyRoomsIncome = 189;

		Integer numberOfFreePremiumRooms = 7;
		Integer numberOfFreeEconomyRooms = 5;

		callOptimizedRoomOccupancyAndAssertResult(expectedPremiumRoomsOccupied, expectedPremiumRoomsIncome,
				expectedEconomyRoomsOccupied, expectedEconomyRoomsIncome, numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms);
	}

	@Test
	public void getOptimizedRoomOccupancy_2PremiunAnd7EconomyFree_2PremiunAnd4EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 2;
		Integer expectedPremiumRoomsIncome = 583;

		Integer expectedEconomyRoomsOccupied = 4;
		Integer expectedEconomyRoomsIncome = 189;

		Integer numberOfFreePremiumRooms = 2;
		Integer numberOfFreeEconomyRooms = 7;

		callOptimizedRoomOccupancyAndAssertResult(expectedPremiumRoomsOccupied, expectedPremiumRoomsIncome,
				expectedEconomyRoomsOccupied, expectedEconomyRoomsIncome, numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms);
	}

	@Test
	public void getOptimizedRoomOccupancy_7PremiunAnd1EconomyFree_7PremiunAnd1EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 7;
		Integer expectedPremiumRoomsIncome = 1153;

		Integer expectedEconomyRoomsOccupied = 1;
		Integer expectedEconomyRoomsIncome = 45;

		Integer numberOfFreePremiumRooms = 7;
		Integer numberOfFreeEconomyRooms = 1;

		callOptimizedRoomOccupancyAndAssertResult(expectedPremiumRoomsOccupied, expectedPremiumRoomsIncome,
				expectedEconomyRoomsOccupied, expectedEconomyRoomsIncome, numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms);
	}

	private void callOptimizedRoomOccupancyAndAssertResult(Integer expectedPremiumRoomsOccupied,
			Integer expectedPremiumRoomsIncome, Integer expectedEconomyRoomsOccupied,
			Integer expectedEconomyRoomsIncome, Integer numberOfFreePremiumRooms, Integer numberOfFreeEconomyRooms) {
		callOptimizedRoomOccupancyAndAssertResult(expectedPremiumRoomsOccupied, expectedPremiumRoomsIncome,
				expectedEconomyRoomsOccupied, expectedEconomyRoomsIncome, numberOfFreePremiumRooms,
				numberOfFreeEconomyRooms, POTENTIAL_GUESTS);
	}

	private void callOptimizedRoomOccupancyAndAssertResult(Integer expectedPremiumRoomsOccupied,
			Integer expectedPremiumRoomsIncome, Integer expectedEconomyRoomsOccupied,
			Integer expectedEconomyRoomsIncome, Integer numberOfFreePremiumRooms, Integer numberOfFreeEconomyRooms,
			Integer[] potentialGuests) {

		OptimizedRoomOccupancyEntity roomOccupancy = occupancyUseCase
				.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms, potentialGuests);

		Assert.assertNotNull(roomOccupancy);
		Assert.assertNotNull(roomOccupancy.getEconomyOccupancy());
		Assert.assertEquals(expectedEconomyRoomsOccupied,
				roomOccupancy.getEconomyOccupancy().getNumberOfOccupiedRooms());
		Assert.assertEquals(expectedEconomyRoomsIncome, roomOccupancy.getEconomyOccupancy().getGeneratedIncome());
		Assert.assertNotNull(roomOccupancy.getPremiumOccupancy());
		Assert.assertEquals(expectedPremiumRoomsOccupied,
				roomOccupancy.getPremiumOccupancy().getNumberOfOccupiedRooms());
		Assert.assertEquals(expectedPremiumRoomsIncome, roomOccupancy.getPremiumOccupancy().getGeneratedIncome());
	}

	private void callOptimizedRoomOccupancyAndAssertBusinessException(Integer numberOfFreePremiumRooms,
			Integer numberOfFreeEconomyRooms, Integer[] potencialGuests, String expectedExceptionMessage) {

		expectedException.expectMessage(expectedExceptionMessage);
		expectedException.expect(BusinessException.class);
		occupancyUseCase.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms, potencialGuests);
	}

}
