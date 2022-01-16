package se.iths.projektarbetekomplexjava.security;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return s.endsWith("@gmail.com") || s.endsWith("@yahoo.com") || s.endsWith("@hotmail.com")
                || s.endsWith("@bokhandel.se");
    }
}