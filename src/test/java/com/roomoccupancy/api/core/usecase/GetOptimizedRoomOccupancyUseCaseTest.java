package com.roomoccupancy.api.core.usecase;

import org.junit.Assert;
import org.junit.Test;

import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;
import com.roomoccupancy.api.core.entity.RoomCategoryOccupancyEntity;

/**
 * Unit tests for {@link GetOptimizedRoomOccupancyUseCase}
 * 
 * @author luis
 *
 */
public class GetOptimizedRoomOccupancyUseCaseTest {

	private static final Integer[] POTENTIAL_GUESTS = { 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 };

	private GetOptimizedRoomOccupancyUseCase occupancyUseCase = new GetOptimizedRoomOccupancyUseCase();

	@Test
	public void getOptimizedRoomOccupancy_3PremiunAnd3EconomyFree_3PremiunAnd3EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 3;
		Integer expectedPremiumRoomsIncome = 738;
		RoomCategoryOccupancyEntity premiumRoomOccupancy = new RoomCategoryOccupancyEntity(expectedPremiumRoomsOccupied,
				expectedPremiumRoomsIncome);

		Integer expectedEconomyRoomsOccupied = 3;
		Integer expectedEconomyRoomsIncome = 167;
		RoomCategoryOccupancyEntity economyRoomOccupancy = new RoomCategoryOccupancyEntity(expectedEconomyRoomsOccupied,
				expectedEconomyRoomsIncome);

		OptimizedRoomOccupancyEntity expectedRoomOccupancy = new OptimizedRoomOccupancyEntity(premiumRoomOccupancy,
				economyRoomOccupancy);

		Integer numberOfFreePremiumRooms = 3;
		Integer numberOfFreeEconomyRooms = 3;

