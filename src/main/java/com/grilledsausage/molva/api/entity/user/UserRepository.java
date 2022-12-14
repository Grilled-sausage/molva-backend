package com.grilledsausage.molva.api.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findById(Long id);

    public void deleteUserByEmail(String email);

}
