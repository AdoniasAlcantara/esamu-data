package org.myopenproject.esamu.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_location")
public class Location {
	@Id @Column(name = "location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@PositiveOrZero
	private long id;
	
	@Column
	@Min(-90) @Max(90)
	private double latitude;
	
	@Column
	@Min(-180) @Max(180)
	private double longitude;
	
	@Column(name = "addr")
	private String address;
	
	@Column(columnDefinition = "VARCHAR(50)")
	@Size(max = 50)
	private String city;
	
	@Column(name = "stat", columnDefinition = "VARCHAR(50)")
	@Size(max = 50)
	private String state;
	
	@Column(columnDefinition = "VARCHAR(50)")
	@Size(max = 50)
	private String country;
	
	@Column(name = "postal_code", columnDefinition = "CHAR(8)")
	@Size(max = 8, min = 8) @Pattern(regexp = "\\d{8}$")
	private String postalCode;
	
	// Getters
	
	public long getId() {
		return id;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	// Setters
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location [id=").append(id)
			.append(", latitude=").append(latitude)
			.append(", longitude=").append(longitude)
			.append(", address=").append(address)
			.append(", city=").append(city)
			.append(", state=").append(state)
			.append(", country=").append(country)
			.append(", postalCode=").append(postalCode)
			.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Location))
			return false;
		Location other = (Location) obj;
		
		if (id != other.id)
			return false;
		
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		
		return true;
	}
}
