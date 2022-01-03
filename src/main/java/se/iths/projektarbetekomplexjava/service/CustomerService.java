package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.email.EmailValidator;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.jms.Receiver;
import se.iths.projektarbetekomplexjava.jms.Sender;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import se.iths.projektarbetekomplexjava.security.PasswordEncoder;
import se.iths.projektarbetekomplexjava.security.PasswordValidator;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final EmailValidator emailValidator;

    public CustomerService(CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, ShoppingCartRepository shoppingCartRepository,
                           BookRepository bookRepository, PasswordEncoder passwordEncoder, EmailValidator emailValidator) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailValidator = emailValidator;
    }

    public Customer addCustomer(Customer customer) {
        boolean isValidEmail = emailValidator.test(customer.getEmail());
        List<Customer> customerList = getByEmail(customer.getEmail());
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty() || customer.getPassword().isEmpty()){
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        }
        else if (!isValidEmail){
            throw new BadRequestException("Email: " + customer.getEmail() + " is not valid");
        }
        for (Customer customers:customerList){
            if(passwordEncoder.bCryptPasswordEncoder().matches(customer.getPassword(), customers.getPassword())){
                throw new BadRequestException("Password: " + customer.getPassword() + " is already taken.");
            }
            else if(customer.getUsername().equals(customers.getUsername())){
                throw new BadRequestException("Username: " + customer.getUsername() + " is already taken.");
            }
            else if(customer.getEmail().equals(customers.getEmail())){
                throw new BadRequestException("Email: " + customer.getEmail() + " is already taken.");
            }
        }
        if(PasswordValidator.isValidPassword(customer.getPassword())){
            customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        }
        else {
            throw new BadRequestException("""
                    Password must fulfill the password requirement: It contains at least 8 characters and at most 20 characters.
                    It contains at least one digit.
                    It contains at least one upper case alphabet.
                    It contains at least one lower case alphabet.
                    It contains at least one special character which includes !@#$%&*()-+=^.
                    It does’t contain any white space.""");
        }
        customer.setRole(Role.USER);
        customer.addShoppingCart(customer.getShoppingCart());
        try {
            Sender.sender(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getUsername(),
                    customer.getAddress(), customer.getPhone(), customer.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerRepository.save(customer);
    }

    public void removeCustomer(Long id) {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        customerRepository.deleteById(foundCustomer.getId());
    }

    public List<Customer> getByEmail(String email) {
        return customerRepository.findCustomersByEmail(email);
    }

    public Customer findCustomerByEmail(String email){
        return customerRepository.findCustomerByEmail(email);
    }

    public Optional<Customer> findUserById(Long id) {
        try{
            Receiver.receiver();
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email, String password) {
        return customerRepository.findCustomerByEmailAndPassword(email, password);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 24 hours. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}