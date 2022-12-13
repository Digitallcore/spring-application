package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class MainController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public MainController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("")
    public String getAllProduct(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "product/product";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @PostMapping("/search")
    public String indexSearch(@RequestParam("search") String search, @RequestParam("from") String from,
                              @RequestParam("to") String to, @RequestParam(value = "price", required = false,
            defaultValue = "") String price, @RequestParam(value = "category", required = false, defaultValue = "") String category, Model model){
        if (!from.isEmpty() & !to.isEmpty()){
            if(!price.isEmpty()){
                if(price.equals("asc_price")){
                    if(!category.isEmpty()) {
                        if (category.equals("furniture")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                        } else if (category.equals("appliances")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        } else if (category.equals("clothes")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                        }
                    } else {
                        model.addAttribute("search_product", productRepository.findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    }
                }else if(price.equals("desc_price")){
                    if(!category.isEmpty()) {
                        if (category.equals("furniture")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                        } else if (category.equals("appliances")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        } else if (category.equals("clothes")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                        }
                    } else {
                        model.addAttribute("search_product", productRepository.findByTitleOrderByPriceDesc(search.toLowerCase(),Float.parseFloat(from), Float.parseFloat(to)));
                    }
                }
            } else {
                if(!category.isEmpty()) {
                    if (category.equals("furniture")) {
                        model.addAttribute("search_product", productRepository.findByTitlePriceCategory(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_product", productRepository.findByTitlePriceCategory(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                    } else if (category.equals("clothes")) {
                        model.addAttribute("search_product", productRepository.findByTitlePriceCategory(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 3));
                    }
                } else {
                    model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                }
            }
        } else {
            if(!price.isEmpty()){
                if(price.equals("asc_price")){
                    if(!category.isEmpty()) {
                        if (category.equals("furniture")) {
                            model.addAttribute("search_product", productRepository.findByTitleCategoryOrderByPriceAsc(search.toLowerCase(), 1));
                        } else if (category.equals("appliances")) {
                            model.addAttribute("search_product", productRepository.findByTitleCategoryOrderByPriceAsc(search.toLowerCase(), 2));
                        } else if (category.equals("clothes")) {
                            model.addAttribute("search_product", productRepository.findByTitleCategoryOrderByPriceAsc(search.toLowerCase(), 3));
                        }
                    } else {
                        model.addAttribute("search_product", productRepository.findByTitleOrderByPriceAsc(search.toLowerCase()));
                    }
                } else if(price.equals("desc_price")){
                    if(!category.isEmpty()) {
                        if (category.equals("furniture")) {
                            model.addAttribute("search_product", productRepository.findByTitleCategoryOrderByPriceDesc(search.toLowerCase(), 1));
                        } else if (category.equals("appliances")) {
                            model.addAttribute("search_product", productRepository.findByTitleCategoryOrderByPriceDesc(search.toLowerCase(), 2));
                        } else if (category.equals("clothes")) {
                            model.addAttribute("search_product", productRepository.findByTitleCategoryOrderByPriceDesc(search.toLowerCase(), 3));
                        }
                    }else {
                        model.addAttribute("search_product", productRepository.findByTitleOrderByPriceDesc(search.toLowerCase()));
                    }
                }
            } else {
                if(!category.isEmpty()){
                    if (category.equals("furniture")) {
                        model.addAttribute("search_product", productRepository.findByTitleCategory(search.toLowerCase(), 1));
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_product", productRepository.findByTitleCategory(search.toLowerCase(), 2));
                    } else if (category.equals("clothes")) {
                        model.addAttribute("search_product", productRepository.findByTitleCategory(search.toLowerCase(), 3));
                    }
                }else{
                    model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
                }
            }
        }
        model.addAttribute("value_search", search);
        model.addAttribute("value_from", from);
        model.addAttribute("value_to", to);
        model.addAttribute("products", productService.getAllProduct());

        return "product/product";
    }

}
