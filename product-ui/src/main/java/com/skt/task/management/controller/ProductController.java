package com.skt.task.management.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.management.service.ProductService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Controller
public class ProductController {

    public static final String INDEX_VIEW = "index";
    public static final String LIST_PRODUCTS_VIEW = "list-products";
    public static final String CREATE_PRODUCT_VIEW = "create-product";

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
        return INDEX_VIEW;
    }

    /**
     * method to go from index to list of products view
     *
     * @return view file name
     */
    @GetMapping("/goToListPage")
    public String listProducts(Model model) {
        String target = LIST_PRODUCTS_VIEW;
        try {
            List<ProductDTO> products = productService.sendGetMsg();
            model.addAttribute("products", products);
        } catch(Exception ex) {
            log.error("List of products cannot be retrieved: " + ex.getMessage());
            model.addAttribute("error", true);
            target = INDEX_VIEW;
        }
        return target;
    }

    /**
     * method to go from index to create a product view
     *
     * @return view file name
     */
    @GetMapping("/goToCreatePage")
    public String goToCreateProduct() {
        return CREATE_PRODUCT_VIEW;
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
        return CREATE_PRODUCT_VIEW;
    }
}
