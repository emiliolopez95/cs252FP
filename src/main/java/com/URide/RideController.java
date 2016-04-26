package com.URide;

import java.text.ParseException;
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
    
    @RequestMapping("/ride/all")
    public String allRides(Model model, HttpSession session) throws ParseException{
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	User user = (User)session.getAttribute("sessionUser");
    	List<Ride> listRides = database.findRidesAllRides();
    	
    	Ride rideTest = new Ride();
    	Date nowDate = new Date();
    	int i = 1;
    	for(Ride ride: listRides){
    		System.out.println(i + ") rides: " + ride.getDate());
    		i ++;
    	}
    	listRides = rideTest.getRidesAfterDate(listRides, nowDate);
    	listRides = rideTest.sortRidesByDate(listRides);
    	
    	model.addAttribute("suser", user);
    	model.addAttribute("rides", listRides);
    	    
        return "allRides";
    }
    @RequestMapping(value = "/ride/create", method = RequestMethod.GET)
    public String newRide(Model model, HttpSession session){
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	model.addAttribute("suser",(User) session.getAttribute("sessionUser"));
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
    	System.out.println("type: " + sUser.getType());
    	if(sUser.getType() == 2){
    		ride.setDId(sUser.getId());
    	}
    	else if(sUser.getType() == 1){
    		ride.getrIds().add(sUser.getId());
    	}
    	ride.setDName(sUser.getName());
    	ride.setCreatedDate(crtDate);
    	database.saveRide(ride);
    	//model.addAttribute("ride", new Ride());
    	model.addAttribute("suser", sUser);
    	return "redirect:/";
    }
    
    @RequestMapping("/ride/{id}")
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
    	if(sUser.getType() == 1){
    		ride.getrIds().add(sUser.getId());
    	}
    	else if(sUser.getType() ==2){
    		ride.setDId(sUser.getId());
    	}
    	database.saveRide(ride);
    	model.addAttribute("ride", new Ride());
    	model.addAttribute("suser", sUser);
    	return "redirect:/user";
    }
    
    @RequestMapping(value = "/ride/leave/{id}")
    public String leaveRide(Model model, @PathVariable Long id, HttpSession session){
    	if(session.getAttribute("sessionUser") == null) {
    		return "redirect:/";
    	}
    	User sUser = (User)session.getAttribute("sessionUser");
    	Ride ride = database.findRideById(id);
    	ride.getrIds().remove(sUser.getId());
    	if(ride.getDId() == sUser.getId()){
    		ride.setDId(null);
    	}
    	database.saveRide(ride);
    	model.addAttribute("ride", new Ride());
    	model.addAttribute("suser", sUser);
    	return "redirect:/user";
    }
}

