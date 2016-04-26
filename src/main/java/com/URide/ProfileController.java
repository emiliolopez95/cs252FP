package com.URide;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.URide.Database;

@Controller
@SessionAttributes("sessionUser")
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
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
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
    
    @RequestMapping("/user")
    public String userProfile(Model model, HttpSession session) throws ParseException {
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	User user = (User) session.getAttribute("sessionUser");
    	List<Ride> userRides = database.findRidesByUser(user);
    	Ride testRide = new Ride();
    	userRides = testRide.sortRidesByDate(userRides);
    	model.addAttribute("rides", userRides);
    	model.addAttribute("suser", user);
    	return "profile";
    }
}
