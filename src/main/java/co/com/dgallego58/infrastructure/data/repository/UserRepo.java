package co.com.dgallego58.infrastructure.data.repository;

import co.com.dgallego58.infrastructure.data.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepo extends JpaRepository<UserEntity, Integer> {


    @Query("""
           SELECT u from UserEntity u where u.username = :username
           """)
    Optional<UserEntity> findByUsername(@Param("username") String username);
}
