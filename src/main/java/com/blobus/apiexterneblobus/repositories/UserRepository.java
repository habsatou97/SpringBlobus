package com.blobus.apiexterneblobus.repositories;

import com.blobus.apiexterneblobus.models.User;
import com.blobus.apiexterneblobus.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);
    User findUserByRoles(Role roles);

    Optional<User> findByUserId(String username);

}
