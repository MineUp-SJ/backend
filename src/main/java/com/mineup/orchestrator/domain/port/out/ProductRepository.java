package com.mineup.orchestrator.domain.port.out;


import com.mineup.orchestrator.domain.model.Category;
import com.mineup.orchestrator.domain.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository {
   // Flux<Product> findAllByName();
    Flux<Product> findAllByIsDeleted(Boolean isDeleted, Sort sort);
    Flux<Product> findAllByIsDeletedAndCategoryId(Boolean isDeleted, Sort sort,String categoryId);
    Mono<Product> findByNameAndIsDeletedAndCategoryId(String name, Boolean isDeleted, String categoryId);
  //  Mono<Product> findByName(String name);
 //   Mono<Product> save(Product product, String categoryId);
    Mono<Product> save(Product product);
    Mono<Product> findById(String id);
}
