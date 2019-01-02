package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.ReimbursementStatus;
import com.revature.util.ConnectionFactory;

public class ReimbursementStatusDao implements DAO<ReimbursementStatus,Integer>{

	@Override
	public List<ReimbursementStatus> findAll() {
		List<ReimbursementStatus> restats = new ArrayList<ReimbursementStatus>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String query = "SELECT * FROM ERS_REIMB_STATUS ORDER BY REIMB_STATUS_ID"; //no semicolon
			
			//STATEMENT INTERFACE - implementation exposed via connection
			Statement statement = conn.createStatement();
			//ResultSet Interface - represents the result set of a DB Query
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				ReimbursementStatus temp = new ReimbursementStatus();
				temp.setId(rs.getInt(1));
				temp.setStatus(rs.getString(2));
				restats.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restats;
	}

	@Override
	public ReimbursementStatus findByID(Integer id) {
		ReimbursementStatus rstat = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_REIMB_STATUS WHERE REIMB_STATUS_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rstat = new ReimbursementStatus();
				rstat.setId(rs.getInt(1));
				rstat.setStatus(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rstat;
	}

	@Override
	public ReimbursementStatus insert(ReimbursementStatus obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus update(ReimbursementStatus obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
