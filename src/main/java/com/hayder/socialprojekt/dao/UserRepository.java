package com.hayder.socialprojekt.dao;

import com.hayder.socialprojekt.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    public ApplicationUser findUserByEmail(String email);
}
