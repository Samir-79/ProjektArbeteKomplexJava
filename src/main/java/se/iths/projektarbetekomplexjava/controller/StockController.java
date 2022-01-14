package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Stock;
import se.iths.projektarbetekomplexjava.service.StockService;

@RestController
@RequestMapping("bokhandel/api/v1/stock")
public class StockController {


    private  final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PutMapping("/updatestock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> updateStock(@RequestBody Stock stock) {
        Stock updateStock = stockService.updateStock(stock);
        return new ResponseEntity<>(updateStock, HttpStatus.OK);
    }


    @GetMapping("/getstockbyid/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stockById = stockService.findStockById(id);
        return new ResponseEntity<>(stockById, HttpStatus.OK);
    }


    @GetMapping("/getAllStocks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Stock>> getAllStocks() {
        Iterable<Stock> allStocks = stockService.findAllStocks();
        return new ResponseEntity<>(allStocks, HttpStatus.OK);
    }


    @DeleteMapping("/removeStock/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeStock(@PathVariable Long id) {
        stockService.removeStock(id);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }


}
