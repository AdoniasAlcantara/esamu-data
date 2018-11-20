package org.myopenproject.esamu.data;

import java.util.Arrays;

import javax.validation.constraints.NotNull;

public class Multimedia {
	@NotNull
	private byte[] picture;
	
	private byte[] video;
	private byte[] voice;
	
	// Getters
	
	public byte[] getPicture() {
		return picture;
	}
	
	public byte[] getVideo() {
		return video;
	}
	
	public byte[] getVoice() {
		return voice;
	}
	
	// Setters
	
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
	public void setVideo(byte[] video) {
		this.video = video;
	}
	
	public void setVoice(byte[] voice) {
		this.voice = voice;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Multimedia [picture=").append(picture.length)
			.append(", video=").append(video.length)
			.append(", voice=").append(voice.length)
			.append("]");
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(picture);
		result = prime * result + Arrays.hashCode(video);
		result = prime * result + Arrays.hashCode(voice);
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (!(obj instanceof Multimedia))
			return false;
		
		Multimedia other = (Multimedia) obj;
		
		if (picture == null && other.picture != null)
			return false;
		else if (other.picture != null && picture.length != other.picture.length)
			return false;
		
		if (video == null && other.video != null)
			return false;
		else if (other.video != null && video.length != other.video.length)
			return false;
		
		if (voice == null && other.voice != null)
			return false;
		else if (other.voice != null && voice.length != other.voice.length)
			return false;
		
		return true;
	}
}
