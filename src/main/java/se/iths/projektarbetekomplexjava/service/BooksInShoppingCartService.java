//package se.iths.projektarbetekomplexjava.service;
//
//
//import org.springframework.stereotype.Service;
//import se.iths.projektarbetekomplexjava.entity.Book;
//import se.iths.projektarbetekomplexjava.entity.BooksInShoppingCart;
//import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
//import se.iths.projektarbetekomplexjava.repository.*;
//
//import javax.persistence.EntityNotFoundException;
//
//
//@Service
//public class BooksInShoppingCartService {
//
//    private BooksInShoppingCartRepository booksInShoppingCartRepository;
//    private ShoppingCartRepository shoppingCartRepository;
//    private BookRepository bookRepository;
//    private CustomerRepository customerRepository;
//    private OrderRepository orderRepository;
//    private BookService bookService;
//    private ShoppingCartService shoppingCartService;
//
//
//
//
//
//
//    public BooksInShoppingCartService(BooksInShoppingCartRepository booksInShoppingCartRepository, ShoppingCartRepository shoppingCartRepository, BookRepository bookRepository, CustomerRepository customerRepository, OrderRepository orderRepository, BookService bookService, ShoppingCartService shoppingCartService) {
//        this.booksInShoppingCartRepository = booksInShoppingCartRepository;
//        this.shoppingCartRepository = shoppingCartRepository;
//        this.bookRepository = bookRepository;
//        this.customerRepository = customerRepository;
//        this.orderRepository = orderRepository;
//        this.bookService = bookService;
//        this.shoppingCartService = shoppingCartService;
//
//    }
//
//
//
//    public BooksInShoppingCart addBookToBooksInShoppingCart(Long bookId, BooksInShoppingCart booksInShoppingCart) {
//     //BooksInShoppingCart foundBooksInShoppingCart = booksInShoppingCartRepository.findById(booksInShoppingCart.getId()).orElseThrow(EntityNotFoundException::new);
//     ShoppingCart foundShoppingCart = shoppingCartRepository.findById(booksInShoppingCart.getShoppingCart().getId()).orElseThrow(EntityNotFoundException::new);
//        Book foundBook = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
//        if(checkIfBookInShoppingCart(foundBook,foundShoppingCart)) {
//            BooksInShoppingCart foundBooksInShoppingCart = booksInShoppingCartRepository.findByIsbn13(foundBook.getISBN13());
//            booksInShoppingCart.setId(foundBooksInShoppingCart.getId());
//            booksInShoppingCart.setIsbn13(foundBook.getISBN13());
//            booksInShoppingCart.setNumberOfCopies(booksInShoppingCart.getNumberOfCopies() + foundBooksInShoppingCart.getNumberOfCopies());
//            booksInShoppingCart.setPriceCopies(foundBook.getPrice() * (long) (booksInShoppingCart.getNumberOfCopies()) + foundBooksInShoppingCart.getPriceCopies());
//            booksInShoppingCart.setShoppingCart(foundShoppingCart);
//        }
//        else{
//            booksInShoppingCart.setIsbn13(foundBook.getISBN13());
//           booksInShoppingCart.setNumberOfCopies(booksInShoppingCart.getNumberOfCopies());
//            booksInShoppingCart.setPriceCopies(foundBook.getPrice() * (long)(booksInShoppingCart.getNumberOfCopies()));
//        }
//
//        //foundBooksInShoppingCart.getShoppingCart().addBook(foundBook);
//        shoppingCartService.addBookToShoppingCart(foundBook.getId(),foundShoppingCart);
//
//        return  booksInShoppingCartRepository.save(booksInShoppingCart);
//
// }
//
//    public boolean checkIfBookInShoppingCart(Book book, ShoppingCart foundShoppingCart){
//        for (Book book1 : foundShoppingCart.getBooks()) {
//            if (book1.getISBN13().equals(book.getISBN13())) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
