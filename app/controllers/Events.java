package controllers;

import models.Event;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.With;

@With(Secure.class)
public class Events extends Controller{

	public static void show(Long id) {
		Event event = Event.findById(id);
		if (eventBelongsToConnectedUser(event)) {
			render(event);
		} else {
			error(Http.StatusCode.FORBIDDEN, "You do not own this event");
		}
	}

	private static boolean eventBelongsToConnectedUser(Event event) {
		return event.user.email.equals(Security.connected());
	}
}
