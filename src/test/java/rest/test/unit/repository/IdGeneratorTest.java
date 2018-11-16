package rest.test.unit.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import rest.repository.IdGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class IdGeneratorTest {
	
	@Autowired
	private IdGenerator idgen1;
	
	@Autowired
	private IdGenerator idgen2;
	
	@Test
	public void testIndependentGenerator() throws Exception{
		Assert.assertEquals(1, idgen1.getNextId());
		Assert.assertEquals(1, idgen2.getNextId());
		Assert.assertEquals(2, idgen1.getNextId());
		Assert.assertEquals(2,  idgen2.getNextId());
	}
}
