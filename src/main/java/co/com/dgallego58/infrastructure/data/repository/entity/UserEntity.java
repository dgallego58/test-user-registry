package co.com.dgallego58.infrastructure.data.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    private Instant createdAt;
    private Instant modifiedAt;
    private Instant lastLog;
    private boolean isActive;
    @Column(length = 1250)
    private String accessToken;


}
