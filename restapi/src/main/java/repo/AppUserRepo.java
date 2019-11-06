package repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.rainrobot.wake.rest.dto.AppUser;

import java.util.Optional;

@Repository("userDao")
public interface AppUserRepo extends CrudRepository<AppUser, Integer> {
	
	AppUser findByUsername(String username);

	boolean existsByUsername(String username);

	Optional<AppUser> findByEmail(String email);

	Optional<AppUser> findByResetToken(Integer token);
}
