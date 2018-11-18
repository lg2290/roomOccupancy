package com.roomoccupancy.api.core.entity;

/**
 * Contains informations about the rooms' optimized division - how many Premium
 * and Economy rooms will be occupied, and how much money each category will
 * generate.
 * 
 * @author luis
 *
 */
public class OptimizedRoomOccupancyEntity {

	private RoomCategoryOccupancyEntity premiumOccupancy;

	private RoomCategoryOccupancyEntity economyOccupancy;

	public OptimizedRoomOccupancyEntity(RoomCategoryOccupancyEntity premiumOccupancy,
			RoomCategoryOccupancyEntity economyOccupancy) {
		this.premiumOccupancy = premiumOccupancy;
		this.economyOccupancy = economyOccupancy;
	}

	public RoomCategoryOccupancyEntity getPremiumOccupancy() {
		return premiumOccupancy;
	}

	public RoomCategoryOccupancyEntity getEconomyOccupancy() {
		return economyOccupancy;
	}

}
