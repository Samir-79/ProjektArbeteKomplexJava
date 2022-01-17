package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Publisher;
import se.iths.projektarbetekomplexjava.service.PublisherService;


@RestController
@RequestMapping("bokhandel/api/v1/publisher/")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PutMapping("/updatepublisher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Publisher> updatePublisher(@RequestBody Publisher publisher) {
        Publisher updatePublisher = publisherService.updatePublisher(publisher);
        return new ResponseEntity<>(updatePublisher, HttpStatus.OK);
    }

    @GetMapping("/getlistofpublishers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Publisher>> getAllPublishers() {
        Iterable<Publisher> allPublishers = publisherService.findAllPublishers();
        return new ResponseEntity<>(allPublishers, HttpStatus.OK);
    }

    @GetMapping("/getpublisherbyid/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        Publisher publisherById = publisherService.findByPublisherId(id);
        return new ResponseEntity<>(publisherById, HttpStatus.OK);
    }

    @GetMapping("/getpublisherbyname/{name}")
    public ResponseEntity<Publisher> getPublisherByName(@PathVariable String name) {
        Publisher publisherByName = publisherService.getPublisherByName(name);
        return new ResponseEntity<>(publisherByName, HttpStatus.OK);
    }
}