package com.sk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sk.entities.Region;

/**
 * This interface is Repository interface
 * for the JPA repository.
 *
 * @author Sandeep Kumar
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}
