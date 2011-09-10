package models;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

import play.test.UnitTest;

public class UserTest {
	
	@Test
	public void shouldCreateEventBidirectionalLink() {
		User user = new User();
		Event event = new Event();
		user.addEvent(event);
		
		assertThat(event.user).isNotNull();
		assertThat(user.events).contains(event);
	}
	
	@Test
	public void shouldHashPassword() throws Exception {
		User user = new User();
		
		String password = "azerty";
		user.password = password;
		user.hashPassword();
		
		assertThat(user.hashedPassword).isNotNull();
		assertThat(user.hashedPassword).isNotEqualTo(password);
		
		assertThat(user.authenticate(password)).isTrue();
		
		
		
	}

}
