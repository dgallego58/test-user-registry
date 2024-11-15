package co.com.dgallego58.infrastructure.data.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
}
