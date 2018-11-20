package com.roomoccupancy.api.core.entity;

/**
 * Contains informations of how much rooms will be occupied, and how much money
 * the reservations will yield
 * 
 * @author luis
 *
 */
public class RoomCategoryOccupancyEntity {

	private Integer numberOfOccupiedRooms;

	private Integer generatedIncome;

	public RoomCategoryOccupancyEntity() {
	}

	public RoomCategoryOccupancyEntity(Integer numberOfOccupiedRooms, Integer generatedIncome) {
		this.generatedIncome = generatedIncome;
		this.numberOfOccupiedRooms = numberOfOccupiedRooms;
	}

	public Integer getNumberOfOccupiedRooms() {
		return numberOfOccupiedRooms;
	}

	public Integer getGeneratedIncome() {
		return generatedIncome;
	}
}
