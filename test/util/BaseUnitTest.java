package util;


import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import play.db.jpa.JPA;
import play.test.PlayJUnitRunner;
import play.test.UnitTest;
import static org.fest.assertions.Assertions.assertThat;

@RunWith(PlayJUnitRunner.class)
public class BaseUnitTest {
	
	@Rule
    public PlayJUnitRunner.StartPlay startPlayBeforeTests = PlayJUnitRunner.StartPlay.rule();

	protected void clearJPASession() {
		JPA.em().flush();
		JPA.em().clear();
	}

}
