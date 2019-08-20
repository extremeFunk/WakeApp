package io.rainrobot.wake.rest.request.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.rainrobot.wake.core.Account;

@Repository
public interface AccountDoa extends JpaRepository<Account, Integer> {

	Account findByUsername(String username);

	void deleteByUsername(String username);

	Account getByUsername(String usrNm);


}
