package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pojos.User;
import com.revature.service.UserService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	static UserService uService = new UserService();

	private static Logger log = Logger.getLogger(RegisterServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// login functionality here:
		
		ObjectMapper usrmap = new ObjectMapper();
		User u = usrmap.readValue(req.getInputStream(), User.class);
		
		log.info(u);
		
		if (uService.findByUsername(u.getUsername()).getId()==0) {
			u.setRoleID(1);
			uService.insertUser(u);
		}

	}

}
