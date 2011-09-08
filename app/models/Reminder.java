package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Reminder extends Model {
	Integer numberOfDaysBeforeEvent;
	
}
