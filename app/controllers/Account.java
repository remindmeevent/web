package controllers;

import models.User;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Account extends Controller {
	
	public static void changePasswordForm() {
		render();
	}
	
	public static void performChangePassword(
			@Required String oldPassword, 
			@Required @MinSize(6) @Equals("newPasswordConfirm") String newPassword, 
			@Required @MinSize(6) @Equals("newPassword") String newPasswordConfirm) {

		User user = User.findByEmail(Security.connected());
		
		if (oldPassword != null && !user.authenticate(oldPassword)) {
			validation.addError("oldPassword", "invalid password");
		}
		
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			changePasswordForm();
		}
		
		user.password = newPassword;
		user.hashPassword();
		user.save();
		
		flash.success("Password successfully changed");
		Home.home();
		
	}
	
	public static void changeEmailForm() {
		render();
	}
	
	public static void performChangeEmail(@Required @Email String email) {

		User user = User.findByEmail(Security.connected());
		
		if (email != null) {
			User userWithSameEmail = User.findByEmail(email);
			if (userWithSameEmail != null && ! userWithSameEmail.id.equals(user.id)) {
				validation.addError("email", "Email already used");
			}
		}
		
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			changeEmailForm();
		}
		
		user.email = email;
		user.save();
		
		flash.success("Email successfully changed");
		Home.home();
		
	}
	

}
