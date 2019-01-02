package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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

@WebServlet({"/pending","/approveReimb","/denyReimb"})
public class ViewPendingServlet extends HttpServlet{

	static ReimbursementService rService = new ReimbursementService();
	private static Logger log = Logger.getLogger(ViewPendingServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//consult service layer for data
		List<Reimbursement> reimbs = rService.getPendingReimbs();
		
		//convert to JSON
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(reimbs);
		log.trace("FINDING ALL REIMBS. JSON: " + json);
		
		//send response
		PrintWriter writer = resp.getWriter();
		resp.setContentType("/application/json");
		writer.write(json);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement r = mapper.readValue(req.getInputStream(), Reimbursement.class);
		Reimbursement reimb = rService.findById(r.getId());
		if (r.getStatus()==2) {
			reimb.setStatus(2);
		}
		else {
			reimb.setStatus(3);
		}
		HttpSession session = req.getSession(false);
		User u = (User) session.getAttribute("user");
		reimb.setResolver(u.getId());
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		reimb.setResolved(ts);
		rService.updateReimb(reimb);
	}
	
}