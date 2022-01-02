package se.iths.projektarbetekomplexjava.security;

import org.springframework.context.annotation.Configuration;

import java.util.regex.*;

public class PasswordValidator {

    public static boolean isValidPassword(String password){

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        return m.matches();
    }

    public static void main(String args[])
    {

        // Test Case 1:
        String str1 = "Geeks@portal20";
        System.out.println(isValidPassword(str1));

        // Test Case 2:
        String str2 = "Geeksforgeeks";
        System.out.println(isValidPassword(str2));

        // Test Case 3:
        String str3 = "Geeks@ portal9";
        System.out.println(isValidPassword(str3));

        // Test Case 4:
        String str4 = "1234";
        System.out.println(isValidPassword(str4));

        // Test Case 5:
        String str5 = "Gfg@20";
        System.out.println(isValidPassword(str5));

        // Test Case 6:
        String str6 = "geeks@portal20";
        System.out.println(isValidPassword(str6));
    }
}