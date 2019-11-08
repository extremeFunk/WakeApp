package io.rainrobot.wake.rest.request.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.rainrobot.wake.rest.dto.Account;

@Repository
public interface AccountDoa extends CrudRepository<Account, Integer> {

	Account findByUsername(String username);

	void deleteByUsername(String username);

	Account getByUsername(String usrNm);


}
