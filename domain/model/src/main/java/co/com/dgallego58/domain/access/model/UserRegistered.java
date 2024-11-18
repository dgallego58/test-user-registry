package co.com.dgallego58.domain.access.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Builder(toBuilder = true)
@Jacksonized
@Getter
public class UserRegistered {

    private UUID id;
    @JsonUnwrapped
    private UserRegistry userRegistry;
    private String accessToken;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastLogin;
    private boolean active;

}
