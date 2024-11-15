package co.com.dgallego58.infrastructure.data.repository;

import co.com.dgallego58.infrastructure.data.repository.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ContactRepo extends JpaRepository<ContactEntity, Integer> {

    @Query("""
           select c from ContactEntity c left join fetch c.user u where u.username = :username
           """)
    List<ContactEntity> findAllByUserUsername(@Param("username") String username);

}
