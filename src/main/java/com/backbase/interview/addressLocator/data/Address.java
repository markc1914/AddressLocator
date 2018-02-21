package com.backbase.interview.addressLocator.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "The formatted address to return")
public class Address {

	
	@ApiModelProperty(value = "The Google Formatter Address", required = true)
	String formattedAddress;
	@ApiModelProperty(value = "The Lattitude in degrees")
	Double lat;
	@ApiModelProperty(value = "The Longitude in degrees")
	Double lon;
	
	
	public Address(String formattedAddress, Double lat, Double lon) {
		this.formattedAddress = formattedAddress;
		this.lat = lat;
		this.lon = lon;
	}
	
	
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	
	
}
