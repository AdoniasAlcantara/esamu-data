package org.myopenproject.esamu.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {
	private static final long serialVersionUID = -281425919275703958L;

	@Id @Column(name = "user_id", columnDefinition = "CHAR(29)")
	@NotBlank @Pattern(regexp = ".{28}")
	private String id;
	
	@Column(columnDefinition = "CHAR(20)")
	@NotBlank @Pattern(regexp = "\\+?\\d+$")
	private String phone;
	
	@Column
	@NotBlank
	private String name;
	
	@Column(name = "notif_api_key", columnDefinition = "CHAR(36)")
	@NotBlank @Pattern(regexp = ".{8}-.{4}-.{4}-.{4}-.{12}")
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
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id)
			.append(", phone=").append(phone)
			.append(", name=").append(name)
			.append(", notificationKey=").append(notificationKey)
			.append("]");
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notificationKey == null) ? 0 : notificationKey.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		
		User other = (User) obj;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		if (notificationKey == null) {
			if (other.notificationKey != null)
				return false;
		} else if (!notificationKey.equals(other.notificationKey))
			return false;
		
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		
		return true;
	}
}
