package com.apress.cloud.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class TaskDemoApplicationTests {

	private final String MOVIES_COUNT_ZERO_SQL = "SELECT COUNT(*) FROM movies;";

	@Autowired
	private DataSource dataSource;

	@Test
	void testTask() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		int result = jdbcTemplate.queryForObject(
				MOVIES_COUNT_ZERO_SQL, Integer.class);
		assertThat(result,equalTo(2));
	}

}
