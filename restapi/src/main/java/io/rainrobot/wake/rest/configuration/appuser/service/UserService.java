package io.rainrobot.wake.rest.configuration.appuser.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rainrobot.wake.rest.configuration.appuser.AppUser;
import io.rainrobot.wake.rest.configuration.appuser.dao.AppUserRepo;

@Service("userService")
@Transactional
public class UserService{

	@Autowired
	private AppUserRepo repo;

	public void save(AppUser user){
		repo.save(user);
	}
	
	public AppUser findByUsername(String username) {
		return repo.findByUsername(username);
	}

	public Optional<AppUser> findByEmail(String email) { return repo.findByEmail(email); }

	public Optional<AppUser> findByPasswordResetToken(Integer token) {
		return repo.findByResetToken(token);
	}

	public List<AppUser> findAll() {
		List<AppUser> rtnList = new ArrayList<>();
		repo.findAll().forEach(rtnList::add);
		return rtnList;
	}
}
