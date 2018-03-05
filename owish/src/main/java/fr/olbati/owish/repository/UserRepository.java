package fr.olbati.owish.repository;

import fr.olbati.owish.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User findByUsername( String username );

    @Transactional
    void deleteByUsername(String username);

}
