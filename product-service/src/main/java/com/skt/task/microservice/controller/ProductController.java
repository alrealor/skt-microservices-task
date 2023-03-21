package com.skt.task.microservice.controller;

import com.skt.task.common.domain.BaseResponse;
import com.skt.task.common.domain.ProductDTO;
import com.skt.task.microservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class in charge of handle products request
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    /**
     * Constructor used for dependency injection
     *
     * @param productService - parameter of {@link ProductService} type
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all available products operation
     *
     * @return {@link ResponseEntity} response
     */
    @GetMapping()
    public ResponseEntity<BaseResponse<ProductDTO>> getProducts() throws Exception {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setPayload(productService.getProducts());
        return ResponseEntity.ok().body(baseResponse);
    }

    /**
     * Add new product operation
     *
     * @return {@link ResponseEntity} response
     */
    @PostMapping()
    public ResponseEntity<BaseResponse<ProductDTO>> addProduct(@RequestBody ProductDTO request) throws Exception {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setPayload(productService.addProduct(request));
        return ResponseEntity.ok().body(baseResponse);
    }
}
