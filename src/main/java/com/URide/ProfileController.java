package com.URide;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.URide.Database;

@Controller
public class ProfileController {
	private final Database database;
	private final HttpServletRequest request;
	
	@Autowired
	public ProfileController(Database database, HttpServletRequest request) {
		this.request = request;
		this.database = database;
	}
    @RequestMapping("/login")
    public String login(Model model, @ModelAttribute User u, HttpSession session){
    	User user;
    	try {
    		user = database.findUserByName(u.getName());
    	} catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
    	
    	if(user.getPassword().equals(u.getPassword())) {
    		return "redirect:/user/" + user.getId(); 
    	}
    	
        return "index";
    }
    
    @RequestMapping("/user/{uId}")
    public String user(Model model, @PathVariable Long uId, HttpSession session) {
    	User user;
    	try {
    		user = database.findUserById(uId);
    	}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
    	model.addAttribute("user", user);
    	return "profile.html";
    }
}
