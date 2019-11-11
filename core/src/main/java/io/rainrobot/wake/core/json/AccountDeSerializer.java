package io.rainrobot.wake.core.json;

import io.rainrobot.wake.core.Account;

public class AccountDeSerializer extends IdabelDeSerializer<Account> {
	@Override
	protected Account getInstance() { return new Account(); }
}
