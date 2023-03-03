package com.kot.reactive;

import com.kot.reactive.controller.ProductController;
import com.kot.reactive.dto.ProductDTO;
import com.kot.reactive.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringReactiveMongoCrudApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService;

	@Test
	public void addProductTest(){
		Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("101","shirts",2,500));
		when(productService.saveProduct(productDTOMono)).thenReturn(productDTOMono);

		webTestClient.post()
					.uri("/products")
					.body(productDTOMono,ProductDTO.class)
					.exchange()
					.expectStatus().isOk();

	}

	@Test
	public void getAllProductsTest(){
		Flux<ProductDTO> productDTOFlux = Flux.just(new ProductDTO("102","t-shirts",5,200),
				new ProductDTO("103","chinos",2,1500));

		when(productService.getAllProducts()).thenReturn(productDTOFlux);

		Flux<ProductDTO> responseBody = webTestClient.get()
											.uri("/products")
											.exchange()
											.expectStatus().isOk()
											.returnResult(ProductDTO.class)
											.getResponseBody();

		StepVerifier.create(responseBody)
					.expectSubscription()
					.expectNext(new ProductDTO("102","t-shirts",5,200))
					.expectNext(new ProductDTO("103","chinos",2,1500))
					.verifyComplete();

	}

	@Test
	public void getProductTest(){
		Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("104","hoodie",1,1600));

		when(productService.getProduct(any())).thenReturn(productDTOMono);

		Flux<ProductDTO> responseBody = webTestClient.get()
				.uri("/products/104")
				.exchange()
				.expectStatus().isOk()
				.returnResult(ProductDTO.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(product->product.getName().equals("hoodie"))
				.verifyComplete();

	}

	@Test
	public void updateProduct(){
		Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("104","hoodie",4,1600));
		when(productService.updateProduct(productDTOMono,"104")).thenReturn(productDTOMono);

		webTestClient.put()
				.uri("/products/update/104")
				.body(productDTOMono,ProductDTO.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void deleteProduct(){
		when(productService.deleteById(any())).thenReturn(Mono.empty());

		webTestClient.delete()
					.uri("/products/delete/101")
					.exchange()
					.expectStatus().isOk();
	}

}
