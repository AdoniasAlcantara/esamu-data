package org.myopenproject.esamu.data.model;

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

@Entity
@Table(name = "tb_emergency")
public class Emergency implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "emergency_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(columnDefinition = "CHAR(15)")
	private String imei;
	
	@OneToOne(mappedBy = "emergency", optional = false, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Location location;
	
	@Transient
	private Multimedia multimedia;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "start_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;
	
	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;
	
	@Column(name = "attach")
	private int attachment = -1;
	
	public Emergency() {}
	
	// Constructor for summary data. For use on "read-only" objects
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
	
	public int getAttachment() {
		return attachment;
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
	
	public void setAttachment(int attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Emergency [")
				.append("id=").append(id)
				.append(", user=").append(user)
				.append(", imei=").append(imei)
				.append(", location=").append(location)
				.append(", multimedia=").append(multimedia)
				.append(", status=").append(status)
				.append(", start=").append(start)
				.append(", end=").append(end)
				.append(", attachment").append(attachment)
				.append("]");
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return 31 + (int) (id ^ (id >>> 32));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Emergency other = (Emergency) obj;
		
		if (id != other.id)
			return false;
		
		return true;
	}

	public enum Status {PENDENT, PROGRESS, FINISHED, CANCELED}
}
