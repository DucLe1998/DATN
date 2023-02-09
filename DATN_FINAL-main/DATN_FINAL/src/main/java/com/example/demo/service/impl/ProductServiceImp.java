package com.example.demo.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItem;
import com.example.demo.dto.CartShowDTO;
import com.example.demo.dto.ColorDTO;
import com.example.demo.dto.ProductDetailShowDTO;
import com.example.demo.dto.ProductDiscountDTO;
import com.example.demo.dto.ProductShow;
import com.example.demo.dto.RelatedColorDTO;
import com.example.demo.dto.RelatedSizeDTO;
import com.example.demo.dto.SizeDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.Discount;
import com.example.demo.entities.DiscountDetail;
import com.example.demo.entities.Interaction;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.repository.BillDetailRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.DiscountDetailRepository;
import com.example.demo.repository.DiscountRepository;
import com.example.demo.repository.InteractionRepository;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;

import net.bytebuddy.asm.Advice.Return;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private DiscountDetailRepository discountDetailRepository;
	
	@Autowired
	private DiscountRepository discountRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private InteractionRepository interactionRepository;

	@Autowired
	private BillDetailRepository billDetailRepository;

	@Override
	public synchronized boolean reductionQuantity(User user) {
		CartDto cartDto = cartService.listCartItem(user);

		List<CartItem> cartItemList = cartDto.getCartItems();

		for (CartItem cartItem : cartItemList) {
			// find product by product id and redution quantity when create order
			ProductDetail productl = productDetailRepository.findById(cartItem.getProduct().getId()).get();
			if (productl.getQuantity()<cartItem.getQuantity()){
				return false;
			}
			productl.setQuantity(productl.getQuantity() - cartItem.getQuantity());
			productDetailRepository.save(productl);
		}
		return true;
	}

	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProductShow> getPageProduct(List<Product> products,Pageable pageable, User user) {
		List<ProductShow> productShows = new ArrayList<>();
		for (Product product : products) {
			ProductShow productShow = convertToProductShow(product, user);
			productShows.add(productShow);
		}
		Page<ProductShow> data = toPage(productShows, pageable);
		return data;
	}

	@Override
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeProductById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductById(Long id) {

		return productRepository.findById(id).get();
	}

	@Override
	public List<Product> getAllProductByCategoryId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductByCategoryName(String name) {
		Category foundCategory = categoryRepository.findByName(name);
		if (foundCategory == null) {
			return null;
		}
		List<Product> listProducts = productRepository.findByCategory(foundCategory);
		return listProducts;
	}

	@Override
	public List<Product> getProductByMaterial(String material) {
		List<Product> foundProducts = productRepository.findByMaterial(material);
		if (foundProducts.isEmpty()) {
			return null;
		}
		return foundProducts;
	}

	@Override
	public List<Product> getProductByInteraction() {
		List<Product> lst = new ArrayList<>();
		if (interactionRepository.findByTopInteractions().size() > 20) {
			for (Long id : interactionRepository.findByTopInteractions().subList(0, 6)) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		} else if (interactionRepository.findByTopInteractions().size() > 0) {
			for (Long id : interactionRepository.findByTopInteractions()) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		}
		if (interactionRepository.findByTopInteractions().size() == 0) {
			return null;
		}
		return lst;
	}

	public List<Product> getAllProductByInteraction() {
		List<Product> lst = new ArrayList<>();
		if (interactionRepository.findByTopInteractions().size() > 0) {
			for (Long id : interactionRepository.findByTopInteractions()) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		}
		if (interactionRepository.findByTopInteractions().size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getProductByLike() {
		List<Product> lst = new ArrayList<>();
		if (interactionRepository.findByTopLike().size() > 20) {
			for (Long id : interactionRepository.findByTopLike().subList(0, 9)) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		} else if (interactionRepository.findByTopLike().size() > 0) {
			for (Long id : interactionRepository.findByTopLike()) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		}
		if (interactionRepository.findByTopLike().size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getProductByRating() {
		List<Product> lst = new ArrayList<>();
		if (interactionRepository.findByTopRating().size() > 20) {
			for (Long id : interactionRepository.findByTopRating().subList(0, 9)) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		} else if (interactionRepository.findByTopRating().size() > 0) {
			for (Long id : interactionRepository.findByTopRating()) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		}
		if (interactionRepository.findByTopRating().size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getProductByFilter(String CateName, String material, String color, String size, String order) {
		List<Product> list = new ArrayList<>();
		if (order.equals("atoz")) {
			list = productRepository.findAllByOrderByName();
		}
		if (order.equals("ztoa")) {
			list = productRepository.findAllByOrderByNameDesc();
		}
		if (order.equals("asc")) {
			list = productRepository.findAllByOrderByPrice();
		}
		if (order.equals("desc")) {
			list = productRepository.findAllByOrderByPriceDesc();
		}
		if (CateName != null) {
			list = list.stream().filter(product -> product.getCategory().getName().equals(CateName)).toList();
		}

		if (material != null) {
			list = list.stream().filter(product -> product.getMaterial().equals(material)).toList();
		}
		if (color != null) {
			list = list.stream().filter(product -> !product.getProductDetails().stream()
					.filter(proDetail -> proDetail.getColor().equals(color)).toList().isEmpty()).toList();
		}
		if (size != null) {
			list = list.stream().filter(product -> !product.getProductDetails().stream()
					.filter(proDetail -> proDetail.getSize().equals(size)).toList().isEmpty()).toList();
		}

		return list;
	}

	@Override
	public List<Product> getProductByBuyQuantity() {
		List<Product> lst = new ArrayList<>();
		if (billDetailRepository.findTop10ProductByBuyQuantity().size() > 10) {
			for (Long id : billDetailRepository.findTop10ProductByBuyQuantity().subList(0, 9)) {
				ProductDetail product = productDetailRepository.findById(id).get();
				lst.add(product.getProduct());
			}
		} else if (billDetailRepository.findTop10ProductByBuyQuantity().size() > 0) {
			for (Long id : billDetailRepository.findTop10ProductByBuyQuantity()) {
				ProductDetail product = productDetailRepository.findById(id).get();
				lst.add(product.getProduct());
			}
		}
		if (billDetailRepository.findTop10ProductByBuyQuantity().size() == 0) {
			return null;
		}
		return lst;
	}

	public List<Product> getAllProductByBuyQuantity() {
		List<Product> lst = new ArrayList<>();
		if (billDetailRepository.findTop10ProductByBuyQuantity().size() > 0) {
			for (Long id : billDetailRepository.findTop10ProductByBuyQuantity()) {
				ProductDetail product = productDetailRepository.findById(id).get();
				lst.add(product.getProduct());
			}
		}
		if (billDetailRepository.findTop10ProductByBuyQuantity().size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getProductByPrice(Double min, Double max) {
		List<Product> lst = new ArrayList<>();
		if (productRepository.findByPriceBetween(min, max).size() > 10) {
			for (Product product : productRepository.findByPriceBetween(min, max).subList(0, 9)) {
				lst.add(product);
			}
		} else if (productRepository.findByPriceBetween(min, max).size() > 0) {
			for (Product product : productRepository.findByPriceBetween(min, max)) {
				lst.add(product);
			}
		}
		if (productRepository.findByPriceBetween(min, max).size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getProductByComment() {
		List<Product> lst = new ArrayList<>();
		if (interactionRepository.findByTopComment().size() > 10) {
			for (Long id : interactionRepository.findByTopComment().subList(0, 9)) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		} else if (interactionRepository.findByTopComment().size() > 0) {
			for (Long id : interactionRepository.findByTopComment()) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		}
		if (interactionRepository.findByTopComment().size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getAllProductByCreateDayDesc() {
		List<Product> lst = new ArrayList<>();
		if (productRepository.findAllByOrderByCreateDayDesc().size() > 10) {
			for (Product product : productRepository.findAllByOrderByCreateDayDesc().subList(0, 9)) {
				lst.add(product);
			}
		} else if (productRepository.findAllByOrderByCreateDayDesc().size() > 0) {
			for (Product product : productRepository.findAllByOrderByCreateDayDesc()) {
				lst.add(product);
			}
		}
		if (productRepository.findAllByOrderByCreateDayDesc().size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getAllProductByCreateDayAsc() {
		List<Product> lst = new ArrayList<>();
		if (productRepository.findAllByOrderByCreateDay().size() > 10) {
			for (Product product : productRepository.findAllByOrderByCreateDay().subList(0, 9)) {
				lst.add(product);
			}
		} else if (productRepository.findAllByOrderByCreateDay().size() > 0) {
			for (Product product : productRepository.findAllByOrderByCreateDay()) {
				lst.add(product);
			}
		}
		if (productRepository.findAllByOrderByCreateDay().size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getAllProductByUserLike(User user) {
		List<Product> lst = new ArrayList<>();
		if (interactionRepository.findByUserLike(user).size() > 10) {
			for (Long id : interactionRepository.findByUserLike(user).subList(0, 9)) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		} else if (interactionRepository.findByUserLike(user).size() > 0) {
			for (Long id : interactionRepository.findByUserLike(user)) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		}
		if (interactionRepository.findByUserLike(user).size() == 0) {
			return null;
		}
		return lst;
	}

	public List<Product> getProductByUserLike(User user) {
		List<Product> lst = new ArrayList<>();
		if (interactionRepository.findByUserLike(user).size() > 0) {
			for (Long id : interactionRepository.findByUserLike(user)) {
				Product product = productRepository.findById(id).get();
				lst.add(product);
			}
		}
		if (interactionRepository.findByUserLike(user).size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public List<Product> getAllProductByDiscount() {
		List<Product> lst = new ArrayList<>();
		List<DiscountDetail> discountDetails = discountDetailRepository.findByDiscountEndDayAfter(new Date());
		if (discountDetails.size() > 10) {
			for (DiscountDetail detail : discountDetails.subList(0, 9)) {
				Product product = detail.getProduct();
				lst.add(product);
			}
		} else if (discountDetails.size() > 0) {
			for (DiscountDetail detail : discountDetails) {
				Product product = detail.getProduct();
				lst.add(product);
			}
		}
		if (discountDetails.size() == 0) {
			return null;
		}
		return lst;
	}

	public List<Product> getProductByDiscount() {
		List<Product> lst = new ArrayList<>();
		List<DiscountDetail> discountDetails = discountDetailRepository.findByDiscountEndDayAfter(new Date());
		if (discountDetails.size() > 0) {
			for (DiscountDetail detail : discountDetails) {
				Product product = detail.getProduct();
				lst.add(product);
			}
		}
		if (discountDetails.size() == 0) {
			return null;
		}
		return lst;
	}

	@Override
	public void addLikeProduct(User user, Product product) {
		Interaction inter = interactionRepository.findByUserAndProduct(user, product);
		if (inter != null) {
			if (inter.getLikeStatus() == 0) {
				inter.setLikeStatus(1);
				interactionRepository.save(inter);
			}
			if (inter.getLikeStatus() == 1) {
				inter.setLikeStatus(0);
				interactionRepository.save(inter);
			}
		} else {
			Interaction interaction = new Interaction();
			interaction.setProduct(product);
			interaction.setUser(user);
			interaction.setLikeStatus(1);
			interaction.setCreateTime(new Date());
			interaction.setStatus(true);
			interactionRepository.save(interaction);
		}
	}
	
	private Page<ProductShow> toPage(List<ProductShow> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if(start > list.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        }
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public ProductDetailShowDTO getProductDetail(Long id, User user) {
		Product product = getProductById(id);
		ProductShow productShow = convertToProductShow(product, user) ;
		List<ColorDTO> colors = new ArrayList<>();
		List<SizeDTO> sizes = new ArrayList<>();
		for (String color : productDetailRepository.findProductDetailColor(product)) {
			ColorDTO colorDTO = new ColorDTO();
			colorDTO.setColor(color);
			colorDTO.setImage(productDetailRepository.findProductDetailThumnail(product,color));
			
			List<RelatedSizeDTO> relatedSizes = new ArrayList<>();
			for (ProductDetail proDetail2 :productDetailRepository.findByColorAndProductId(color, id)) {
				RelatedSizeDTO relatedSizeDTO = new RelatedSizeDTO();
				relatedSizeDTO.setSize(proDetail2.getSize());
				relatedSizeDTO.setRemained(true);
				relatedSizes.add(relatedSizeDTO);
			}
			colorDTO.setRelatedSizes(relatedSizes);		
			colors.add(colorDTO);
		}
		
		for (String size : productDetailRepository.findProductDetailSize(product)) {
			SizeDTO sizeDTO = new SizeDTO();
			sizeDTO.setSize(size);
			
			List<RelatedColorDTO> relatedColors = new ArrayList<>();
			for (ProductDetail proDetail2 :productDetailRepository.findBySizeAndProductId(size, id)) {
				RelatedColorDTO relatedColorDTO = new RelatedColorDTO();
				relatedColorDTO.setColor(proDetail2.getColor());
				relatedColorDTO.setRemained(true);
				relatedColors.add(relatedColorDTO);
			}
			sizeDTO.setRelatedColors(relatedColors);		
			sizes.add(sizeDTO);
		}
		
		List<ProductShow> relatedProducts = new ArrayList<>();
		for (Product pro : productRepository.findByCategory(product.getCategory())) {
			relatedProducts.add(convertToProductShow(pro, user));
		}
		
		List<Interaction> interactions = interactionRepository.findByProduct(product);
		
		List<ProductDiscountDTO> productDiscounts = new ArrayList<>();
		for (Discount discount : discountRepository.findByEndDayAfter(new Date())) {
			ProductDiscountDTO productDiscountDTO = new ProductDiscountDTO();
			if (discount.getMemberType() == null) {
				if (discount.getQuantity()==0) {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(false); 
					productDiscountDTO.setReason("Hết mã");
					productDiscounts.add(productDiscountDTO);	
				}
				productDiscountDTO.setDiscount(discount);
				productDiscountDTO.setAble(true); 
				productDiscounts.add(productDiscountDTO);	
			}
			else if (discount.getMemberType().equalsIgnoreCase("thuong")) {
				if (discount.getQuantity()==0) {
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(false); 
					productDiscountDTO.setReason("Hết mã");
					productDiscounts.add(productDiscountDTO);	
				}
				productDiscountDTO.setDiscount(discount);
				productDiscountDTO.setAble(true); 
				productDiscounts.add(productDiscountDTO);	
			}
			else if (discount.getMemberType().equalsIgnoreCase("truc tiep")) {
				continue;
			}
			else if (discount.getMemberType().equalsIgnoreCase("nganh hang")) {
				DiscountDetail discountDetail = discountDetailRepository.findByProductAndDiscount(product,discount);
				if (discountDetail!=null) {
					if (discount.getQuantity()==0) {
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(false); 
						productDiscountDTO.setReason("Hết mã");
						productDiscounts.add(productDiscountDTO);	
					}
					productDiscountDTO.setDiscount(discount);
					productDiscountDTO.setAble(true); 
					productDiscounts.add(productDiscountDTO);				
				}else {
					continue;
				}
			}
			else if (user == null || user.getIsMember() == 0) {
				productDiscountDTO.setDiscount(discount);
				productDiscountDTO.setAble(false); 
				productDiscountDTO.setReason("Mã chỉ dành cho thành viên");
				productDiscounts.add(productDiscountDTO);
			}
			else {
				if (user.getMemberType().equalsIgnoreCase("đồng")) {
					if (discount.getMemberType().equalsIgnoreCase(user.getMemberType())) {
						if (discount.getQuantity()==0) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("Hết mã");
							productDiscounts.add(productDiscountDTO);	
						}
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(true);
						productDiscountDTO.setReason("Mã đồng");
						productDiscounts.add(productDiscountDTO);
					}else {
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(false); 
						productDiscountDTO.setReason("Bạn cần nâng cấp thành viên "+discount.getMemberType() +" để sử dụng mã này");
						productDiscounts.add(productDiscountDTO);
					}
				}
				
				if (user.getMemberType().equalsIgnoreCase("bạc")) {
					if (discount.getMemberType().equalsIgnoreCase(user.getMemberType()) || discount.getMemberType().equalsIgnoreCase("đồng")) {
						if (discount.getQuantity()==0) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("Hết mã");
							productDiscounts.add(productDiscountDTO);	
						}
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(true);
						productDiscountDTO.setReason("Mã "+discount.getMemberType());
						productDiscounts.add(productDiscountDTO);
					}else {
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(false); 
						productDiscountDTO.setReason("Bạn cần nâng cấp thành viên "+discount.getMemberType() +" để sử dụng mã này");
						productDiscounts.add(productDiscountDTO);
					}
				}
				if (user.getMemberType().equalsIgnoreCase("vàng")) {
						if (discount.getQuantity()==0) {
							productDiscountDTO.setDiscount(discount);
							productDiscountDTO.setAble(false); 
							productDiscountDTO.setReason("Hết mã");
							productDiscounts.add(productDiscountDTO);	
						}
						productDiscountDTO.setDiscount(discount);
						productDiscountDTO.setAble(true);
						productDiscountDTO.setReason("Mã "+discount.getMemberType());
						productDiscounts.add(productDiscountDTO);
				}
			}
		}
		
		return new ProductDetailShowDTO(productShow, colors, sizes, relatedProducts, interactions, productDiscounts);
	}
	
	private ProductShow convertToProductShow(Product product, User user) {
		ProductShow productShow = new ProductShow();
		if (user == null) {		
				DecimalFormat df = new DecimalFormat("#.0");
				productShow.setProduct(product);
				if (interactionRepository.findProductRatingAvg(product.getId()) != null) {
					productShow.setRating(df.format(interactionRepository.findProductRatingAvg(product.getId())));
				}else {
					productShow.setRating("Chưa có đánh giá");
				}
				productShow.setBoughtQuantity(billDetailRepository.findQuantityByProduct(product)==null?0:billDetailRepository.findQuantityByProduct(product));
				productShow.setLike(false);
				productShow.setDecreasePercent(discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), product)==null?0:discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), product));
				productShow.setNewPrice((int) Math.floor((product.getPrice() - product.getPrice()*productShow.getDecreasePercent()/100) / 1000) * 1000);
		}else {
				DecimalFormat df = new DecimalFormat("#.0");
				productShow.setProduct(product);
				if (interactionRepository.findProductRatingAvg(product.getId()) != null) {
					productShow.setRating(df.format(interactionRepository.findProductRatingAvg(product.getId())));
				}else {
					productShow.setRating("Chưa có đánh giá");
				}
				productShow.setBoughtQuantity(billDetailRepository.findQuantityByProduct(product)==null?0:billDetailRepository.findQuantityByProduct(product));
				if (interactionRepository.findByUserAndProductAndLikeStatus(user, product,1)!= null) {
					productShow.setLike(true);
				}else {
					productShow.setLike(false);
				}
				productShow.setLike(false);
				productShow.setDecreasePercent(discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), product)==null?0:discountDetailRepository.findProductMaxDiscountEndDayAfter(new Date(), product));
				productShow.setNewPrice((int) Math.floor((product.getPrice() - product.getPrice()*productShow.getDecreasePercent()/100) / 1000) * 1000);
		}
		return productShow;
	}
}
