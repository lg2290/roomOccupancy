package com.roomoccupancy.api.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains informations of how much rooms will be occupied, and how much money
 * the reservations will yield
 * 
 * @author luis
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCategoryOccupancyEntity {

	private Integer numberOfOccupiedRooms;

	private Integer generatedIncome;
}
