package com.wexort.hatee.composite.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import com.wexort.hatee.composite.status.services.StatusCompositeIntegration;
import com.wexort.hatee.api.core.status.Status;
import com.wexort.hatee.api.core.comment.Comment;
import com.wexort.hatee.api.composite.status.*;
import com.wexort.hatee.util.exceptions.InvalidInputException;
import com.wexort.hatee.util.exceptions.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=RANDOM_PORT)
public class StatusCompositeServiceApplicationTests {

	private static final int STATUS_ID_OK = 1;
	private static final int STATUS_ID_NOT_FOUND = 2;
	private static final int STATUS_ID_INVALID = 3;

	@Autowired
	private WebTestClient client;

	@MockBean
	private StatusCompositeIntegration compositeIntegration;

	@Before
	public void setUp() {
		// setup the mock for core APIs
		when(compositeIntegration.getStatus(STATUS_ID_OK)).
				thenReturn(new Status(STATUS_ID_OK, 1, 0, "I am status content"));
		when(compositeIntegration.getComments(STATUS_ID_OK)).
				thenReturn(singletonList(new Comment(1, STATUS_ID_OK, 1, "I am comment content")));

		when(compositeIntegration.getStatus(STATUS_ID_NOT_FOUND)).thenThrow(new NotFoundException("NOT FOUND: " + STATUS_ID_NOT_FOUND));

		when(compositeIntegration.getStatus(STATUS_ID_INVALID)).thenThrow(new InvalidInputException("INVALID: " + STATUS_ID_INVALID));
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void getStatusById() {

		client.get()
				.uri("/status-composite/" + STATUS_ID_OK)
				.accept(APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.statusId").isEqualTo(STATUS_ID_OK)
				.jsonPath("$.comments.length()").isEqualTo(1);
	}

	@Test
	public void getStatusNotFound() {

		client.get()
				.uri("/status-composite/" + STATUS_ID_NOT_FOUND)
				.accept(APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/status-composite/" + STATUS_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND: " + STATUS_ID_NOT_FOUND);
	}

	@Test
	public void getStatusInvalidInput() {

		client.get()
				.uri("/status-composite/" + STATUS_ID_INVALID)
				.accept(APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/status-composite/" + STATUS_ID_INVALID)
				.jsonPath("$.message").isEqualTo("INVALID: " + STATUS_ID_INVALID);
	}
}
