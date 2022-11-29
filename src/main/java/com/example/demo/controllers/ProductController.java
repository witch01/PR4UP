package com.example.demo.controllers;

import com.example.demo.models.Employee;
import com.example.demo.models.Product;
import com.example.demo.repo.EmployeeRepository;
import com.example.demo.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/prod")
    public String blogMain(Model model)
    {
        Iterable<Product> product = productRepository.findAll();
        model.addAttribute("product", product);
        return "product-main";
    }

    @GetMapping("/add")
    public String ProductAdd(Product product,Model model)
    {
        return "product-add";
    }

//    @PostMapping("/add")
//    public String ProductPostAdd(@RequestParam(defaultValue = "0") String names,
//                                  @RequestParam(defaultValue = "0") double cent,
//                                  @RequestParam(defaultValue = "false") boolean online,
//                                  @RequestParam(defaultValue = "0") int quantity,
//                                  @RequestParam(defaultValue = "0000.00.00") Date dr, Model model)
//    {
//
//        Product product = new Product(names, dr, online, cent, quantity);
//        productRepository.save(product);
//        return "redirect:/";
//    }
@PostMapping("/add")
public String ProductPostAdd (@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "product-add";
        }
        productRepository.save(product);
        return "redirect:/product/prod";
    }

    @GetMapping("/filter")
    public String productFilter(Model model)
    {
        return "product-filter";
    }

    @PostMapping("/filter/result")
    public String productResult(@RequestParam String names, Model model)
    {
        List<Product> result = productRepository.findByNamesContains(names);
        List<Product> result1 = productRepository.findByNames(names);
//        List<Post> result = postRepository.findLikeTitle(title);
        model.addAttribute("result", result);
        return "product-filter";
    }

    @GetMapping("/{id}")
    public String productView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        if(!productRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "product-view";

    }

    @GetMapping("/{id}/edit")
    public String productEdit(@PathVariable("id")long id,
                              Model model)
    {
        Product product = productRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid products Id" + id));
        model.addAttribute("product",product);
        return "product-view";
    }

    @PostMapping("/{id}/edit")
    public String productUpdate(@ModelAttribute("product")@Valid Product product, BindingResult bindingResult,
                                @PathVariable("id") long id) {

        product.setId(id);
        if (bindingResult.hasErrors()) {
            return "product-view";
        }
        productRepository.save(product);
        return "redirect:/product/prod";
    }

    @PostMapping("/{id}/remove")
    public String productDelete(@PathVariable("id") long id, Model model){
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/product/prod";
    }
}

