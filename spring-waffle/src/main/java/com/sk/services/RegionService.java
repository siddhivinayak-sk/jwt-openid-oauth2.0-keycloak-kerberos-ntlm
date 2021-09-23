package com.sk.services;

import java.util.List;

import com.sk.dto.RegionDTO;

/**
 * Services for region.
 *
 * @author Sandeep Kumar
 */
public interface RegionService {

	/**
	 * Adds the.
	 *
	 * @param dto the dto
	 * @return the region DTO
	 */
	public RegionDTO add(RegionDTO dto);
	
	/**
	 * Update.
	 *
	 * @param dto the dto
	 * @return the region DTO
	 */
	public RegionDTO update(RegionDTO dto);
	
	/**
	 * Delete.
	 *
	 * @param dto the dto
	 * @return the region DTO
	 */
	public RegionDTO delete(RegionDTO dto);
	
	/**
	 * Gets the.
	 *
	 * @param dto the dto
	 * @return the region DTO
	 */
	public RegionDTO get(RegionDTO dto);
	
	/**
	 * List.
	 *
	 * @return the list
	 */
	public List<RegionDTO> list();
}
