package models;

import play.data.validation.Check;

public class UniqueEmailCheck extends Check {

	@Override
	public boolean isSatisfied(Object validatedObject, Object email) {
		 if(User.isEmailAlreadyUsed((String) email)) {
			 setMessage("Email already used");
			 return false;
		 } else {
			 return true;
		 }
	}

}
