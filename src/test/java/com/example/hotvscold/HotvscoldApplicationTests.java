package com.example.hotvscold;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootTest
class HotvscoldApplicationTests {

	@Test
	void coldPublishTest() throws InterruptedException {
		Flux<String> flux = Flux.just("A", "B", "C", "D")
				.delayElements(Duration.ofSeconds(1));
		flux.subscribe(data -> System.out.println("Subscriber 1"+data));

		Thread.sleep(2000);

		flux.subscribe(data -> System.out.println("Subscriber 2"+data));

		Thread.sleep(2000);

	}

	/*Result :
	Subscriber 1A
	Subscriber 1B
	Subscriber 2A
	Subscriber 1C
	Subscriber 2B
	Subscriber 1D*/

	@Test
	void hotPublishTest() throws InterruptedException {
		Flux<String> flux = Flux.just("A", "B", "C", "D")
				.delayElements(Duration.ofSeconds(1));

		ConnectableFlux<String> connectableflux = flux.publish();
		connectableflux.connect();

		connectableflux.subscribe(data -> System.out.println("Subscriber 1"+data));

		Thread.sleep(2000);

		connectableflux.subscribe(data -> System.out.println("Subscriber 2"+data));

		Thread.sleep(2000);

	}

	/*Result:
	Subscriber 1A
	Subscriber 1B
	Subscriber 2B
	Subscriber 1C
	Subscriber 2C
	Subscriber 1D
	Subscriber 2D
	 */


}
