package com.example.security.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties(value = { "createdBY", "updatedBy" }, allowGetters = true)
public abstract class UserAudit extends DateAudit {
	private static final long serialVersionUID = 1L;

	@CreatedBy
	@Column(updatable = false)
	private Long createdBy;

	@LastModifiedBy
	private Long updatedBy;

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public UserAudit(Long createdBy, Long updatedBy) {
		super();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public UserAudit() {
		super();
	}

}
