package webplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import webplatform.config.CustomUserDetails;
import webplatform.entity.User;
import webplatform.service.AccountService;
import webplatform.service.AuthorityService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AuthorityService authorityService;

    @Autowired
    public AccountController(AccountService accountService, AuthorityService authorityService) {
        this.accountService = accountService;
        this.authorityService = authorityService;
    }

    //main page, lists users, if admin logged in, don't make a row for him,
    //so he wont get confused while managing employees accounts
    @GetMapping("/users")
    public String getUserList(Model model){
        List<User> users = accountService.getAllUsers();
        model.addAttribute("userList", users);
        return "listUsers";
    }

    @GetMapping("/register")
    public String register(Model model){
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        return "register";
    }

    @GetMapping("/showLoginPage")
    public String login(){
        return "login";
    }

    //update user if you are an admin, different options, update for admin form
    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("userId") int userId, Model model){
        User user = accountService.getUser(userId);
        model.addAttribute("newUser", user);
        return "updateByAdmin";
    }

    //gets the currently logged in session user and forwards him to the update for for users
    @GetMapping("/updateLoggedUser")
    public String updateLoggedUser(Model model){
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = accountService.getUser(userDetails.getId());
        model.addAttribute("newUser", user);
        return "updateLoggedUser";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult){
        System.out.println("I am in the controller for createUser "+user.toString());
        if(bindingResult.hasErrors()){ //check is the fields were filled incorrectly
            return "register";//yes, they were, loop and the print the errors
        }
        else{
            accountService.createUser(user);
            return "redirect:users";
        }
    }

    //checks security features:
    // admin cannot delete himself but can delete anyone else,
    // user can only delete himself
    @GetMapping(value = "/deleteUser", params = "userId")
    public String deleteUser(HttpServletRequest request, @RequestParam("userId") int theId){
        if(request.isUserInRole("ROLE_ADMIN")){ //is the logged user admin
            User user = accountService.getUser(theId);
            if(user.getGrantedAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){  //is he trying to delete admin
                return "redirect:users"; //yes, admin trying to delete admin, redirect him
                }
            accountService.deleteUser(theId); //the admin is not deleting himself, allow him to delete anyone else he wants
            return "redirect:users";
        }
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails.getId() == theId){// is the logged user authorized to delete the account he wants to
            accountService.deleteUser(theId); //yes, he can delete himself, allow him
            new SecurityContextLogoutHandler().logout(request, null, null);
            return "redirect:users"; //no, he is not, return him to main page
        }else return "redirect:users";
    }

    @PostMapping(value = "/search", params = "searchPhrase")
    public String searchUsers(@RequestParam("searchPhrase") String searchPhrase, Model model){
        List<User> users = accountService.searchUsers(searchPhrase);
        model.addAttribute("userList", users);
        return "listUsers";
    }

    //well a test mapping
    @RequestMapping("/testing")
    public String test(){
        return "test";
    }

    //for validation purposes
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    //loads all the possible Authorities in the authorities_table to model
    @ModelAttribute(name = "existingAuthorities")
    public List<String> getExistingAuthorities(){
        List<String> authoritiesInString = new ArrayList<>();
        authorityService.getExistingAuthorities()
                .stream()
                .filter(auth -> !auth.getAuthority().equals("ROLE_ADMIN"))
                .forEach(auth -> authoritiesInString.add(auth.getAuthority()));
        return authoritiesInString;
    }

}
