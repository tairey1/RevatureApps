package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;
import com.revature.service.ReimbursementService;
import com.revature.service.UserService;

@WebServlet("/past")
public class ViewPastServlet extends HttpServlet{

	static ReimbursementService rService = new ReimbursementService();
	static UserService uService = new UserService();
	private static Logger log = Logger.getLogger(ViewPastServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//consult service layer for data
		HttpSession session=req.getSession(false);
		System.out.println(session.getAttribute("user"));
		User u = (User) session.getAttribute("user");
		
		List<Reimbursement> reimbs = rService.getUserReimbs(u);
		
		//convert to JSON
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(reimbs);
		log.trace("FINDING ALL REIMBS. JSON: " + json);
		
		//send response
		PrintWriter writer = resp.getWriter();
		resp.setContentType("/application/json");
		writer.write(json);
		
	}
	
}