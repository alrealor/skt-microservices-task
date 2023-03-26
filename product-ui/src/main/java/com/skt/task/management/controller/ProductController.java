package com.skt.task.management.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.management.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller class to manage the binding between JSPs and server
 */
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * method with get operation for redirecting from root / to index view
     *
     * @return view file name
     */
    @GetMapping("/")
    public String index() throws Exception {
        return "index";
    }

    /**
     * method to go from index to list of products view
     *
     * @return view file name
     */
    @GetMapping("/goToListPage")
    public String listProducts(Model model) throws Exception {
        List<ProductDTO> products = productService.sendGetMsg();
        model.addAttribute("products", products);
        return "list-products";
    }

    /**
     * method to go from index to create a product view
     *
     * @return view file name
     */
    @GetMapping("/goToCreatePage")
    public String goToCreateProduct() throws Exception {
        return "create-product";
    }

    /**
     * action method to create a product, sending the form content to the post queue
     *
     * @return view file name
     */
    @PostMapping(value = "/createProduct", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) throws Exception {
        ProductDTO result = productService.publishPostRequestMsg(product);
        return ResponseEntity.ok().body(result);
    }
}
