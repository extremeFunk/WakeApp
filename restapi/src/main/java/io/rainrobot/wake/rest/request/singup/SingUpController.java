package io.rainrobot.wake.rest.request.singup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.rainrobot.wake.core.Path;
import io.rainrobot.wake.rest.configuration.appuser.service.UserService;
import io.rainrobot.wake.rest.request.account.AccountService;
import io.rainrobot.wake.core.SignUpForm;
import io.rainrobot.wake.core.SingupError;


@RestController()
public class SingUpController {
   
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SignUpFormValidator validator;

	@Autowired
	private UserService users;
	
	@Autowired
	private AccountService accounts;

	
	@PostMapping(Path.SINGUP)
    public List<SingupError> signup(@RequestBody SignUpForm form, BindingResult result) {
    	
        validator.validate(form, result);
        
        if (!result.hasErrors()) {
        	createAccount(form);
        }
        
        return createErrEnumList(result);
    }

	private List<SingupError> createErrEnumList(BindingResult result) {
		
		List<SingupError> errList = new ArrayList<>();
        
		result.getAllErrors().forEach((err) -> {
        	
        	SingupError singupErr = SingupError.valueOf(err.getCode());
			errList.add(singupErr);
        
		});
        
		return errList;
	}

	private void createAccount(SignUpForm form) {
		users.save(AppUserBuilder.build(form, passwordEncoder));
    	accounts.save(AccountBuilder.build(form.getUsername()));
	}
}
