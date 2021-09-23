package com.sk.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.dto.RegionDTO;
import com.sk.services.RegionService;

/**
 * Region Controller class
 * It provides list of end points for
 * managing region data.
 *
 * @author Sandeep Kumar
 */
@RestController
@RequestMapping("/region")
public class RegionController {

	/** The region service. */
	@Autowired
	private RegionService regionService;
	
	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping("/{id}")
	public ResponseEntity<RegionDTO> get(@PathVariable(name = "id")Long id) {
		RegionDTO dto = new RegionDTO();
		dto.setId(id);
		return new ResponseEntity<>(regionService.get(dto), HttpStatus.OK);
	}

	/**
	 * Put.
	 *
	 * @param dto the dto
	 * @return the response entity
	 */
	@PostMapping
	public ResponseEntity<RegionDTO> put(@RequestBody RegionDTO dto) {
		return new ResponseEntity<>(regionService.add(dto), HttpStatus.OK);
	}

	/**
	 * Patch.
	 *
	 * @param dto the dto
	 * @return the response entity
	 */
	@PatchMapping
	public ResponseEntity<RegionDTO> patch(@RequestBody RegionDTO dto) {
		return new ResponseEntity<>(regionService.update(dto), HttpStatus.OK);
	}
	
	
	/**
	 * Delete.
	 *
	 * @param dto the dto
	 * @return the response entity
	 */
	@DeleteMapping
	public ResponseEntity<RegionDTO> delete(@RequestBody RegionDTO dto) {
		return new ResponseEntity<>(regionService.delete(dto), HttpStatus.OK);
	}
	
	/**
	 * List.
	 *
	 * @return the response entity
	 */
	@GetMapping
	public ResponseEntity<List<RegionDTO>> list() {
		return new ResponseEntity<>(regionService.list(), HttpStatus.OK);
	}
}
