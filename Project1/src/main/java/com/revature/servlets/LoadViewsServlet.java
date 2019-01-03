package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class LoadViewsServlet extends HttpServlet {

	private static final long serialVersionUID = 5788439482292060664L;
	
	private static Logger log = Logger.getLogger(LoadViewsServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//consult service layer for data
		String path = "partials/" + process(req,resp) + ".html";
		log.debug(path);
		req.getRequestDispatcher(path).forward(req, resp);
	}
	
	static String process(HttpServletRequest req, HttpServletResponse resp) throws JsonParseException, JsonMappingException, IOException { 
		log.info("LOAD VIEW REQUEST SENT TO: " + req.getRequestURI());
		
		switch(req.getRequestURI()) {
		case "/Project1/login.view" :
			return "login";
		case "/Project1/error-login.view" :
			return "error-login";
		case "/Project1/employee.view" :
			return "employee";
		case "/Project1/manager.view" :
			return "manager";
		case "/Project1/reimb.view" :
			return "reimb";
		case "/Project1/past.view" :
			return "past";
		case "/Project1/pending.view" :
			return "pending";
		case "/Project1/old.view" :
			return "old";
		case "/Project1/register.view" :
			return "register";
		case "/Project1/empnav.view" :
			return "empnav";
		case "/Project1/mannav.view" :
			return "mannav";
		case "/Project1/emptynav.view" :
			return "emptynav";
		}
		return null;
	}

}
