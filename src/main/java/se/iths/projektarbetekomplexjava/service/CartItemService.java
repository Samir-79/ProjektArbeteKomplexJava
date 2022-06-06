package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.BookToCartRepository;
import se.iths.projektarbetekomplexjava.repository.CartItemRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
        return cartItemRepository.findByShoppingCart(Optional.ofNullable(shoppingCart));
    }

    public CartItem updateCartItem(CartItem cartItem) {
        cartItem.setSubtotal((double) (cartItem.getBook().getPrice() * cartItem.getQty()));
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    public CartItem removeBookFromCart(CartItem cartItem) {
        cartItem.setSubtotal((double) (cartItem.getBook().getPrice() * cartItem.getQty()));
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    public CartItem addBookToCart(Book book, Optional<Customer> customer, int qty) {
        List<CartItem> cartItemList = findByShoppingCart(customer.get().getShoppingCart());

        for (CartItem cartItem : cartItemList) {
            if (book.getId() == cartItem.getBook().getId()) {
                if (qty > 10) {
                    throw new BadRequestException("Number of copies should not exceed 10!");
                }

                cartItem.setQty( qty);

                cartItem.setSubtotal(book.getPrice() * qty);
                cartItem.setBookIsbn(cartItem.getBookIsbn());
                cartItem.setBookTitle(cartItem.getBookTitle());
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();

        cartItem.setShoppingCart(customer.get().getShoppingCart());
        cartItem.setBook(book);
        if (qty > 10) {
            throw new BadRequestException("Number of copies should not exceed 10!");
        }
        cartItem.setQty(qty);

        cartItem.setBookIsbn(book.getISBN13());
        cartItem.setBookTitle(book.getTitle());
        cartItem.setSubtotal((double) (book.getPrice() * qty));

        cartItem = cartItemRepository.save(cartItem);

        BookToCart bookToCart = new BookToCart();
        bookToCart.setBook(book);
        bookToCart.setCartItem(cartItem);
        bookToCartRepository.save(bookToCart);

        return cartItem;
    }

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart with id: " + id + " not found"));
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void removeCartItem(CartItem cartItem) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findById(cartItem.getShoppingCart().getId())
                .orElseThrow(EntityNotFoundException::new);

        shoppingCart.setTotalNumberOfBooks(shoppingCart.getTotalNumberOfBooks() - cartItem.getQty());
        shoppingCart.setGrandTotal(shoppingCart.getGrandTotal() - cartItem.getSubtotal());
        bookToCartRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    public List<CartItem> getCartItemList(Long shoppingCartId) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(shoppingCartId);
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }
}