package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;
import com.revature.util.ConnectionFactory;

public class ReimbursementDao implements DAO<Reimbursement,Integer>{
	
	@Override
	public List<Reimbursement> findAll() {
		List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String query = "SELECT * FROM ERS_REIMBURSEMENT ORDER BY REIMB_SUBMITTED DESC"; //no semicolon
			
			//STATEMENT INTERFACE - implementation exposed via connection
			Statement statement = conn.createStatement();
			
			//ResultSet Interface - represents the result set of a DB Query
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				Reimbursement temp = new Reimbursement();
				temp.setId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setSubmitted(rs.getTimestamp(3));
				temp.setResolved(rs.getTimestamp(4));
				temp.setDescription(rs.getString(5));
				temp.setAuthor(rs.getInt(6));
				temp.setResolver(rs.getInt(7));
				temp.setStatus(rs.getInt(8));
				temp.setType(rs.getInt(9));
				reimbs.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reimbs;
	}

	
	/*
	 * Prepared Statement
	 */
	public Reimbursement findByID(Integer id) {
		Reimbursement r = null;
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				r = new Reimbursement();
				r.setId(rs.getInt(1));
				r.setAmount(rs.getDouble(2));
				r.setSubmitted(rs.getTimestamp(3));
				r.setResolved(rs.getTimestamp(4));
				r.setDescription(rs.getString(5));
				r.setAuthor(rs.getInt(6));
				r.setResolver(rs.getInt(7));
				r.setStatus(rs.getInt(8));
				r.setType(rs.getInt(9));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

	public List<Reimbursement> findAllByID(Integer id) {
		List <Reimbursement> reimbs = new ArrayList<Reimbursement>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_AUTHOR = ? ORDER BY REIMB_SUBMITTED DESC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Reimbursement temp = new Reimbursement();
				temp.setId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setSubmitted(rs.getTimestamp(3));
				temp.setResolved(rs.getTimestamp(4));
				temp.setDescription(rs.getString(5));
				temp.setAuthor(rs.getInt(6));
				temp.setResolver(rs.getInt(7));
				temp.setStatus(rs.getInt(8));
				temp.setType(rs.getInt(9));
				reimbs.add(temp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbs;
		
		
	}
	
	public List<Reimbursement> findPendingReimbs() {
		List <Reimbursement> reimbs = new ArrayList<Reimbursement>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_STATUS_ID = 1 ORDER BY REIMB_SUBMITTED";
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			
			while (rs.next()) {
				Reimbursement temp = new Reimbursement();
				temp.setId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setSubmitted(rs.getTimestamp(3));
				temp.setResolved(rs.getTimestamp(4));
				temp.setDescription(rs.getString(5));
				temp.setAuthor(rs.getInt(6));
				temp.setResolver(rs.getInt(7));
				temp.setStatus(rs.getInt(8));
				temp.setType(rs.getInt(9));
				reimbs.add(temp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbs;
		
		
	}
	
	public List<Reimbursement> findPastReimbs() {
		List <Reimbursement> reimbs = new ArrayList<Reimbursement>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_STATUS_ID != 1 ORDER BY REIMB_SUBMITTED DESC";
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				Reimbursement temp = new Reimbursement();
				temp.setId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setSubmitted(rs.getTimestamp(3));
				temp.setResolved(rs.getTimestamp(4));
				temp.setDescription(rs.getString(5));
				temp.setAuthor(rs.getInt(6));
				temp.setResolver(rs.getInt(7));
				temp.setStatus(rs.getInt(8));
				temp.setType(rs.getInt(9));
				reimbs.add(temp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbs;
		
		
	}
	
	public Reimbursement insert(Reimbursement obj) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO ERS_REIMBURSEMENT(REIMB_AMOUNT,REIMB_SUBMITTED,"
					+ "REIMB_DESCRIPTION,REIMB_AUTHOR,"
					+ "REIMB_STATUS_ID,REIMB_TYPE_ID) VALUES(?,?,?,?,?,?)";
			String[] keyNames = {"REIMB_ID"};
			
			PreparedStatement ps = conn.prepareStatement(sql,keyNames);
			ps.setDouble(1, obj.getAmount());
			ps.setTimestamp(2, obj.getSubmitted());
			ps.setString(3, obj.getDescription());
			ps.setInt(4, obj.getAuthor());
			ps.setInt(5, obj.getStatus());
			ps.setInt(6, obj.getType());
			
			int numRows = ps.executeUpdate();
			if(numRows > 0) {
				ResultSet pk = ps.getGeneratedKeys();
				while(pk.next()) {
					obj.setId(pk.getInt(1));
				}
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	public Reimbursement update(Reimbursement obj) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "UPDATE ERS_REIMBURSEMENT SET REIMB_STATUS_ID = ?, "
					+ "REIMB_RESOLVER = ?, REIMB_RESOLVED = ? WHERE REIMB_ID = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, obj.getStatus());
			ps.setInt(2, obj.getResolver());
			ps.setTimestamp(3, obj.getResolved());
			ps.setInt(4, obj.getId());
			ps.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

}
