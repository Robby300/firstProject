package com.example.myProject.controllers;

import com.example.myProject.models.Product;
import com.example.myProject.models.ProductsEnum;
import com.example.myProject.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/shop")
    public String productMain(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/shop/add")
    public String productAdd(Model model) {
        return "shop-add";
    }

    @PostMapping("/shop/add")
    public String shopProductAdd(@RequestParam Double price, @RequestParam String name, @RequestParam ProductsEnum type, Model model) {
        Product product = new Product(price, name, type);
        productRepository.save(product);
        return "redirect:/shop";
    }

    @GetMapping("/shop/{id}")
    public String productDetails(@PathVariable(value = "id") long id, Model model) {
        if(!productRepository.existsById(id)) {
            return "redirect:/shop";
        }
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        return "product-details";
    }

    @GetMapping("/shop/{id}/edit")
    public String productEdit(@PathVariable(value = "id") long id, Model model) {
        if (!productRepository.existsById(id)) {
            return "redirect:/shop";
        }
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        return "shop-edit";
    }

    @PostMapping("/shop/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam Double price, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setPrice(price);
        productRepository.save(product);
        return "redirect:/shop/" + id;
    }
}
