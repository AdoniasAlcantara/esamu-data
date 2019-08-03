package org.myopenproject.esamu.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@Column(name = "user_id", columnDefinition = "CHAR(29)")
	private String id;
	
	@Column(length = 20, nullable = false)
	private String phone;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@Column(name = "notif_api_key", nullable = false, columnDefinition = "CHAR(36)")
	private String notificationKey;
	
	// Getters

	public String getId() {
		return id;
	}

	public String getPhone() {
		return phone;
	}

	public String getName() {
		return name;
	}

	public String getNotificationKey() {
		return notificationKey;
	}

	// Setters
	
	public void setId(String id) {
		this.id = id;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNotificationKey(String notificationKey) {
		this.notificationKey = notificationKey;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("User [")
				.append("id=").append(id)
				.append(", phone=").append(phone)
				.append(", name=").append(name)
				.append(", notificationKey=").append(notificationKey)
				.append("]");
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		User other = (User) obj;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}
}
