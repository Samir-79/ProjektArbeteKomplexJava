package se.iths.projektarbetekomplexjava.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        //we can filter any email that we do not want to be created in our database.
        return s.endsWith("@gmail.com") || s.endsWith("@yahoo.com") || s.endsWith("@hotmail.com")
                || s.endsWith("@bokhandel.se");
    }
}