package models;

import org.junit.Test;

import play.test.UnitTest;

public class UserTest extends UnitTest {
	
	@Test
	public void shouldCreateEventBidirectionalLink() {
		User user = new User();
		Event event = new Event();
		user.addEvent(event);
		
		assertNotNull(event.user);
		assertTrue(user.events.contains(event));
	}

}
