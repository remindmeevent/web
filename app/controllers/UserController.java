package controllers;

import models.User;
import play.data.validation.Valid;
import play.mvc.Controller;

public class UserController extends Controller {

	public static void blank() {
		User user = new User();
		render(user);
	}

	public static void create(@Valid User user) {
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			blank();
		}
		user.hashPassword();
		user.save();
		// Mark user as connected
        session.put("username", user.email);
        Home.home();
	}
}
