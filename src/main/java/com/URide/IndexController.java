package com.URide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.URide.Database;

@Controller
public class IndexController {
	private final Database database;
	private final HttpServletRequest request;
	
	@Autowired
	public IndexController(Database database, HttpServletRequest request) {
		this.request = request;
		this.database = database;
	}
    @RequestMapping("/")
    String index(Model model, HttpSession session){
    	model.addAttribute("user", new User());
    	
    	Driver drive = database.findDriverByName("nico");
    	
    	System.out.println("id: " + drive.getEmail());
    	
    	
        return "index";
    }
}
