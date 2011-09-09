package models;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;

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

}
