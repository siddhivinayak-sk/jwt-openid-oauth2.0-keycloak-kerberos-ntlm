package com.sk.dto;

import java.io.Serializable;

/**
 * Region DTO class.
 *
 * @author Sandeep Kumar
 */
public class RegionDTO implements Serializable {
	
	/** The Constant serialVersionUID. */
	final private static long serialVersionUID = 1l;
	
	/** The id. */
	private Long id;
	
	/** The name. */
	private String name;
	
	/** The code. */
	private String code;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	
	
}
