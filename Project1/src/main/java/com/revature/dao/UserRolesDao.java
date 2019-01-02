package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.UserRoles;
import com.revature.util.ConnectionFactory;

public class UserRolesDao implements DAO<UserRoles,Integer>{

	@Override
	public List<UserRoles> findAll() {
		List<UserRoles> uroles = new ArrayList<UserRoles>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String query = "SELECT * FROM ERS_USER_ROLES ORDER BY USER_ROLE_ID"; //no semicolon
			
			//STATEMENT INTERFACE - implementation exposed via connection
			Statement statement = conn.createStatement();
			//ResultSet Interface - represents the result set of a DB Query
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				UserRoles temp = new UserRoles();
				temp.setId(rs.getInt(1));
				temp.setRole(rs.getString(2));
				uroles.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uroles;
	}

	@Override
	public UserRoles findByID(Integer id) {
		UserRoles ur = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_USER_ROLES WHERE USER_ROLE_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ur = new UserRoles();
				ur.setId(rs.getInt(1));
				ur.setRole(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ur;
	}

	@Override
	public UserRoles insert(UserRoles obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRoles update(UserRoles obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
