package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddToCart;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItem;
import com.example.demo.dto.CartShowDTO;
import com.example.demo.dto.ProductDiscountDTO;
import com.example.demo.entities.Cart;
import com.example.demo.entities.Discount;
import com.example.demo.entities.DiscountDetail;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.exception.CartItemNotExistException;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.DiscountDetailRepository;
import com.example.demo.repository.DiscountRepository;
import com.example.demo.service.CartService;


@Service
public class CartServiceImp implements CartService{
	@Autowired
    private CartRepository cartRepository;
	
	@Autowired
    private DiscountDetailRepository  discountDetailRepository;
	
	@Autowired
    private DiscountRepository  discountRepository;

    @Override
    public Cart addToCart(AddToCart addToCart, ProductDetail product, User user) {
    	Cart foundCart = cartRepository.findByProductAndUser(product,user);
    	if(foundCart==null) {
    		Cart cart = new Cart(product, addToCart.getQuantity(), user, false);
            cartRepository.save(cart);
            return cart;
    	} else {
    		foundCart.setQuantity(foundCart.getQuantity()+addToCart.getQuantity());
    		return foundCart;
    	}
    }
    
    public CartDto listCartItem(User user) {
        List<Cart> cartList = cartRepository.findAllByUser(user);
        List<CartItem> cartItemList = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
        	CartItem cartItem = new CartItem(cart);
        	Integer percent = discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), cartItem.getProduct().getProduct())==null?0:discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), cartItem.getProduct().getProduct());
        	Double price= 0.0;
        	if (cartItem.getProduct().getSize().equals("XXS")) {
				price = cartItem.getProduct().getProduct().getPrice() ;
			}
			if (cartItem.getProduct().getSize().equals("XS")) {
				price = cartItem.getProduct().getProduct().getPrice() ;
			}
			if (cartItem.getProduct().getSize().equals("S")) {
				price = cartItem.getProduct().getProduct().getPrice() + 5000;
			}
			if (cartItem.getProduct().getSize().equals("M")) {
				price = cartItem.getProduct().getProduct().getPrice() + 5000;
			}
			if (cartItem.getProduct().getSize().equals("L")) {
				price = cartItem.getProduct().getProduct().getPrice() + 10000;
			}
			if (cartItem.getProduct().getSize().equals("XL")) {
				price = cartItem.getProduct().getProduct().getPrice() + 10000;
			}
			if (cartItem.getProduct().getSize().equals("XXL")) {
				price = cartItem.getProduct().getProduct().getPrice() + 15000;
			}
			if (cartItem.getProduct().getSize().equals("XXXL")) {
				price = cartItem.getProduct().getProduct().getPrice() + 15000;
			}
        	cartItem.setNewPrice((double) Math.floor((price - price*percent/100) / 1000) * 1000);   
        	cartItem.setTotal(cartItem.getNewPrice()*cartItem.getQuantity());
            cartItemList.add(cartItem);
        }
        for (CartItem cartItem : cartItemList) {
            totalCost += cartItem.getTotal();
        }
        CartDto cartDto = new CartDto(cartItemList, totalCost);
        return cartDto;
    }

    @Override
    public CartShowDTO listCartShow(User user) {
        List<Cart> cartList = cartRepository.findAllByUser(user);
        List<CartItem> cartItemList = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
        	CartItem cartItem = new CartItem(cart);
        	Integer percent = discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), cartItem.getProduct().getProduct())==null?0:discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), cartItem.getProduct().getProduct());
        	Double price= 0.0;
        	if (cartItem.getProduct().getSize().equals("XXS")) {
				price = cartItem.getProduct().getProduct().getPrice() ;
			}
			if (cartItem.getProduct().getSize().equals("XS")) {
				price = cartItem.getProduct().getProduct().getPrice() ;
			}
			if (cartItem.getProduct().getSize().equals("S")) {
				price = cartItem.getProduct().getProduct().getPrice() + 5000;
			}
			if (cartItem.getProduct().getSize().equals("M")) {
				price = cartItem.getProduct().getProduct().getPrice() + 5000;
			}
			if (cartItem.getProduct().getSize().equals("L")) {
				price = cartItem.getProduct().getProduct().getPrice() + 10000;
			}
			if (cartItem.getProduct().getSize().equals("XL")) {
				price = cartItem.getProduct().getProduct().getPrice() + 10000;
			}
			if (cartItem.getProduct().getSize().equals("XXL")) {
				price = cartItem.getProduct().getProduct().getPrice() + 15000;
			}
			if (cartItem.getProduct().getSize().equals("XXXL")) {
				price = cartItem.getProduct().getProduct().getPrice() + 15000;
			}
        	cartItem.setNewPrice((double) Math.floor((price - price*percent/100) / 1000) * 1000);   
        	cartItem.setTotal(cartItem.getNewPrice()*cartItem.getQuantity());
            cartItemList.add(cartItem);
        }
        for (CartItem cartItem : cartItemList) {
            totalCost += cartItem.getTotal();
        }
        CartDto cartDto = new CartDto(cartItemList, totalCost);
        
        List<ProductDiscountDTO> productDiscounts = new ArrayList<>();
		for (Discount discount : discountRepository.findByEndDayAfter(new Date())) {
			ProductDiscountDTO productDiscountDTO = new ProductDiscountDTO();
			if (discount.getMemberType() == null) {
				if (discount.getQuantity()==0) {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(false); 
					productDiscountDTO.setReason("H???t m??");
					productDiscounts.add(productDiscountDTO);	
				}
				if (cartDto.getTotalCost() > discount.getCondition()) {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(true); 
					productDiscounts.add(productDiscountDTO);
				}else {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(false); 
					productDiscountDTO.setReason("Gi?? tr??? ????n h??ng ch??a ????? ??i???u ki???n ??p d???ng m?? n??y");
					productDiscounts.add(productDiscountDTO);	
				}
			}
			else if (discount.getMemberType().equalsIgnoreCase("thuong")) {
				if (discount.getQuantity()==0) {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(false); 
					productDiscountDTO.setReason("H???t m??");
					productDiscounts.add(productDiscountDTO);	
				}
				if (cartDto.getTotalCost() > discount.getCondition()) {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(true); 
					productDiscounts.add(productDiscountDTO);
				}else {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(false); 
					productDiscountDTO.setReason("Gi?? tr??? ????n h??ng ch??a ????? ??i???u ki???n ??p d???ng m?? n??y");
					productDiscounts.add(productDiscountDTO);	
				}	
			}
			else if (discount.getMemberType().equalsIgnoreCase("truc tiep")) {
				continue;
			}
			else if (discount.getMemberType().equalsIgnoreCase("nganh hang")) {
				Set<Long> lst = new HashSet<>();
				for (CartItem item : cartDto.getCartItems()) {
					DiscountDetail discountDetail = discountDetailRepository.findByProductAndDiscount(item.getProduct().getProduct(),discount);
					if (discountDetail==null) {
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(false); 
						productDiscountDTO.setReason("M?? n??y kh??ng ??p d???ng v???i s???n ph???m trong gi??? h??ng");
						productDiscounts.add(productDiscountDTO);
						lst.clear();
						break;
					}else {
						lst.add(discountDetail.getDiscount().getId());
					}
				}		
				if (lst.size()==1) {
					if (cartDto.getTotalCost() > discount.getCondition()) {
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(true); 
						productDiscounts.add(productDiscountDTO);
					}else {
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(false); 
						productDiscountDTO.setReason("Gi?? tr??? ????n h??ng ch??a ????? ??i???u ki???n ??p d???ng m?? n??y");
						productDiscounts.add(productDiscountDTO);	
					}	
				}
				if (lst.size()>1) {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(false); 
					productDiscountDTO.setReason("M?? n??y ch??? ??p d???ng cho m???t s??? s???n ph???m nh???t ?????nh");
					productDiscounts.add(productDiscountDTO);	
				}
			}
			else if (user == null || user.getIsMember() == 0) {
				productDiscountDTO.setDiscount(discount);
				productDiscountDTO.setAble(false); 
				productDiscountDTO.setReason("M?? ch??? d??nh cho th??nh vi??n");
				productDiscounts.add(productDiscountDTO);
			}
			else {
				if (user.getMemberType().equalsIgnoreCase("?????ng")) {
					if (discount.getMemberType().equalsIgnoreCase(user.getMemberType())) {
						if (discount.getQuantity()==0) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("H???t m??");
							productDiscounts.add(productDiscountDTO);	
						}if (cartDto.getTotalCost() > discount.getCondition()) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(true);
							productDiscountDTO.setReason("M?? ?????ng");
							productDiscounts.add(productDiscountDTO);
						}else {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("Gi?? tr??? ????n h??ng ch??a ????? ??i???u ki???n ??p d???ng m?? n??y");
							productDiscounts.add(productDiscountDTO);	
						}					
					}else {
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(false); 
						productDiscountDTO.setReason("B???n c???n n??ng c???p th??nh vi??n "+discount.getMemberType() +" ????? s??? d???ng m?? n??y");
						productDiscounts.add(productDiscountDTO);
					}
				}
				
				if (user.getMemberType().equalsIgnoreCase("b???c")) {
					if (discount.getMemberType().equalsIgnoreCase(user.getMemberType()) || discount.getMemberType().equalsIgnoreCase("?????ng")) {
						if (discount.getQuantity()==0) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("H???t m??");
							productDiscounts.add(productDiscountDTO);	
						}if (cartDto.getTotalCost() > discount.getCondition()) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(true);
							productDiscountDTO.setReason("M?? "+discount.getMemberType());
							productDiscounts.add(productDiscountDTO);
						}else {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("Gi?? tr??? ????n h??ng ch??a ????? ??i???u ki???n ??p d???ng m?? n??y");
							productDiscounts.add(productDiscountDTO);	
						}									
					}else {
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(false); 
						productDiscountDTO.setReason("B???n c???n n??ng c???p th??nh vi??n "+discount.getMemberType() +" ????? s??? d???ng m?? n??y");
						productDiscounts.add(productDiscountDTO);
					}
				}
				if (user.getMemberType().equalsIgnoreCase("v??ng")) {
						if (discount.getQuantity()==0) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("H???t m??");
							productDiscounts.add(productDiscountDTO);	
						}if (cartDto.getTotalCost() > discount.getCondition()) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(true);
							productDiscountDTO.setReason("M?? "+discount.getMemberType());
							productDiscounts.add(productDiscountDTO);
						}else {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("Gi?? tr??? ????n h??ng ch??a ????? ??i???u ki???n ??p d???ng m?? n??y");
							productDiscounts.add(productDiscountDTO);	
						}									
				}
			}
		}
		return new CartShowDTO(cartDto, productDiscounts);
    }

    private CartItem getCartItemFromCart(Cart cart) {
        return new CartItem(cart);
    }

    @Override
    public Cart updateCart(AddToCart addToCart, Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        cart.get().setQuantity(addToCart.getQuantity());
        cart.get().setCreatedDate(new Date());
        cartRepository.save(cart.get());
        return cart.get();
    }

    @Override
    public void removeCartItem(Long id, Long userId) throws CartItemNotExistException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);
    }

    @Override
    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }

	
}
