package com.example.myProject.controllers;


import com.example.myProject.models.Product;
import com.example.myProject.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String shopAdd(Model model) {
        return "shop-add";
    }

    @PostMapping("/shop/add")
    public String shopProductAdd(@RequestParam String name, @RequestParam Double price, @RequestParam String description, @RequestParam String imagePath, Model model) {
        Product product = new Product(name, price, description, imagePath);
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
        return "shop-details";
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
    public String shopProductUpdate(@PathVariable(value = "id") long id, @RequestParam String name
            ,@RequestParam Double price, @RequestParam String description, @RequestParam String imagePath, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImagePath(imagePath);
        productRepository.save(product);
        return "redirect:/shop/" + id;
    }

    @PostMapping("/shop/{id}/remove")
    public String shopProductDelete(@PathVariable(value = "id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/shop";
    }
}
