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

@WebServlet("/reimb")
public class FileReimbServlet extends HttpServlet{

	static ReimbursementService rService = new ReimbursementService();
	private static Logger log = Logger.getLogger(FileReimbServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession(false);
		System.out.println(session.getAttribute("user"));
		User u = (User) session.getAttribute("user");
		
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement r = mapper.readValue(req.getInputStream(), Reimbursement.class);
		rService.addReimbursement(r.getAmount(),r.getType(),r.getDescription(),u.getId());
		log.trace("ADDED NEW REIMBURSEMENT "+r);
	}
	
}
