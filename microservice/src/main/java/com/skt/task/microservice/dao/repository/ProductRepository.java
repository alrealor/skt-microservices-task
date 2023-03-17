package com.skt.task.microservice.dao.repository;

import com.skt.task.microservice.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository class to handle DB operations
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Method to retrieve all available products using a stored function by native query
     *
     * @return a collection of {@link Product} type
     */
    @Query(value = "SELECT * FROM get_products();", nativeQuery = true)
    List<Product> getProducts();

    /**
     * Method to insert a products into table using a stored function by native query
     *
     * @return a collection of {@link Product} type
     */
    @Query(value = "SELECT insert_product AS id FROM insert_product(:name, :price);", nativeQuery = true)
    Long addProduct(@Param("name") final String name,
                   @Param("price") final BigDecimal price);
}
