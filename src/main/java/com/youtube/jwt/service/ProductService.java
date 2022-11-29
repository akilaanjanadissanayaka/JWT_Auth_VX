package com.youtube.jwt.service;

import com.youtube.jwt.dao.ProductRepository;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendSimpleEmail(String toEmail,
//                                String subject,
//                                String body
//    ) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("akiladissanayaka255@gmail.com");
//        message.setTo(toEmail);
//        message.setText(body);
//        message.setSubject(subject);
//        mailSender.send(message);
//        System.out.println("Mail Send...");
//
//
//    }

    public Product updateProductqty(int productId, int qty) {
        Product depDB = productRepository.findById(productId).get();
        int newQty= depDB.getQty()+qty;
        depDB.setQty(newQty);
        return productRepository.save(depDB);
    }

    public List<Product> getProduct() {
        return (List<Product>) productRepository.findAll();
    }


    public String deleteProduct(int id) {
        productRepository.deleteById((int) id);
        return "success";
    }


    public Product updateProduct(Product product, int productId) {
        Product depDB = productRepository.findById(productId).get();
        if (Objects.nonNull(product.getName()) && !"".equalsIgnoreCase(product.getName())) {
            depDB.setName(product.getName());
        }
        if (Objects.nonNull(product.getCategory()) && !"".equalsIgnoreCase(product.getCategory())) {
            depDB.setCategory(product.getCategory());
        }
        return productRepository.save(depDB);
    }


    public Product addProduct(Product product) {
//        sendSimpleEmail("akiladissanayaka255@gmail.com","Test subject",product.getName());
        return productRepository.save(product);
    }

    public Product getProductById(int productId) throws ProductNotFoundException {
        Product product= productRepository.getProductById(productId);
        if(product!= null){
            return product;
        }
        else {
            throw new ProductNotFoundException("Product not found id- "+productId);
        }
    }

}
