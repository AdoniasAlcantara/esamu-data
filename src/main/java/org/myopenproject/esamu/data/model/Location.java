package org.myopenproject.esamu.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_location")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private double latitude;
	
	@Column(nullable = false)
	private double longitude;
	
	@Column(name = "addr", nullable = false)
	private String address;
	
	@Column(length = 50)
	private String city;
	
	@Column(length = 50)
	private String state;
	
	@Column(length = 50)
	private String country;
	
	@Column(name = "postal_code", columnDefinition = "CHAR(8)")
	private String postalCode;
	
	@Id
	@OneToOne
	@JoinColumn(name = "emergency_id")
	private Emergency emergency;

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

	public Emergency getEmergency() {
		return emergency;
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

	public void setEmergency(Emergency emergency) {
		this.emergency = emergency;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Location [")
				.append("latitude=").append(latitude)
				.append(", longitude=").append(longitude)
				.append(", address=").append(address)
				.append(", city=").append(city)
				.append(", state=").append(state)
				.append(", country=").append(country)
				.append(", postalCode=").append(postalCode)
				.append(", emergency=").append(emergency)
				.append("]");
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return 31 + (int) ((emergency == null) ? 0 : emergency.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Location other = (Location) obj;
		
		if (getEmergency() == null) {
			if (other.getEmergency() != null)
				return false;
		} else if (getEmergency().getId() != other.getEmergency().getId())
			return false;
		
		return true;
	}
}
