package co.com.dgallego58.infrastructure.data.repository.adapter;


import co.com.dgallego58.domain.access.model.UserRegistered;
import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.infrastructure.data.repository.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserAdapter implements UserRepository {

    private final UserRepo userRepo;

    public UserAdapter(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserRegistered getUser(String username) {

        return userRepo.findByUsername(username)
                       .map(ue -> UserRegistered.builder().id(ue.getId())
                                                .userRegistry(
                                                        new UserRegistry(ue.getUsername(), ue.getEmail(),
                                                                ue.getPassword(),
                                                                null))
                                                .createdAt(ue.getCreatedAt())
                                                .updatedAt(ue.getModifiedAt())
                                                .accessToken(ue.getAccessToken())
                                                .active(ue.isActive())
                                                .build()
                       )
                       .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserRegistered save(UserRegistered user) {
        var userRegistered = userRepo.findById(user.getId());
        return userRegistered.map(userEntity -> {
                                 userEntity.setEmail(userEntity.getEmail());
                                 userEntity.setUsername(userEntity.getUsername());
                                 userEntity.setPassword(userEntity.getPassword());
                                 userEntity.setAccessToken(user.getAccessToken());
                                 userEntity.setLastLog(user.getLastLogin());
                                 userEntity.setModifiedAt(user.getUpdatedAt());
                                 userEntity.setActive(user.isActive());
                                 return userRepo.save(userEntity);

                             }).map(userEntity ->
                                     user.toBuilder().accessToken(userEntity.getAccessToken())
                                         .createdAt(userEntity.getCreatedAt())
                                         .build())
                             .orElseThrow(() -> new UsernameNotFoundException("User not found"));


    }
}
