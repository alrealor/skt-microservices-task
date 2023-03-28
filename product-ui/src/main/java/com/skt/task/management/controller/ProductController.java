package com.skt.task.management.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.management.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String index() {
        return "index";
    }

    /**
     * method to go from index to list of products view
     *
     * @return view file name
     */
    @GetMapping("/goToListPage")
    public String listProducts(Model model) {
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
    public String goToCreateProduct() {
        return "create-product";
    }

    /**
     * action method to create a product, sending the form content to the post queue
     *
     * @return view file name
     */
    @PostMapping(value = "/createProduct")
    public String createProduct(Model model, ProductDTO product) {
        productService.publishPostRequestMsg(product);
        model.addAttribute("messageSent", true);
        return "create-product";
    }
}
