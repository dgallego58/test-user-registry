package co.com.dgallego58.domain.access.model;

import java.time.Instant;

public record UserUpgrade(UserRegistry user, String token, Instant accessDate) {
}
