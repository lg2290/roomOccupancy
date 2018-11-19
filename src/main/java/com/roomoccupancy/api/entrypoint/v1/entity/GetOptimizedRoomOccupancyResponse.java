package com.roomoccupancy.api.entrypoint.v1.entity;

import java.time.Instant;

import com.roomoccupancy.api.core.entity.OptimizedRoomOccupancyEntity;
import com.roomoccupancy.api.entrypoint.v1.GetOptimizedRoomOccupancyEntrypoint;

/**
 * Response of the
 * {@link GetOptimizedRoomOccupancyEntrypoint#getOptimizedRoomOccupancy(Integer, Integer, Integer[])}
 * 
 * @author luis
 *
 */
public class GetOptimizedRoomOccupancyResponse {

	private Instant requestTimestamp;

	private OptimizedRoomOccupancyEntity result;

	private GetOptimizedRoomOccupancyResponse(Instant requestTimestamp, OptimizedRoomOccupancyEntity result) {
		this.requestTimestamp = requestTimestamp;
		this.result = result;
	}

	/**
	 * Creates a response with the result passed as argument, and with
	 * {@link Instant#now()} as request timestamp
	 * 
	 * @param result
	 *            Result of the request
	 * @return New instance of GetOptimizedRoomOccupancyResponse
	 */
	public static GetOptimizedRoomOccupancyResponse of(OptimizedRoomOccupancyEntity result) {
		return new GetOptimizedRoomOccupancyResponse(Instant.now(), result);
	}

	public Instant getRequestTimestamp() {
		return requestTimestamp;
	}

	public OptimizedRoomOccupancyEntity getResult() {
		return result;
	}

}
