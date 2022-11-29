package com.youtube.jwt.controller;

import com.youtube.jwt.entity.Product;
import com.youtube.jwt.exception.ProductNotFoundException;
import com.youtube.jwt.service.ProductService;
import com.youtube.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getall")
    public List<Product> getProduct(){
        System.out.println("Get all");
        return productService.getProduct();
    }

    @PostMapping("/AddProduct")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @DeleteMapping("deleteProduct/{id}")
    public String deleteTutorial(@PathVariable("id") int id ) {
        productService.deleteProduct(id);
        return "Product successfully deleted";
    }

    @PutMapping("/updateProduct/{id}")
    public Product updateProduct(@RequestBody Product product,
                                 @PathVariable("id") int productId)
    {

        return productService.updateProduct(
                product, productId);
    }
    @PutMapping("/updateProductqty/{id}/{qty}")
    public Product updateProductqty(@PathVariable("id") int productId , @PathVariable("qty") int qty)
    {
        //To decrease set value as minus
        return productService.updateProductqty(productId,qty);

    }
    @GetMapping("/getProductById/{id}")
    public Product getProductById(@PathVariable("id") int productId) throws ProductNotFoundException {

        return productService.getProductById(productId);
    }



}
