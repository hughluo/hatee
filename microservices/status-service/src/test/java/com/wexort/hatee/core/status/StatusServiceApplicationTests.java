package com.wexort.hatee.core.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=RANDOM_PORT)
public class StatusServiceApplicationTests {

	@Autowired
	private WebTestClient client;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getStatusByStatusId() {

		int statusId = 1;

		client.get()
				.uri("/status/" + statusId)
				.accept(APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.statusId").isEqualTo(statusId);
	}

	@Test
	public void getStatusByInvalidStatusId() {

		int statusIdInvalid = -1;

		client.get()
				.uri("/status/" + statusIdInvalid)
				.accept(APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/status/" + statusIdInvalid)
				.jsonPath("$.message").isEqualTo("Invalid statusId: " + statusIdInvalid);
	}

}
