package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.repository.BookToCartRepository;
import se.iths.projektarbetekomplexjava.repository.CartItemRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final BookToCartRepository bookToCartRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public CartItemService(CartItemRepository cartItemRepository,
                           BookToCartRepository bookToCartRepository,
                           ShoppingCartRepository shoppingCartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.bookToCartRepository = bookToCartRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        cartItem.setSubtotal((double) (cartItem.getBook().getPrice() * cartItem.getQty()));
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    public CartItem removeBookFromCart(CartItem cartItem){
        cartItem.setSubtotal((double) (cartItem.getBook().getPrice() * cartItem.getQty()));
        cartItemRepository.save(cartItem);
        return cartItem;
    }


    public CartItem addBookToCart(Book book, Customer customer, int qty) {

        List<CartItem> cartItemList = findByShoppingCart(customer.getShoppingCart());

        for (CartItem cartItem : cartItemList) {
            if (book.getId() == cartItem.getBook().getId()) {
                cartItem.setQty(cartItem.getQty() + qty);
                cartItem.setSubtotal(cartItem.getSubtotal() + (book.getPrice() * qty));
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();

        cartItem.setShoppingCart(customer.getShoppingCart());
        cartItem.setBook(book);
        cartItem.setQty(qty);
        cartItem.setSubtotal((double) (book.getPrice() * qty));

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

    public void removeCartItem(CartItem cartItem) {
        ShoppingCart shoppingCart= shoppingCartRepository
                                   .findById(cartItem.getShoppingCart().getId())
                                   .orElseThrow(EntityNotFoundException::new);

        shoppingCart.setTotalNumberOfBooks(shoppingCart.getTotalNumberOfBooks()-cartItem.getQty());
        shoppingCart.setGrandTotal(shoppingCart.getGrandTotal() - cartItem.getSubtotal());
        bookToCartRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }
}
