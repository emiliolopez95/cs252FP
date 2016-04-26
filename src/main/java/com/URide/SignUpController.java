package com.URide;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.URide.Database;

@Controller
@SessionAttributes("sessionUser")
public class SignUpController {
	private final Database database;
	private final HttpServletRequest request;
	
	@Autowired
	public SignUpController(Database database, HttpServletRequest request) {
		this.request = request;
		this.database = database;
	}
	//GET for href
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String login(Model model, @ModelAttribute User user, HttpSession session){
    	//database.saveUser(user);
		if(session.getAttribute("sessionUser") != null) {
			return "redirect:/";
    	}
        return "signup";
    }
	
	//POST for thymeleaf
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signin(Model model, @ModelAttribute @Valid User user, BindingResult bindingResult, HttpSession session){
    	if(session.getAttribute("sessionUser") != null) {
    		return "redirect:/";
    	}
    	/*if(bindingResult.hasErrors()){
    		return "signup";
    	}*/
    	if(user.getType() == 1){
    		Rider rider = new Rider(user.getName(),user.getLastName(), user.getPassword(),
    				user.getEmail(), user.getType());
    		database.saveRider(rider);
    	}
    	else if(user.getType() ==2){
    		Driver driver = new Driver(user.getName(),user.getLastName(), user.getPassword(),
    				user.getEmail(), user.getType());
    		database.saveDriver(driver);
    	}
    	
        return "redirect:/";
    }
}