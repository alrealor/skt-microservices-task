package com.skt.task.microservice.service;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.common.exception.BusinessException;
import com.skt.task.microservice.dao.entity.Product;
import com.skt.task.microservice.dao.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.skt.task.microservice.mapper.ProductMapper.productMapper;

/**
 * Service class in charge of handle business logic
 */
@Service
public class ProductService {

    /**
     * Product repository of type {@link ProductRepository}
     */
    private final ProductRepository productRepository;

    /**
     * Constructor used for dependency injection
     *
     * @param productRepository dependency of {@link ProductRepository} type
     */
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * transactional service method to get all the available products from DB
     *
     * @return Collection of type {@link List<Product>} type
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ProductDTO> getProducts() throws Exception {
        return this.productRepository.getProducts()
                .stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    /**
     * transactional service method to add a new product into DB
     *
     * @return Collection of type {@link List<Product>} type
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ProductDTO addProduct(final ProductDTO request) throws Exception {
         return Optional.of(request)
                .map(req -> this.productRepository.addProduct(req.getName(), req.getPrice()))
                .map(id -> new ProductDTO(id, request.getName(), request.getPrice()))
                .orElseThrow(() -> new BusinessException("BUS001 - Product cannot be added"));
    }
}
