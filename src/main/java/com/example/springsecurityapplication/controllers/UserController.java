package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.enums.Status;
import com.example.springsecurityapplication.models.Cart;
import com.example.springsecurityapplication.models.Orders;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CartRepository;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.security.PersonDetails;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

    private final OrderRepository orderRepository;


    @Autowired
    public UserController(ProductRepository productRepository, CartRepository cartRepository, ProductService productService, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/index")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        String role = personDetails.getPerson().getRole();
        if(role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        model.addAttribute("products", productService.getAllProduct());
        return "user/index";
    }

    @PostMapping("/index/search")
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

        return "user/index";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @GetMapping("/cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int personId = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(personId);
        List<Product> productList = new ArrayList<>();
        for(Cart cart : cartList) {
            productList.add(productService.getProductId(cart.getProductId()));
        }
        float price = 0;
        for (Product product : productList){
            price += product.getPrice();
        }
        model.addAttribute("total_price", price);
        model.addAttribute("user_cart", productList);
        return "user/cart";
    }

    @GetMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") int id, Model model){
        Product product = productService.getProductId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int personId = personDetails.getPerson().getId();
        Cart cart = new Cart(personId, product.getId());
        cartRepository.save(cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteProductFromCart(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int personId = personDetails.getPerson().getId();
        cartRepository.deleteCartById(id, personId);
        return "redirect:/cart";
    }

    @GetMapping("/order/create")
    public String createOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int personId = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(personId);
        List<Product> productList = new ArrayList<>();
        for(Cart cart : cartList) {
            productList.add(productService.getProductId(cart.getProductId()));
        }
        String uuid = UUID.randomUUID().toString();
        for(Product product : productList){
            Orders newOrder = new Orders(uuid, 1, product.getPrice(), Status.Оформлен, product, personDetails.getPerson());
            orderRepository.save(newOrder);
            cartRepository.deleteCartById(product.getId(), personId);
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String userOrders(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Orders> ordersList = orderRepository.findByPerson(personDetails.getPerson());
        model.addAttribute("user_orders", ordersList);
        return "user/orders";
    }

}
