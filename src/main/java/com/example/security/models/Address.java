package com.example.security.models;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "address")
public class Address extends UserAudit {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "street")
	private String street;

	@Column(name = "suite")
	private String suite;

	@Column(name = "city")
	private String city;

	@Column(name = "zipcode")
	private String zipcode;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "geo_id")
	private Geo geo;

	@OneToOne(mappedBy = "address")
	private User user;

	public Address(String street, String suite, String city, String zipcode, Geo geo) {
		this.street = street;
		this.suite = suite;
		this.city = city;
		this.zipcode = zipcode;
		this.geo = geo;
	}

	public Address() {
		super();
	}

	@JsonIgnore
	public Long getId() {
		return id;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSuite() {
		return suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
