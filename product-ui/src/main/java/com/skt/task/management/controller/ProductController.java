package com.skt.task.management.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * controller class to manage the binding between JSPs and server
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

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
        List<ProductDTO> products = productService.sendGet();
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
    @PostMapping("/createProduct")
    public String createProduct(Model model, ProductDTO product) {
        productService.publishPostRequestMsg(product);
        return "list-products";
    }
}
