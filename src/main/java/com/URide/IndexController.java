package com.URide;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    String index(){
    	User user = new User();
    	user.setName("emilio");
    	user.setEmail("lopez@purdue.edu");
    	user.setPassword("pass123");
    	user.setType(1);
    	database.saveUser(user);
    	
        return "index";
    }
}
