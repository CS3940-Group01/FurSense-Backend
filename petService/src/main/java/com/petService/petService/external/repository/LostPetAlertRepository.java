package com.petService.petService.external.repository;

import com.petService.petService.model.AlertStatus;
import com.petService.petService.model.LostPetAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LostPetAlertRepository extends JpaRepository<LostPetAlert, Integer> {
    
    // Find all active alerts
    List<LostPetAlert> findByStatus(AlertStatus status);
    
    // Find alerts by owner
    List<LostPetAlert> findByOwnerId(Integer ownerId);
    
    // Find alerts by pet ID
    List<LostPetAlert> findByPetId(Integer petId);
    
    // Find active alert for a specific pet
    Optional<LostPetAlert> findByPetIdAndStatus(Integer petId, AlertStatus status);
    
    // Find alerts created within a date range
    List<LostPetAlert> findByAlertCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find alerts by location (case insensitive search)
    @Query("SELECT l FROM LostPetAlert l WHERE LOWER(l.lastSeenLocation) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<LostPetAlert> findByLocationContainingIgnoreCase(@Param("location") String location);
    
    // Find recent active alerts (within last 30 days)
    @Query("SELECT l FROM LostPetAlert l WHERE l.status = :status AND l.alertCreated >= :thirtyDaysAgo ORDER BY l.alertCreated DESC")
    List<LostPetAlert> findRecentActiveAlerts(@Param("status") AlertStatus status, @Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo);
    
    // Count active alerts for a user
    long countByOwnerIdAndStatus(Integer ownerId, AlertStatus status);
}
