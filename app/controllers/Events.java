package controllers;

import models.Event;
import play.db.jpa.JPABase;
import play.mvc.Controller;

public class Events extends Controller{

	public static void show(Long id) {
		Event event = Event.findById(id);
		render(event);
	}
}
