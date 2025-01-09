package ru.job4j.springbootstart;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
class SpringbootstartApplicationTests {

	@Test
	void contextLoads() {
	}

}
