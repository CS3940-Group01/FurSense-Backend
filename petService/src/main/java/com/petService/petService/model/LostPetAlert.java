package com.petService.petService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lost_pet_alerts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LostPetAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer petId;

    @Column(nullable = false)
    private Integer ownerId;

    @Column(nullable = false)
    private String lastSeenLocation;

    @Column(length = 1000)
    private String description;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "reward_amount")
    private Double rewardAmount;

    @Column(name = "alert_created")
    private LocalDateTime alertCreated;

    @Column(name = "alert_updated")
    private LocalDateTime alertUpdated;

    @Enumerated(EnumType.STRING)
    private AlertStatus status;

    @Column(name = "found_location")
    private String foundLocation;

    @Column(name = "found_by")
    private String foundBy;

    @Column(name = "found_date")
    private LocalDateTime foundDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getLastSeenLocation() {
        return lastSeenLocation;
    }

    public void setLastSeenLocation(String lastSeenLocation) {
        this.lastSeenLocation = lastSeenLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Double getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Double rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public LocalDateTime getAlertCreated() {
        return alertCreated;
    }

    public void setAlertCreated(LocalDateTime alertCreated) {
        this.alertCreated = alertCreated;
    }

    public LocalDateTime getAlertUpdated() {
        return alertUpdated;
    }

    public void setAlertUpdated(LocalDateTime alertUpdated) {
        this.alertUpdated = alertUpdated;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public String getFoundLocation() {
        return foundLocation;
    }

    public void setFoundLocation(String foundLocation) {
        this.foundLocation = foundLocation;
    }

    public String getFoundBy() {
        return foundBy;
    }

    public void setFoundBy(String foundBy) {
        this.foundBy = foundBy;
    }

    public LocalDateTime getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(LocalDateTime foundDate) {
        this.foundDate = foundDate;
    }
}
