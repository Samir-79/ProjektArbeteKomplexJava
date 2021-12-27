package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.repository.BookToCartRepository;
import se.iths.projektarbetekomplexjava.repository.CartItemRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final BookToCartRepository bookToCartRepository;

    public CartItemService(CartItemRepository cartItemRepository, BookToCartRepository bookToCartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.bookToCartRepository = bookToCartRepository;
    }

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }
    public CartItem updateCartItem(CartItem cartItem) {
        cartItem.setSubtotal((double) (cartItem.getBook().getPrice()* cartItem.getQty()));

        cartItemRepository.save(cartItem);

        return cartItem;
    }

    public CartItem addBookToCart(Book book, Customer customer, int qty) {

        List<CartItem> cartItemList = findByShoppingCart(customer.getShoppingCart());

        for(CartItem cartItem : cartItemList) {
            if(book.getId() == cartItem.getBook().getId()) {
                cartItem.setQty(cartItem.getQty() + qty);
                cartItem.setSubtotal((double) (book.getPrice() * qty));
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();

        cartItem.setShoppingCart(customer.getShoppingCart());
        cartItem.setBook(book);
        cartItem.setQty(qty);
        cartItem.setSubtotal((double) (book.getPrice()* qty));

        cartItem = cartItemRepository.save(cartItem);

        BookToCart bookToCart = new BookToCart();
        bookToCart.setBook(book);
        bookToCart.setCartItem(cartItem);
        bookToCartRepository.save(bookToCart);

        return cartItem;
    }

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
}
