package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Author;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.AuthorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/getauthorbyid/{id}")
    public ResponseEntity<Optional<Author>> getById(@PathVariable long id) {
        Optional<Author> authorId = authorService.findAuthorById(id);
        if (authorId.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(authorId, HttpStatus.OK);
    }

    @GetMapping("/getauthorbyfirstname/{firstName}")
    public ResponseEntity<List<Author>> getByFirstname(@PathVariable String firstName) {
        List<Author> authorsByFirstName = authorService.getAuthorByFirstName(firstName);
        return new ResponseEntity<>(authorsByFirstName, HttpStatus.OK);
    }

    @GetMapping("/getauthorbylastname/{lastName}")
    public ResponseEntity<List<Author>> getByLastname(@PathVariable String lastName) {
        List<Author> authorsByLastName = authorService.getAuthorByLastName(lastName);
        return new ResponseEntity<>(authorsByLastName, HttpStatus.OK);
    }

    @GetMapping("/getauthorbyfullname/{firstName}/{lastName}")
    public ResponseEntity<Author> getByFullName(@PathVariable String firstName, @PathVariable String lastName) {
        Author authorByFullName = authorService.getAuthorByFullName(firstName, lastName);
        return new ResponseEntity<>(authorByFullName, HttpStatus.OK);
    }

    @PutMapping("/updateauthor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        Author updateAuthor = authorService.updateAuthor(author);
        return new ResponseEntity<>(updateAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/deleteauthor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        Optional<Author> authorId = authorService.findAuthorById(id);
        if (authorId.isEmpty()){
            throw new NotFoundException("No data available of author ID: " + id);
        }
        authorService.removeAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}