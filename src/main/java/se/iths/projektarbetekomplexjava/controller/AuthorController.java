package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Author;
import se.iths.projektarbetekomplexjava.service.AuthorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/getauthorbyid")
    public ResponseEntity<Optional<Author>> getById(@PathVariable Long id) {
        Optional<Author> authorId = authorService.findAuthorById(id);
        return new ResponseEntity<>(authorId, HttpStatus.OK);
    }

    @GetMapping("/getauthorbyfirstname")
    public ResponseEntity<List<Author>> getByFirstname(@PathVariable String firstName) {
        List<Author> authorsByFirstName = authorService.getAuthorByFirstName(firstName);
        return new ResponseEntity<>(authorsByFirstName, HttpStatus.OK);
    }

    @GetMapping("/getauthorbylastname")
    public ResponseEntity<List<Author>> getByLastname(@PathVariable String lastName) {
        List<Author> authorsByLastName = authorService.getAuthorByLastName(lastName);
        return new ResponseEntity<>(authorsByLastName, HttpStatus.OK);
    }

    @GetMapping("/getauthorbyfullname")
    public ResponseEntity<Author> getByFullName(@PathVariable String firstName, String lastName) {
        Author authorByFullName = authorService.getAuthorByFullName(firstName, lastName);
        return new ResponseEntity<>(authorByFullName, HttpStatus.OK);
    }

    @PutMapping("/updateauthor")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        Author updateAuthor = authorService.updateAuthor(author);
        return new ResponseEntity<>(updateAuthor, HttpStatus.OK);
    }


}
