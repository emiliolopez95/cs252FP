package com.URide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.URide.Database;

@Controller
@SessionAttributes("sessionUser")
public class RideController {
	private final Database database;
	private final HttpServletRequest request;
	
	@Autowired
	public RideController(Database database, HttpServletRequest request) {
		this.request = request;
		this.database = database;
	}
	@RequestMapping("/ride")
    public String ride(Model model, HttpSession session){
		if(session.getAttribute("sessionUser") == null) {
			return "redirect:/";
    	}
		User suser = (User)session.getAttribute("sessionUser");
		model.addAttribute("suser", suser);
		model.addAttribute("ride", new Ride());
    	    	
        return "rides";
    }
	
    @RequestMapping("/ride/find")
    public String findRide(Model model, @ModelAttribute Ride ride, HttpSession session){
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	User user = (User)session.getAttribute("sessionUser");
    	List<Ride> listRides = database.findRidesByDateIPointAndFPoint(ride.getDate(), ride.getInitialPoint(), ride.getFinalPoint());
    	model.addAttribute("iPoint", ride.getInitialPoint());
    	model.addAttribute("fPoint", ride.getFinalPoint());
    	model.addAttribute("suser", user);
    	model.addAttribute("rides", listRides);
    	    
        return "findRides";
    }
    
    @RequestMapping(value = "/ride/create", method = RequestMethod.GET)
    public String newRide(Model model, HttpSession session){
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	model.addAttribute("ride", new Ride());
    	return "createRide";
    }
    @RequestMapping(value = "/ride/create", method = RequestMethod.POST)
    public String createRide(Model model, @ModelAttribute Ride ride, HttpSession session){
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	Date crtDate = new Date();
    	User sUser = (User) session.getAttribute("sessionUser");
    	List<Long> rIds = new ArrayList<>();
    	ride.setrIds(rIds);
    	ride.setDId(sUser.getId());
    	ride.setDName(sUser.getName());
    	ride.setCreatedDate(crtDate);
    	database.saveRide(ride);
    	//model.addAttribute("ride", new Ride());
    	return "redirect:/";
    }
    @RequestMapping(value = "/ride/{id}", method = RequestMethod.GET)
    public String ride(Model model, @PathVariable Long id, @ModelAttribute Ride ride, HttpSession session){
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	User sUser = (User)session.getAttribute("sessionUser");
    	System.out.println("userNameride: " + sUser.getName());
    	Ride ride1 = database.findRideById(id);
    	
    	model.addAttribute("ride", ride1);
    	model.addAttribute("suser", sUser);
    	System.out.println("Rideid: " + id);
    	return "ride";
    }
    
    @RequestMapping(value = "/ride/join/{id}")
    public String joinRide(Model model, @PathVariable Long id, HttpSession session){
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	User sUser = (User)session.getAttribute("sessionUser");
    	Ride ride = database.findRideById(id);
    	ride.getrIds().add(sUser.getId());
    	database.saveRide(ride);
    	model.addAttribute("ride", new Ride());
    	model.addAttribute("suser", sUser);
    	return "ride";
    }
}

