package com.youtube.jwt.service;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.youtube.jwt.dao.ProductRepository;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.exception.ProductNotFoundException;
import com.youtube.jwt.payload.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private static final String UNAUTHORISED_MESSAGE = "Authentication failed";



    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("akiladissanayaka255@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            javaMailSender.send(message);
            LOGGER.info("Successfully send the mail");
        }
        catch (Exception e){
            LOGGER.error(">>> Unable to send email");
            LOGGER.error("Context", e);
        }


    }

    public Product updateProductqty(int productId, int qty) {

        try{
            LOGGER.info("Successfully update the product");
            Product depDB = productRepository.findById(productId).get();
            int newQty= depDB.getQty()+qty;
            depDB.setQty(newQty);
            return productRepository.save(depDB);
        }
        catch (Exception e){
            LOGGER.error(">>> Unable to send email");
            LOGGER.error("Context", e);
            return null;
        }
    }

    public void GenPDF(List<Product> products) throws FileNotFoundException {

        try{
            LOGGER.info("Successfully build the pdf");
            String path="testPdf.pdf";
            String text="Helooooooooooo new text";

            String result = products.stream()
                    .map(n -> {
                        return String.valueOf(n.getName()) +" - "+String.valueOf(n.getQty());
                    })
                    .collect(Collectors.joining("\n"));
            Paragraph paragraph=new Paragraph(result);
            PdfWriter pdfWriter= new PdfWriter(path);
            PdfDocument pdfDocument=new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            Document document=new Document(pdfDocument);
            document.add(paragraph);
            document.close();
        }
        catch (Exception e){
            LOGGER.error(">>> Unable to send email");
            LOGGER.error("Context", e);
        }

    }
    public ResponseEntity getProduct() throws FileNotFoundException {

        try{
            LOGGER.info("Successfully get product");
            List<Product> products=new ArrayList<Product>(productRepository.findAll());

            GenPDF(products);
            return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
//            return products;
        }
        catch (Exception e){
            LOGGER.error(">>> Unable to get product");
            LOGGER.error("Context", e);
//            return null;
            return ResponseEntity.ok(new ApiResponse(false, UNAUTHORISED_MESSAGE));


        }

    }


    public String deleteProduct(int id) {

        try{
            LOGGER.info("Successfully delete product");
            productRepository.deleteById((int) id);
            return "success";
        }
        catch (Exception e){
            LOGGER.error(">>> Unable to delete product");
            LOGGER.error("Context", e);
            return null;
        }

    }


    public Product updateProduct(Product product, int productId) {

        try{
            LOGGER.info("Successfully update product");
            Product depDB = productRepository.findById(productId).get();
            if (Objects.nonNull(product.getName()) && !"".equalsIgnoreCase(product.getName())) {
                depDB.setName(product.getName());
            }
            if (Objects.nonNull(product.getCategory()) && !"".equalsIgnoreCase(product.getCategory())) {
                depDB.setCategory(product.getCategory());
            }
            return productRepository.save(depDB);
        }
        catch (Exception e){
            LOGGER.error(">>> Unable to update product");
            LOGGER.error("Context", e);
            return null;
        }
    }


    public Product addProduct(Product product) {

        try{
            LOGGER.info("Successfully add product");
            return productRepository.save(product);

        }
        catch (Exception e){
            LOGGER.error(">>> Unable to add product");
            LOGGER.error("Context", e);
            return null;
        }
    }

    public Product getProductById(int productId) throws ProductNotFoundException {

        try{
            LOGGER.info("Successfully get product by Id");
            Product product= productRepository.getProductById(productId);
            if(product!= null){
                return product;
            }
            else {
                throw new ProductNotFoundException("Product not found id- "+productId);
            }
        }
            catch (Exception e){
            LOGGER.error(">>> Unable to get product by id");
            LOGGER.error("Context", e);
            return null;
        }
    }

}
