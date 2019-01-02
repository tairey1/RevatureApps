package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.ReimbursementType;
import com.revature.util.ConnectionFactory;

public class ReimbursementTypeDao implements DAO <ReimbursementType,Integer>{

	@Override
	public List<ReimbursementType> findAll() {
		List<ReimbursementType> retypes = new ArrayList<ReimbursementType>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String query = "SELECT * FROM ERS_REIMB_TYPE ORDER BY REIMB_TYPE_ID"; //no semicolon
			
			//STATEMENT INTERFACE - implementation exposed via connection
			Statement statement = conn.createStatement();
			
			//ResultSet Interface - represents the result set of a DB Query
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				ReimbursementType temp = new ReimbursementType();
				temp.setId(rs.getInt(1));
				temp.setType(rs.getString(2));
				retypes.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retypes;
	}

	@Override
	public ReimbursementType findByID(Integer id) {
		ReimbursementType rt = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_REIMB_TYPE WHERE REIMB_TYPE_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rt = new ReimbursementType();
				rt.setId(rs.getInt(1));
				rt.setType(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}

	@Override
	public ReimbursementType insert(ReimbursementType obj) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO ERS_REIMB_TYPE(TYPE) VALUES(?)";
			String[] keyNames = {"REIMB_TYPE_ID"};
			
			PreparedStatement ps = conn.prepareStatement(sql,keyNames);
			ps.setString(1, obj.getType());
			
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

	@Override
	public ReimbursementType update(ReimbursementType obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
