package it.epicode.be.energy.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByUserName(String userName);

	public boolean existsByEmail(String email);

	public boolean existsByUserName(String userName);
}
