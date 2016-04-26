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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.URide.Database;

@Controller
@SessionAttributes("sessionUser")
public class SignInController {
	private final Database database;
	private final HttpServletRequest request;
	
	@Autowired
	public SignInController(Database database, HttpServletRequest request) {
		this.request = request;
		this.database = database;
	}
    @RequestMapping(value ="/signin", method = RequestMethod.GET)
    public String loginButton(Model model,@ModelAttribute User user, HttpSession session){
    	if(session.getAttribute("sessionUser") != null) {
    		return "redirect:/";
    	}
        return "signin";
    }
    @RequestMapping("/signin")
    public String login(Model model, @ModelAttribute User u, HttpSession session, final RedirectAttributes redirectAttributes){
    	if(session.getAttribute("sessionUser") != null) {
    		return "redirect:/";
    	}
    	User user;
    	try {
    		user = database.findUserByEmail(u.getEmail());
    	} catch (DataAccessException e) {
			// throw 404
			user = null;
		}
    	if (user == null || !user.getPassword().equals(u.getPassword())){
    		redirectAttributes.addFlashAttribute("errorMessage",
					"Username/Password does not match or could not be found");
    		return"redirect:/signin";
    	}
    	session.setAttribute("sessionUser", user);
    	return "redirect:/";
    }
}
