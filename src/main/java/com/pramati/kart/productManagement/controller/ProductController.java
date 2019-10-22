package com.pramati.kart.productManagement.controller;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.pramati.kart.productManagement.entity.Product;
import com.pramati.kart.productManagement.repository.ProductRepository;
import com.pramati.kart.productManagement.utility.BeanUtility;
import com.pramati.kart.productManagement.utility.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private BeanUtility beanUtility;

    @GetMapping("/product/{id}")
    public Product getProductByID(@PathVariable("id") long id) {
        return repository.findById(id).orElse(null);

    }

//    @GetMapping("/product")
//    public List<Product> getAllProducts() {
//        return repository.findAll();
//    }

    @PostMapping("/product")
    public Response createProduct(@RequestBody Product product) {
        repository.saveAndFlush(product);
        return new Response("Product Created Successfully");
    }

    @PutMapping("/product/{id}")
    public Product updateEmployee(@PathVariable("id") Long id,@RequestBody Product productdata) {
        Product product=repository.findById(id).orElse(null);
        if(product==null) return null;
        BeanUtils.copyProperties(productdata, product,beanUtility.getNullPropertyNames(productdata));
        repository.saveAndFlush(product);
        return  repository.findById(id).orElse(null);
    }

    @DeleteMapping("/product/{id}")
    public Response deleteProduct(@PathVariable("id") Long id){
        repository.deleteById(id);
        return new Response("Product Deleted Successfully");

    }

    @GetMapping("/product")
    public List<Product> searchProduct(@RequestParam("search") String search){
       if(search==null)  return repository.findAll();
           return repository.findProduct(search);


    }


    @PatchMapping("/product/{id}")
    public Product patchProduct(@PathVariable("id") Long id, @RequestBody HashMap<String, Object> fields) {
        Product product=repository.findById(id).orElse(null);
        String[] attributes = new String[]{"createdTime", "lastModifiedTime", "id","createdBy"};
        List<String> list = Arrays.asList(attributes);
        if(product==null) return null;
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Product.class, k);
            if (field != null && !list.contains(field.getName()) )
            { System.out.println(field.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, product,v);}
        });
        repository.saveAndFlush(product);
        return product;
    }
}
