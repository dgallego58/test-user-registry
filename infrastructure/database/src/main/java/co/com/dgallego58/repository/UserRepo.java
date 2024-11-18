package co.com.dgallego58.repository;

import co.com.dgallego58.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface UserRepo extends JpaRepository<UserEntity, UUID> {


    @Query("""
           SELECT u from UserEntity u where u.username = :username
           """)
    Optional<UserEntity> findByUsername(@Param("username") String username);


    @Modifying
    @Query("""
           UPDATE UserEntity u SET u.accessToken = :#{#user.accessToken}
           """)
    int update(@Param("user") UserEntity user);

    @Query("""
           SELECT u from UserEntity u where u.email = :email
           """)
    Optional<UserEntity> findByEmail(@Param("email") String email);
}
