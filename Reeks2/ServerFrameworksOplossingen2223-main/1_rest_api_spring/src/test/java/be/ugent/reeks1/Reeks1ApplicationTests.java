package be.ugent.reeks1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebTestClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class Reeks1ApplicationTests {





	@Test
	void AddPosts(@Autowired WebTestClient client)
	{

		BlogPost bp = new BlogPost();
		bp.setTitle("Arne");
		bp.setContent("Arne is best wel slim ja!");
		client.post().uri("/posts").body(BodyInserters.fromValue(bp)).exchange().expectStatus().isCreated();
	}

	@Test
	void GetPosts(@Autowired WebTestClient client)
	{
		client.get().uri("/posts").exchange().expectStatus().isOk();
	}

	@Test
	void GetCorrectID(@Autowired WebTestClient client)
	{
		//client.get().uri("/posts").exchange().expectStatus().isOk();
		client.get().uri("/posts/1").exchange().expectStatus().isOk();
	}

	@Test
	void GetWrongID(@Autowired WebTestClient client)
	{

		client.get().uri("/posts/10").exchange().expectStatus().isNotFound();
	}


}
