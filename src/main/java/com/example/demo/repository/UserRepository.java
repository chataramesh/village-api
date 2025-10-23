package com.example.demo.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.response.UserCountResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;



public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("""
            SELECT new com.example.demo.dto.response.UserCountResponse(
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGE_ADMIN THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGE_ADMIN AND u.isActive = true THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGE_ADMIN AND u.isActive = false THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGER THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGER AND u.isActive = true THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGER AND u.isActive = false THEN 1 ELSE 0 END)
            )
            FROM User u
        """)
	UserCountResponse getUserCount();

    @Query("""
            SELECT new com.example.demo.dto.response.UserCountResponse(
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGE_ADMIN THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGE_ADMIN AND u.isActive = true THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGE_ADMIN AND u.isActive = false THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGER THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGER AND u.isActive = true THEN 1 ELSE 0 END),
                SUM(CASE WHEN u.role = com.example.demo.enums.Role.VILLAGER AND u.isActive = false THEN 1 ELSE 0 END)
            )
            FROM User u
            WHERE u.village.id = :villageId
        """)
    UserCountResponse getUserCountByVillageId(@Param("villageId") UUID villageId);

    List<User> findByRole(Role role);

    List<User> findByVillageIdAndRole(UUID villageId, Role role);

    Optional<User> findByIdAndIsActiveTrue(UUID id);

	List<User> findByVillageId(UUID villageId);
}
