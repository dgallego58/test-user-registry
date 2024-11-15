package co.com.dgallego58.infrastructure.data.repository.adapter;

import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.infrastructure.data.repository.UserRepo;
import org.springframework.stereotype.Repository;

@Repository
public class UserAdapter implements UserRepository {

    private final UserRepo userRepo;

    public UserAdapter(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


}
