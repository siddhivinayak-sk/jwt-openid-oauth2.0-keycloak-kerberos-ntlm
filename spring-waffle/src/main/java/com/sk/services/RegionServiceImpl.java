package com.sk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.dto.RegionDTO;
import com.sk.entities.Region;
import com.sk.repositories.RegionRepository;

/**
 * Region Service implementation.
 *
 * @author Sandeep Kumar
 */
@Service
public class RegionServiceImpl implements RegionService {

	/** The region repository. */
	@Autowired
	private RegionRepository regionRepository;
	
	@Override
	public RegionDTO add(RegionDTO dto) {
		Region region = new Region();
		region.setCode(dto.getCode());
		region.setName(dto.getName());
		region = regionRepository.saveAndFlush(region);
		region.setId(region.getId());
		return dto;
	}
	
	@Override
	public RegionDTO update(RegionDTO dto) {
		Optional<Region> region = regionRepository.findById(dto.getId());
		region.ifPresent(r -> {
			r.setCode(dto.getCode());
			r.setName(dto.getName());
		});
		regionRepository.saveAndFlush(region.get());
		return dto;
	}

	@Override
	public RegionDTO delete(RegionDTO dto) {
		regionRepository.deleteById(dto.getId());
		return dto;
	}

	@Override
	public RegionDTO get(RegionDTO dto) {
		Optional<Region> op = regionRepository.findById(dto.getId());
		op.ifPresent(e -> {
			dto.setId(e.getId());
			dto.setName(e.getName());
			dto.setCode(e.getCode());
		});
		return dto;
	}
	
	
	@Override
	public List<RegionDTO> list() {
		return regionRepository.findAll()
				.stream()
				.map(e -> {
					RegionDTO d = new RegionDTO();
					d.setId(e.getId());
					d.setCode(e.getCode());
					d.setName(e.getName());
					return d;
				})
				.collect(Collectors.toList());
	}
	
	
}
