package controllers;

import models.User;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import util.BaseUnitTest;

public class SecurityTest extends BaseUnitTest {

	@Test
	public void shouldNotAuthenticateNotConfirmedUser() {
		User user = new User();
		user.email = "me@nobody.com";
		user.password = "azerty";
		user.hashPassword();
		user.save();
		
		assertThat(Security.authenticate(user.email, user.password)).isFalse();
	}
}
