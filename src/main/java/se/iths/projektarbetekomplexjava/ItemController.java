package se.iths.projektarbetekomplexjava;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping()
    public ResponseEntity<ItemEntity> createItem(@RequestBody ItemEntity itemEntity) {
        ItemEntity createdItem = itemService.createItem(itemEntity);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Iterable<ItemEntity>> findAllItems() {
        Iterable<ItemEntity> allItems = itemService.findAllItems();
        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }
}
