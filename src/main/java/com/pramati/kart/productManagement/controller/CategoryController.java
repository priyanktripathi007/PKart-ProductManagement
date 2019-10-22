package com.pramati.kart.productManagement.controller;

import com.pramati.kart.productManagement.entity.Category;
import com.pramati.kart.productManagement.repository.CategoryRepository;
import com.pramati.kart.productManagement.utility.BeanUtility;
import com.pramati.kart.productManagement.entity.Category;
import com.pramati.kart.productManagement.utility.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private BeanUtility beanUtility;

    @GetMapping("/category/{id}")
    public Category getCategoryByID(@PathVariable("id") long id) {
        return repository.findById(id).orElse(null);

    }

    @GetMapping("/category")
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @PostMapping("/category")
    public Response createCategory(@RequestBody Category category) {
        repository.saveAndFlush(category);
        return new Response("Category Created Successfully");
    }

    @PutMapping("/category/{id}")
    public Category updateEmployee(@PathVariable("id") Long id,@RequestBody Category categorydata) {
        Category category=repository.findById(id).orElse(null);
        if(category==null) return null;
        BeanUtils.copyProperties(categorydata, category,beanUtility.getNullPropertyNames(categorydata));
        repository.saveAndFlush(category);
        return  repository.findById(id).orElse(null);
    }

    @DeleteMapping("/category/{id}")
    public Response deleteCategory(@PathVariable("id") Long id){
        repository.deleteById(id);
        return new Response("Category Deleted Successfully");

    }




    @PatchMapping("/category/{id}")
    public Category patchCategory(@PathVariable("id") Long id, @RequestBody HashMap<String, Object> fields) {
        Category category=repository.findById(id).orElse(null);
        String[] attributes = new String[]{"createdTime", "lastModifiedTime", "id","createdBy"};
        List<String> list = Arrays.asList(attributes);
        if(category==null) return null;
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Category.class, k);
            if (field != null && !list.contains(field.getName()) )
            { System.out.println(field.toString());
                field.setAccessible(true);

                ReflectionUtils.setField(field, category,v);}
        });
        repository.saveAndFlush(category);
        return category;
    }
}
