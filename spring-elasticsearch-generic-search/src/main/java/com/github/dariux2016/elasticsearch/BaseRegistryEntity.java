package com.github.dariux2016.elasticsearch;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import it.eng.lw.common.enumeration.EnInsertUpdateType;
import it.eng.lw.common.model.LwUser;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for the registry entities
 */
@MappedSuperclass
@Getter
@Setter
public class BaseRegistryEntity implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6097798948633234694L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	/**
     *  EnInsertUpdateType inserttype
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "inserttype")
    private EnInsertUpdateType insertType;
    
    /**
     *  EnInsertUpdateType updatetype
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "updatetype")
    private EnInsertUpdateType updateType;
    
    /**
     * Date startDate
     */
    @Column(name = "startdate", columnDefinition = "DATE")
    private LocalDateTime startDate;

    /**
     * Date endDate
     */
    @Column(name = "enddate", columnDefinition = "DATE")
    private LocalDateTime endDate;
    
    /**
     * Date definitionDate
     */
    @Column(name = "definitiondate", columnDefinition = "DATE")
    private LocalDateTime definitionDate;
    
    /**
     * Date traceDate
     */
    @Column(name = "tracedate", columnDefinition = "TIMESTAMP")
    private LocalDateTime traceDate;

	/**
     * String updateUser
     */
    @Column(name = "userid")
    private String userId;
    
    /**
	 * Method called before saving new entity
	 */
	@PrePersist
	private void baseEntityPrePersist() {
		// TODO - fix mandatory fields
		LwUser lwUser=LwUser.mock();
		this.setUserId(lwUser.getUsername());
		this.setTraceDate(LocalDateTime.now());
	}
	
	/**
	 *  Method called before updating entity
	 */
	@PreUpdate
	private void baseEntityPreUpdate() {
		this.setTraceDate(LocalDateTime.now());
	}
}