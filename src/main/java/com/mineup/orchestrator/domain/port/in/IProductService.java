package com.mineup.orchestrator.domain.port.in;

import com.mineup.orchestrator.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    public Flux<Product> findAll();
    public Flux<Product> findAll(String categoryId);
    public Mono<Product> createProduct(Product product, String categoryId);

}
