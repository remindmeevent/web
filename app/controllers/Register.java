package controllers;

import models.User;
import play.data.validation.Valid;
import play.mvc.Controller;

public class Register extends Controller {

	public static void form() {
		User user = new User();
		render(user);
	}

	public static void perform(@Valid User user) {
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			form();
		}
		user.hashPassword();
		user.save();
		// Mark user as connected
        session.put("username", user.email);
        Home.home();
	}
}
