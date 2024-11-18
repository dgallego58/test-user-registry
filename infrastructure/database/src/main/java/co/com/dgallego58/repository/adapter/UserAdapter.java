package co.com.dgallego58.repository.adapter;


import co.com.dgallego58.domain.access.model.UserRegistered;
import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.repository.UserRepo;
import co.com.dgallego58.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Repository
public class UserAdapter implements UserRepository {

    private final UserRepo userRepo;

    public UserAdapter(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserRegistered getUser(String username) {

        return userRepo.findByUsername(username)
                       .map(ue -> UserRegistered.builder()
                                                .id(ue.getId())
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
                       .orElse(null);
    }


    @Override
    public UserRegistered save(UserRegistered user) {


        UnaryOperator<UserEntity> merge = userEntity -> {
            //userEntity.setId(user.getId());
            userEntity.setEmail(user.getUserRegistry().email());
            userEntity.setUsername(user.getUserRegistry().name());
            userEntity.setPassword(user.getUserRegistry().password());
            userEntity.setAccessToken(user.getAccessToken());
            userEntity.setCreatedAt(user.getCreatedAt());
            userEntity.setLastLog(user.getLastLogin());
            userEntity.setModifiedAt(user.getUpdatedAt());
            userEntity.setActive(user.isActive());
            return userRepo.save(userEntity);
        };

        Supplier<UserEntity> persist = () -> merge.apply(new UserEntity());
        var saved = userRepo.findByEmail(user.getUserRegistry().email())
                .map(merge)
                .orElseGet(persist);

        return user.toBuilder()
            .accessToken(saved.getAccessToken())
            .createdAt(saved.getCreatedAt())
            .build();

    }
}
