package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Stock;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.StockRepository;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final BookRepository bookRepository;

    public StockService(StockRepository stockRepository, BookRepository bookRepository) {
        this.stockRepository = stockRepository;
        this.bookRepository = bookRepository;
    }

    public Stock updateStock(Stock stock) {
        Stock foundStock = findStockById(stock.getId());
        foundStock.setQuantity(stock.getQuantity());
        foundStock.setInStock(stock.isInStock());
        return stockRepository.save(foundStock);
    }

    public Stock findStockById(Long id) {
        Stock foundStock = stockRepository.findById(id).orElseThrow(() -> new NotFoundException("stock not found"));
        return foundStock;
    }

    public Iterable<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    public void removeStock(Long id) {
        Stock foundStock = findStockById(id);
        stockRepository.deleteById(foundStock.getId());
    }
}