		OptimizedRoomOccupancyEntity roomOccupancy = occupancyUseCase
				.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms, POTENTIAL_GUESTS);

		Assert.assertNotNull(roomOccupancy);
		Assert.assertNotNull(roomOccupancy.getEconomyOccupancy());
		Assert.assertEquals(expectedRoomOccupancy.getEconomyOccupancy().getNumberOfOccupiedRooms(),
				expectedEconomyRoomsOccupied);
		Assert.assertEquals(expectedRoomOccupancy.getEconomyOccupancy().getGeneratedIncome(),
				expectedEconomyRoomsIncome);
		Assert.assertNotNull(roomOccupancy.getPremiumOccupancy());
		Assert.assertEquals(expectedRoomOccupancy.getPremiumOccupancy().getNumberOfOccupiedRooms(),
				expectedPremiumRoomsOccupied);
		Assert.assertEquals(expectedRoomOccupancy.getPremiumOccupancy().getGeneratedIncome(),
				expectedPremiumRoomsIncome);
	}

	@Test
	public void getOptimizedRoomOccupancy_7PremiunAnd5EconomyFree_6PremiunAnd4EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 6;
		Integer expectedPremiumRoomsIncome = 1054;
		RoomCategoryOccupancyEntity premiumRoomOccupancy = new RoomCategoryOccupancyEntity(expectedPremiumRoomsOccupied,
				expectedPremiumRoomsIncome);

		Integer expectedEconomyRoomsOccupied = 4;
		Integer expectedEconomyRoomsIncome = 189;
		RoomCategoryOccupancyEntity economyRoomOccupancy = new RoomCategoryOccupancyEntity(expectedEconomyRoomsOccupied,
				expectedEconomyRoomsIncome);

		OptimizedRoomOccupancyEntity expectedRoomOccupancy = new OptimizedRoomOccupancyEntity(premiumRoomOccupancy,
				economyRoomOccupancy);

		Integer numberOfFreePremiumRooms = 7;
		Integer numberOfFreeEconomyRooms = 5;

		OptimizedRoomOccupancyEntity roomOccupancy = occupancyUseCase
				.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms, POTENTIAL_GUESTS);

		Assert.assertNotNull(roomOccupancy);
		Assert.assertNotNull(roomOccupancy.getEconomyOccupancy());
		Assert.assertEquals(expectedRoomOccupancy.getEconomyOccupancy().getNumberOfOccupiedRooms(),
				expectedEconomyRoomsOccupied);
		Assert.assertEquals(expectedRoomOccupancy.getEconomyOccupancy().getGeneratedIncome(),
				expectedEconomyRoomsIncome);
		Assert.assertNotNull(roomOccupancy.getPremiumOccupancy());
		Assert.assertEquals(expectedRoomOccupancy.getPremiumOccupancy().getNumberOfOccupiedRooms(),
				expectedPremiumRoomsOccupied);
		Assert.assertEquals(expectedRoomOccupancy.getPremiumOccupancy().getGeneratedIncome(),
				expectedPremiumRoomsIncome);
	}

	@Test
	public void getOptimizedRoomOccupancy_2PremiunAnd7EconomyFree_2PremiunAnd4EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 2;
		Integer expectedPremiumRoomsIncome = 583;
		RoomCategoryOccupancyEntity premiumRoomOccupancy = new RoomCategoryOccupancyEntity(expectedPremiumRoomsOccupied,
				expectedPremiumRoomsIncome);

		Integer expectedEconomyRoomsOccupied = 4;
		Integer expectedEconomyRoomsIncome = 189;
		RoomCategoryOccupancyEntity economyRoomOccupancy = new RoomCategoryOccupancyEntity(expectedEconomyRoomsOccupied,
				expectedEconomyRoomsIncome);

		OptimizedRoomOccupancyEntity expectedRoomOccupancy = new OptimizedRoomOccupancyEntity(premiumRoomOccupancy,
				economyRoomOccupancy);

		Integer numberOfFreePremiumRooms = 2;
		Integer numberOfFreeEconomyRooms = 7;

		OptimizedRoomOccupancyEntity roomOccupancy = occupancyUseCase
				.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms, POTENTIAL_GUESTS);

		Assert.assertNotNull(roomOccupancy);
		Assert.assertNotNull(roomOccupancy.getEconomyOccupancy());
		Assert.assertEquals(expectedRoomOccupancy.getEconomyOccupancy().getNumberOfOccupiedRooms(),
				expectedEconomyRoomsOccupied);
		Assert.assertEquals(expectedRoomOccupancy.getEconomyOccupancy().getGeneratedIncome(),
				expectedEconomyRoomsIncome);
		Assert.assertNotNull(roomOccupancy.getPremiumOccupancy());
		Assert.assertEquals(expectedRoomOccupancy.getPremiumOccupancy().getNumberOfOccupiedRooms(),
				expectedPremiumRoomsOccupied);
		Assert.assertEquals(expectedRoomOccupancy.getPremiumOccupancy().getGeneratedIncome(),
				expectedPremiumRoomsIncome);
	}

	@Test
	public void getOptimizedRoomOccupancy_7PremiunAnd1EconomyFree_7PremiunAnd1EconomyUsage() {
		Integer expectedPremiumRoomsOccupied = 7;
		Integer expectedPremiumRoomsIncome = 1153;
		RoomCategoryOccupancyEntity premiumRoomOccupancy = new RoomCategoryOccupancyEntity(expectedPremiumRoomsOccupied,
				expectedPremiumRoomsIncome);

		Integer expectedEconomyRoomsOccupied = 1;
		Integer expectedEconomyRoomsIncome = 45;
		RoomCategoryOccupancyEntity economyRoomOccupancy = new RoomCategoryOccupancyEntity(expectedEconomyRoomsOccupied,
				expectedEconomyRoomsIncome);

		OptimizedRoomOccupancyEntity expectedRoomOccupancy = new OptimizedRoomOccupancyEntity(premiumRoomOccupancy,
				economyRoomOccupancy);

		Integer numberOfFreePremiumRooms = 7;
		Integer numberOfFreeEconomyRooms = 1;

		OptimizedRoomOccupancyEntity roomOccupancy = occupancyUseCase
				.getOptimizedRoomOccupancy(numberOfFreePremiumRooms, numberOfFreeEconomyRooms, POTENTIAL_GUESTS);

		Assert.assertNotNull(roomOccupancy);
		Assert.assertNotNull(roomOccupancy.getEconomyOccupancy());
		Assert.assertEquals(expectedRoomOccupancy.getEconomyOccupancy().getNumberOfOccupiedRooms(),
				expectedEconomyRoomsOccupied);
		Assert.assertEquals(expectedRoomOccupancy.getEconomyOccupancy().getGeneratedIncome(),
				expectedEconomyRoomsIncome);
		Assert.assertNotNull(roomOccupancy.getPremiumOccupancy());
		Assert.assertEquals(expectedRoomOccupancy.getPremiumOccupancy().getNumberOfOccupiedRooms(),
				expectedPremiumRoomsOccupied);
		Assert.assertEquals(expectedRoomOccupancy.getPremiumOccupancy().getGeneratedIncome(),
				expectedPremiumRoomsIncome);
	}

}
