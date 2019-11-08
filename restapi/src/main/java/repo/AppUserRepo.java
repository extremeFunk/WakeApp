package repo;

import io.rainrobot.wake.rest.dto.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends MongoRepository<AppUser, Integer> {
	AppUser findByUsername(String username);
	boolean existsByUsername(String username);
	Optional<AppUser> findByEmail(String email);
	Optional<AppUser> findByResetToken(Integer token);
}
