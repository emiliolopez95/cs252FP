package com.URide;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.URide.Database;

@Controller
public class SignUpController {
	private final Database database;
	private final HttpServletRequest request;
	
	@Autowired
	public SignUpController(Database database, HttpServletRequest request) {
		this.request = request;
		this.database = database;
	}
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String login(Model model, @ModelAttribute User user, HttpSession session){
    	//database.saveUser(user);
    	
        return "signup";
    }
	
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signin(Model model, @ModelAttribute User user, HttpSession session){
    	try{
    		database.saveUser(user);
    	} catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
    	
        return "index";
    }
}