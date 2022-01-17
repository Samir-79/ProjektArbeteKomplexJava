package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Publisher;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.AuthorRepository;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.PublisherRepository;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Publisher updatePublisher(Publisher publisher) {
       findByPublisherId(publisher.getId());
        return publisherRepository.save(publisher);
    }

    public Publisher getPublisherByName(String name) {
        Publisher foundPublisher = publisherRepository.findByName(name);
        if (foundPublisher.getName() == null) {
            throw new NotFoundException("Book not found!");
        }
        return publisherRepository.findByName(foundPublisher.getName());
    }

    public Publisher findByPublisherId(Long id) {
        Publisher foundPublisher = publisherRepository.findById(id).orElseThrow(()->new NotFoundException("Publisher not found"));
        return foundPublisher;

    }

    public Iterable<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }
}