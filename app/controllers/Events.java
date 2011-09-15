package controllers;

import models.Event;
import models.Reminder;
import models.User;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.With;

@With(Secure.class)
public class Events extends Controller{

	public static void show(Long id) {
		Event event = Event.findById(id);
		if (eventBelongsToConnectedUser(event)) {
			Reminder reminder = event.reminders.get(0);
			User user = connectedUser();
			render("Home/connected.html", user, event, reminder);
		} else {
			error(Http.StatusCode.FORBIDDEN, "You do not own this event");
		}
	}

	private static boolean eventBelongsToConnectedUser(Event event) {
		return event.user.email.equals(Security.connected());
	}
	
	public static void save(@Valid Event event, @Valid Reminder reminder) {
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Home.home();
		}
		if (event.id == null) {
			User user = connectedUser();
			user.addEvent(event);
			user.save();
		}
		
		event.reminders.clear();
		
		event.addReminder(reminder);
		reminder.computeNextFireDate();
		
		event.save();
		
		flash.success("Event successfully saved, next fire date : " + reminder.nextFireDate);
		Home.home();
		
	}

	private static User connectedUser() {
		return User.findByEmail(Security.connected());
	}
	
	public static void delete(Long id) {
		Event event = Event.findById(id);
		if (eventBelongsToConnectedUser(event)) {
			event.delete();
			flash.success("Event successfully deleted");
			Home.home();
		} else {
			error(Http.StatusCode.FORBIDDEN, "You do not own this event");
		}
	}
}
