package org.myopenproject.esamu.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "tb_emergency")
public class Emergency implements Serializable {
	private static final long serialVersionUID = -4650383164897458340L;
	
	@Id @Column(name = "emergency_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@PositiveOrZero
	private long id;
	
	@ManyToOne @JoinColumn(name = "user_id")
	private User user;
	
	@Column(columnDefinition = "CHAR(15)")
	@Pattern(regexp = "\\d{15}$")
	private String imei;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, optional = false)
	@JoinColumn(name = "location_id")
	@Valid
	private Location location;
	
	@Transient
	@NotNull @Valid
	private Multimedia multimedia;
	
	@Column(columnDefinition = "CHAR(10)") @Enumerated(EnumType.STRING)
	@NotNull
	private Status status;
	
	@Column(name = "start_time") @Temporal(TemporalType.TIMESTAMP)
	private Date start;
	
	@Column(name = "end_time") @Temporal(TemporalType.TIMESTAMP)
	private Date end;
	
	public Emergency() {}
	
	// Constructor for summary data. Use it on "read-only" objects
	public Emergency(long id, String username, String userPhone, Date start, Status status) {
		this.id = id;
		this.start = start;
		this.status = status;
		user = new User();
		user.setPhone(userPhone);
		user.setName(username);
	}
	
	// Getters
	
	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getImei() {
		return imei;
	}

	public Location getLocation() {
		return location;
	}

	public Multimedia getMultimedia() {
		return multimedia;
	}

	public Status getStatus() {
		return status;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
	
	// Setters

	public void setId(long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setMultimedia(Multimedia multimedia) {
		this.multimedia = multimedia;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Emergency [id=").append(id)
			.append(", user=").append(user)
			.append(", imei=").append(imei)
			.append(", location=").append(location)
			.append(", multimedia=").append(multimedia)
			.append(", status=").append(status)
			.append(", start=").append(start)
			.append(", end=").append(end)
			.append("]");
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((imei == null) ? 0 : imei.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((multimedia == null) ? 0 : multimedia.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		if (!(obj instanceof Emergency))
			return false;
		
		Emergency other = (Emergency) obj;
		
		if (id != other.id)
			return false;
		
		if (imei == null) {
			if (other.imei != null)
				return false;
		} else if (!imei.equals(other.imei))
			return false;
		
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		
		if (multimedia == null) {
			if (other.multimedia != null)
				return false;
		} else if (!multimedia.equals(other.multimedia))
			return false;
		
		if (status != other.status)
			return false;
		
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		
		return true;
	}

	public enum Status {
		PENDENT, PROGRESS, FINISHED, CANCELED;
		
		public Status fromString(String status) {
			switch (status) {
			case "PENDENT":
				return PENDENT;
				
			case "PROGRESS":
				return PROGRESS;
				
			case "FINISHED":
				return FINISHED;
				
			case "CANCELED":
				return CANCELED;
				
			default:
				return PENDENT;
			}
		}
	}
}
