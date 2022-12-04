package com.youtube.jwt.controller;

import com.youtube.jwt.entity.Product;
import com.youtube.jwt.exception.ProductNotFoundException;
import com.youtube.jwt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getall")
    public List<Product> getProduct() throws FileNotFoundException {
        System.out.println("Get all");
        return productService.getProduct();
    }

    @PostMapping("/AddProduct")
    public Product addProduct(@RequestBody @Valid Product product){
        return productService.addProduct(product);
    }

    @DeleteMapping("deleteProduct/{id}")
    public String deleteTutorial(@PathVariable("id") @Valid int id ) {
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
    @GetMapping("/csvExport")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Product> listProduct = productService.listAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"name", "Category", "qty"};
        String[] nameMapping = {"name", "Category", "qty"};

        csvWriter.writeHeader(csvHeader);

        for (Product product : listProduct) {
            csvWriter.write(product, nameMapping);
        }

        csvWriter.close();

    }


}
