package io.rainrobot.wake.rest.request.singup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import io.rainrobot.wake.rest.configuration.appuser.dao.AppUserRepo;
import io.rainrobot.wake.core.SignUpForm;
import io.rainrobot.wake.core.SingupError;

@Service
public class SignUpFormValidator implements Validator {

		@Autowired
		AppUserRepo userDoa;

	
	    @Override
	    public boolean supports(Class<?> cls) {
	        return SignUpForm.class.equals(cls);
	    }

	    @Override
	    public void validate(Object target, Errors errors) {
	        
	    	valedateUsenameIsNotEmpty(errors);
	    	
	    	valedatePasswordIsNotEmpty(errors);

	        
	        SignUpForm form = (SignUpForm) target;

	        valedatePasswordMatch(errors, form);            

	        valedateUsernameIsNotUsed(errors, form);


	    }

		private void valedateUsernameIsNotUsed(Errors errors, SignUpForm form) {
			
			if(checkIfUsernameIsUsed(form.getUsername())) {
	        	
				errors.rejectValue("username", SingupError
												.USERNAME_EXIST
												.name());	
	        }
		}

		private void valedatePasswordMatch(Errors errors, SignUpForm form) {
			
			if (!checkIfPasswordConfirmationMatch(form)) { 
	        	
				errors.rejectValue("passwordConfirm", SingupError
														.PASSWORD_MUST_MATCH
														.name());
	        }
		}

		private void valedatePasswordIsNotEmpty(Errors errors) {
			
			ValidationUtils.rejectIfEmptyOrWhitespace
	        					(errors, "password", SingupError
														.PASSWORD_REQUIERD
														.name());
		}

		private void valedateUsenameIsNotEmpty(Errors errors) {
			
			ValidationUtils.rejectIfEmptyOrWhitespace 
									(errors, "username", 
									SingupError.USERNAME_REQUIERD.name());
		}

		private boolean checkIfPasswordConfirmationMatch(SignUpForm form) {
			return form.getPassword().equals(form.getPasswordConfirm());
		}

		private boolean checkIfUsernameIsUsed(String username) {
			return userDoa.existsByUsername(username);

		}
	}

