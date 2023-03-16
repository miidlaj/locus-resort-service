package com.midlaj.resort.entity;

import com.midlaj.resort.util.Constants;
import jakarta.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "resort")
public class Resort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 1024)
    private String description;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "default_image")
    private String defaultImage;

    @Column(name = "admin_approval", nullable = false)
    private Boolean isAdminApproved;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResortCreateStatus resortCreateStatus;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(name = "admin_ban", nullable = false)
    private Boolean isBanned;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ResortCategory category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "resort_facilities",
            joinColumns = @JoinColumn(name = "resort_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Set<ResortFacility> facilities = new HashSet<>();

    @OneToMany(mappedBy = "resort", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResortImages> images = new HashSet<>();

    @OneToOne(mappedBy = "resort", cascade = CascadeType.ALL, orphanRemoval = true)
    private ResortLocationDetails locationDetails;

    @OneToOne(mappedBy = "resort", cascade = CascadeType.ALL, orphanRemoval = true)
    private ResortAddress resortAddress;

	@Column(nullable = false, name = "created_time")
	private Date createdTime;

	@Column(nullable = false, name = "updated_time")
	private Date updateTime;


	public Resort(Long id, String name, String description, Long userId, String defaultImage, Boolean isAdminApproved, ResortCreateStatus resortCreateStatus, Boolean enabled, Boolean isBanned, ResortCategory category, Set<ResortFacility> facilities, Set<ResortImages> images, ResortLocationDetails locationDetails, ResortAddress resortAddress) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.defaultImage = defaultImage;
		this.isAdminApproved = isAdminApproved;
		this.resortCreateStatus = resortCreateStatus;
		this.enabled = enabled;
		this.isBanned = isBanned;
		this.category = category;
		this.facilities = facilities;
		this.images = images;
		this.locationDetails = locationDetails;
		this.resortAddress = resortAddress;
	}

	public Resort() {
	}

	public void addExtraImage(String originalImage, String resizedImage){
        this.images.add(new ResortImages(originalImage, resizedImage, this));
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public Boolean getAdminApproved() {
		return isAdminApproved;
	}

	public void setAdminApproved(Boolean adminApproved) {
		isAdminApproved = adminApproved;
	}

	public ResortCreateStatus getResortCreateStatus() {
		return resortCreateStatus;
	}

	public void setResortCreateStatus(ResortCreateStatus resortCreateStatus) {
		this.resortCreateStatus = resortCreateStatus;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getBanned() {
		return isBanned;
	}

	public void setBanned(Boolean banned) {
		isBanned = banned;
	}

	public ResortCategory getCategory() {
		return category;
	}

	public void setCategory(ResortCategory category) {
		this.category = category;
	}

	public Set<ResortFacility> getFacilities() {
		return facilities;
	}

	public void setFacilities(Set<ResortFacility> facilities) {
		this.facilities = facilities;
	}

	public Set<ResortImages> getImages() {
		return images;
	}

	public void setImages(Set<ResortImages> images) {
		this.images = images;
	}

	public ResortLocationDetails getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(ResortLocationDetails locationDetails) {
		this.locationDetails = locationDetails;
	}

	public ResortAddress getResortAddress() {
		return resortAddress;
	}

	public void setResortAddress(ResortAddress resortAddress) {
		this.resortAddress = resortAddress;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Resort{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", userId=" + userId +
				", defaultImage='" + defaultImage + '\'' +
				", isAdminApproved=" + isAdminApproved +
				", resortCreateStatus=" + resortCreateStatus +
				", enabled=" + enabled +
				", isBanned=" + isBanned +
				", category=" + category +
				", facilities=" + facilities +
				", images=" + images +
				", locationDetails=" + locationDetails +
				", resortAddress=" + resortAddress +
				'}';
	}

	@Transient
	public String getDefaultImageUrl(){
		return Constants.S3_BASE_URI + "/resortImages/default/" + this.id + "/" + this.defaultImage;
	}

	@Transient
	public String getCreatedDateForForm() {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormatter.format(this.createdTime);
	}


}
