package com.example.security.models;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "geo")
public class Geo extends UserAudit {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "lat")
	private String lat;

	@Column(name = "lng")
	private String lng;

	@OneToOne(mappedBy = "geo")
	private Address address;

	public Geo(String lat, String lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public Geo() {
		super();
	}

	@JsonIgnore
	@Override
	public Long getCreatedBy() {
		return super.getCreatedBy();
	}

	@JsonIgnore
	@Override
	public void setCreatedBy(Long createdBy) {
		super.setCreatedBy(createdBy);
	}

	@JsonIgnore
	@Override
	public Long getUpdatedBy() {
		return super.getUpdatedBy();
	}

	@JsonIgnore
	@Override
	public void setUpdatedBy(Long updatedBy) {
		super.setUpdatedBy(updatedBy);
	}

	@JsonIgnore
	@Override
	public Instant getCreatedAt() {
		return super.getCreatedAt();
	}

	@JsonIgnore
	@Override
	public void setCreatedAt(Instant createdAt) {
		super.setCreatedAt(createdAt);
	}

	@JsonIgnore
	@Override
	public Instant getUpdatedAt() {
		return super.getUpdatedAt();
	}

	@JsonIgnore
	@Override
	public void setUpdatedAt(Instant updatedAt) {
		super.setUpdatedAt(updatedAt);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
