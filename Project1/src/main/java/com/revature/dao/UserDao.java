package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.User;
import com.revature.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

public class UserDao implements DAO<User, Integer> {

	// FINDS ALL USERS AND RETURNS A LIST
	// USES CALLABLE STATEMENT
	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		// using callable statement
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "{ call GET_ERS_USERS(?) }"; // {} used for callable

			// STATEMENT INTERFACE - implementation exposed via connection
			CallableStatement cs = conn.prepareCall(sql);

			// ResultSet Interface - represents the result set of a DB Query
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.execute();

			ResultSet rs = (ResultSet) cs.getObject(1);

			while (rs.next()) {
				User temp = new User();
				temp.setId(rs.getInt(1));
				temp.setUsername(rs.getString(2));
				temp.setFirstName(rs.getString(3));
				temp.setLastName(rs.getString(4));
				temp.setEmail(rs.getString(5));
				temp.setPassword(rs.getString(6));
				temp.setRoleID(rs.getInt(7));
				users.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	// FINDS USER BY ID
	@Override
	public User findByID(Integer id) {
		User u = null;
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_USER WHERE USER_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				u.setEmail(rs.getString(6));
				u.setRoleID(rs.getInt(7));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}
	
	public User findByUsername(String Username) {
		User u = new User();
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ERS_USER WHERE USERNAME = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Username);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u.setId(rs.getInt(1));
				u.setUsername(Username);
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				u.setEmail(rs.getString(6));
				u.setRoleID(rs.getInt(7));
			}
			return u;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// INSERTS A USER INTO DATABASE
	@Override
	public User insert(User obj) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO ERS_USER(USERNAME,PW,FIRSTNAME,LASTNAME,USER_EMAIL,USER_ROLE_ID) VALUES(?,?,?,?,?,?)";
			String[] keyNames = { "USER_ID" };

			PreparedStatement ps = conn.prepareStatement(sql, keyNames);
			ps.setString(1, obj.getUsername());
			ps.setString(2, obj.getPassword());
			ps.setString(3, obj.getFirstName());
			ps.setString(4, obj.getLastName());
			ps.setString(5, obj.getEmail());
			ps.setInt(6, obj.getRoleID());

			int numRows = ps.executeUpdate();
			if (numRows > 0) {
				ResultSet pk = ps.getGeneratedKeys();
				while (pk.next()) {
					obj.setId(pk.getInt(1));
				}
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obj;
	}

	// UPDATE IS NOT IMPLEMENTED
	@Override
	public User update(User obj) {

		return null;
	}

}
