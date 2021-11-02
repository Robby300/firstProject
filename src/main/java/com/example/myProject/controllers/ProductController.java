package com.example.myProject.controllers;


import com.example.myProject.models.Product;
import com.example.myProject.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    public String shopProductAdd(@RequestParam String name, @RequestParam Double price, @RequestParam String description,
                                 @RequestParam("fileImage") MultipartFile multipartFile,
                                 Model model) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Product product = new Product(name, price, description);
        product.setLogo(fileName);
        productRepository.save(product);
        String uploadDir = "./images/" + product.getId();


        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Не могу сохранить загруженный файл: " + fileName);
        }

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
            ,@RequestParam Double price, @RequestParam String description, @RequestParam("fileImage") MultipartFile multipartFile, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setLogo(fileName);
        productRepository.save(product);
        String uploadDir = "./src/main/resources/static/images/" + product.getId();

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toString());
            //System.out.println(filePath.relativize(Path.of("./images/28/")));
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new IOException("Не могу сохранить загруженный файл: " + fileName);
        }


        return "redirect:/shop/" + id;
    }

    @PostMapping("/shop/{id}/remove")
    public String shopProductDelete(@PathVariable(value = "id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/shop";
    }
}
