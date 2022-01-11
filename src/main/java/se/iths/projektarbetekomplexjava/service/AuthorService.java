package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Author;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.AuthorRepository;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.PublisherRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAuthorByFirstName(String firstName) {
        List<Author> foundAuthor = authorRepository.findByFirstName(firstName);
        if (foundAuthor.isEmpty()) {
            throw new NotFoundException("No authors found!");
        }
        return authorRepository.findByFirstName(firstName);
    }

    public List<Author> getAuthorByLastName(String lastName) {
        List<Author> foundAuthor = authorRepository.findByLastName(lastName);
        if (foundAuthor.isEmpty()) {
            throw new NotFoundException("No authors found!");
        }
        return authorRepository.findByLastName(lastName);
    }

    public Author getAuthorByFullName(String firstName, String lastName) {
        Author foundAuthor = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (foundAuthor == null) {
            throw new NotFoundException("Author not found!");
        }
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void removeAuthor(Long id) {
        Author foundAuthor = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        authorRepository.deleteById(foundAuthor.getId());
    }
}