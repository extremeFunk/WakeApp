package io.rainrobot.wake.rest.configuration.appuser.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rainrobot.wake.rest.configuration.appuser.AppUser;



@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
									throws UsernameNotFoundException {
		
		AppUser user = userService.findByUsername(username);
		
		if (user == null) {
			
			throw new UsernameNotFoundException("Username not found"); 
		}
		
		return new User(user.getUsername(), user.getPassword(), 
			user.getState().equals("Active"), true, true, true, 
			user.getAuthority());
	}
	
	//test 29.6.18
	public AppUser loadAppUserByUsername(String username) {
		return userService.findByUsername(username);
	}

	
}
