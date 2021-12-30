package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.Publisher;
import se.iths.projektarbetekomplexjava.service.PublisherService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PutMapping("/updatepublisher")
    public ResponseEntity<Publisher> updatePublisher(@RequestBody Publisher publisher) {
        Publisher updatePublisher = publisherService.updatePublisher(publisher);
        return new ResponseEntity<>(updatePublisher, HttpStatus.OK);
    }

    @GetMapping("/getlistofpublishers")
    public ResponseEntity<Iterable<Publisher>> getAllPublishers() {
        Iterable<Publisher> allPublishers = publisherService.findAllPublishers();
        return new ResponseEntity<>(allPublishers, HttpStatus.OK);
    }

    @GetMapping("/getpublisherbyid")
    public ResponseEntity<Optional<Publisher>> getPublisherById(@PathVariable Long id) {
        Optional<Publisher> publisherById = publisherService.findByPublisherId(id);
        return new ResponseEntity<>(publisherById, HttpStatus.OK);
    }

    @GetMapping("/getpublisherbyname")
    public ResponseEntity<Publisher> getPublisherByName(@PathVariable String name) {
        Publisher publisherByName = publisherService.getPublisherByName(name);
        return new ResponseEntity<>(publisherByName, HttpStatus.OK);
    }
}