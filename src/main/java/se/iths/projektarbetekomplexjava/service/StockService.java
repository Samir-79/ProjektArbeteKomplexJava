package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Publisher;
import se.iths.projektarbetekomplexjava.entity.Stock;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.StockRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final BookRepository bookRepository;

    public StockService(StockRepository stockRepository, BookRepository bookRepository) {
        this.stockRepository = stockRepository;
        this.bookRepository = bookRepository;
    }

    public Stock updateStock(Stock stock) {
        Stock foundStock = stockRepository.findById(stock.getId()).orElseThrow(EntityNotFoundException::new);
        return stockRepository.save(foundStock);
    }

    public Iterable<Stock>  findAllStocks() {
        return stockRepository.findAll();
    }






}
