package com.example.demo.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.AddToCart;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItem;
import com.example.demo.dto.CartShowDTO;
import com.example.demo.dto.UpdateCartDTO;
import com.example.demo.entities.Cart;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.DiscountDetailRepository;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.CartServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CartController {
	@Autowired
    private CartServiceImp cartService;
	
	@Autowired
    private CartRepository cartRepository;
	
	@Autowired
    private DiscountDetailRepository discountDetailRepository;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductDetailRepository productDetailRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @RequestMapping("/detail")
    public String detail() {
    	return "customer/shop-single"; 
    }
    
    @GetMapping("api/cart/list")
    @ResponseBody
	 public CartShowDTO listCart(@RequestParam(name = "user") @Nullable Integer userId) {
    	User user = null;
		if (userId != null) {
			user = userRepository.findById(userId).get();
		}else {
			return null;
		}
		CartShowDTO cartDto = cartService.listCartShow(user);				
		return cartDto;
	}
    
    @RequestMapping({"api/cart/checkouts"})
	public String checkout(Model model,Principal principal) {
    	if(principal==null) {
    		return "redirect:/security/login";
    	}
    	User user = userRepository.findByUsernameEquals(principal.getName());
		CartDto cartDto = cartService.listCartItem(user);
		int total = (int) cartDto.getTotalCost();
		List<CartItem> list = cartDto.getCartItems();
		int quantity=0;
		for (CartItem item : list) {
			quantity += item.getQuantity();
		}
		model.addAttribute("total", total);
		model.addAttribute("dto", cartDto);
		model.addAttribute("itemList", list);
		model.addAttribute("user", user);
		model.addAttribute("quantity", quantity);
		return "customer/checkout";
	}

//    @GetMapping("/cart")
//    @ResponseBody
//    public CartDto getCartItems(Principal principal) {
//    	User user = userRepository.findByUsernameEquals(principal.getName());
//        CartDto cartDto = cartService.listCartItem(user);
//        return cartDto;
//    }

    @RequestMapping(value = "api/cart/add",method = RequestMethod.POST)
    @ResponseBody
    public String addToCart(@RequestParam("color") String color,@RequestParam("proId") Long id,@RequestParam("size") String size,
    		@RequestParam("quantity") Integer quantity,@RequestParam(name = "user") @Nullable Integer userId) {
    	User user = null;
		if (userId != null) {
			user = userRepository.findById(userId).get();
		}else {
			return "Bạn cần đăng nhập";
		}
		AddToCart add = new AddToCart(id, quantity);
		List<ProductDetail> productDetail =  productDetailRepository.findByColorAndSizeAndProductId(color,size,id);
		System.out.println(productDetail);
		Cart cart = cartService.addToCart(add, productDetail.get(0), user);  
        if (cart == null) {
			return "Có lỗi khi thêm vào giỏ hàng mời thử lại";
		}
        return "Thành công";
    }
    
   
    
    @PostMapping("/cart/add/{proId}")
    @ResponseBody
    public void addToCart2(@ModelAttribute("addToCart") AddToCart addToCart,@PathVariable Long proId, Model model,Principal principal) {
    	User user = userRepository.findByUsernameEquals(principal.getName());

        ObjectMapper mapper =new ObjectMapper();
        try {
			String jsonString = mapper.writeValueAsString(addToCart);
			AddToCart add = mapper.readValue(jsonString, AddToCart.class);
			model.addAttribute("addToCart", addToCart);
			model.addAttribute("quantity",addToCart.getQuantity());
			model.addAttribute("insertCart",cartService.addToCart(add, productDetailRepository.findById(add.getProductId()).get(), user));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }

    @PutMapping("api/cart/update")
    @ResponseBody
    public UpdateCartDTO updateCartItem(@RequestParam("cartId") Long id, @RequestParam("quantity") Integer quantity) {
    	Cart cart = cartRepository.findById(id).get();
    	CartItem cartItem = new CartItem(cart);
    	if (quantity>cart.getProduct().getQuantity()) {
    		return new UpdateCartDTO(null, false, "Vượt quá số lượng hàng còn lại");
		}
    	Integer percent = discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), cartItem.getProduct().getProduct())==null?0:discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), cartItem.getProduct().getProduct());
    	cartItem.setNewPrice((double) Math.floor((cartItem.getProduct().getProduct().getPrice() - cartItem.getProduct().getProduct().getPrice()*percent/100) / 1000) * 1000);   
    	cartItem.setTotal(cartItem.getNewPrice()*cartItem.getQuantity());
    	cart.setQuantity(quantity);
        cartRepository.save(cart);
        return new UpdateCartDTO(cartItem, true, "success");
    }

    @RequestMapping("/cart/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable("cartItemId") Long itemID,Model model,Principal principal) {
    	if(principal==null) {
    		return "redirect:/security/login";
    	}
    	User user = userRepository.findByUsernameEquals(principal.getName());
    	Long userId= Long.valueOf(user.getId());
        cartService.removeCartItem(itemID, userId);
        return "redirect:/cartlist";
    }
}
