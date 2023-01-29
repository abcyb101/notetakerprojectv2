package com.abcyb101.notetakerprojectv2.repositories.accountRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abcyb101.notetakerprojectv2.models.accountModels.ERole;
import com.abcyb101.notetakerprojectv2.models.accountModels.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
