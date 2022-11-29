package com.youtube.jwt.service;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.youtube.jwt.dao.ProductRepository;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("akiladissanayaka255@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
        System.out.println("Mail Send...");


    }

    public Product updateProductqty(int productId, int qty) {
        Product depDB = productRepository.findById(productId).get();
        int newQty= depDB.getQty()+qty;
        depDB.setQty(newQty);
        return productRepository.save(depDB);
    }

    public void GenPDF(List<Product> products) throws FileNotFoundException {
        String path="testPdf.pdf";
        String text="Helooooooooooo new text";

        String result = products.stream()
                .map(n -> {
                    return String.valueOf(n.getName()) +" - "+String.valueOf(n.getQty());
                })
                .collect(Collectors.joining("\n"));
//        System.out.println(result);
        Paragraph paragraph=new Paragraph(result);
        PdfWriter pdfWriter= new PdfWriter(path);
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();
        Document document=new Document(pdfDocument);
        document.add(paragraph);
        document.close();

    }
    public List<Product> getProduct() throws FileNotFoundException {
        List<Product> products=new ArrayList<Product>(productRepository.findAll());
        GenPDF(products);
        return products;
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
