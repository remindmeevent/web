package models;

import org.junit.Test;

import controllers.Security;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

import play.libs.Crypto;
import play.test.UnitTest;
import util.BaseUnitTest;

public class UserTest extends BaseUnitTest {

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

	@Test
	public void shouldUseASaltForPasswordHash() throws Exception {
		User user = new User();

		String password = "azerty";
		user.password = password;
		user.hashPassword();

		assertThat(user.hashedPassword).isNotEqualTo(Crypto.passwordHash(password));
	}

	@Test
	public void shouldGetNotConfirmUser() {
		User user = new User();
		assertThat(user.state).isEqualTo(User.State.NOT_CONFIRMED);
	}
}
