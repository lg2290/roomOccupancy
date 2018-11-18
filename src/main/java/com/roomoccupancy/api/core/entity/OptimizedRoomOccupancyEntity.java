package com.roomoccupancy.api.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains informations about the rooms' optimized division - how many Premium
 * and Economy rooms will be occupied, and how much money each category will
 * generate.
 * 
 * @author luis
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimizedRoomOccupancyEntity {

	private RoomCategoryOccupancyEntity premiumOccupancy;

	private RoomCategoryOccupancyEntity economyOccupancy;

}
