package controllers;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import models.Reminder;
import models.User;
import play.data.validation.Valid;
import play.libs.Mail;
import play.mvc.Controller;

public class Register extends Controller {
	
	//TODO : Refactor
	private static final String EMAIL_SUBJECT = "Remind me event registration";
	private static final String EMAIL_FROM = "remindmeevent@gmail.com";


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
		user.generateConfirmationToken();
		user.save();
		
		sendConfirmationEmail(user);
		flash.success("Check your mail box, an email as been send to confirm your registration");
        
        Home.home();
	}

	private static void sendConfirmationEmail(User user) {
		try {
			SimpleEmail email = new SimpleEmail();
			email.setFrom(EMAIL_FROM);
			email.addTo(user.email);
			email.setSubject(EMAIL_SUBJECT);
			email.setMsg(createRegistrationMessage(user));
			Mail.send(email);
		} catch (EmailException e) {
			// TODO ....
			throw new RuntimeException(e);
		}
	}

	private static String createRegistrationMessage(User user) {
		// TODO retrieve public URL
		return "http://localhost:9000/register/confirm/" + user.confirmationToken;
	}
}
