package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.pojos.User;
import com.revature.util.ConnectionFactory;

import oracle.jdbc.internal.OracleTypes;

public class UserDao implements Dao<User, Integer> {

	//FINDS ALL USERS AND RETURNS A LIST
	//USES CALLABLE STATEMENT
	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		//using callable statement
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "{ call GET_ALL_USERS(?) }"; // {} used for callable

			// STATEMENT INTERFACE - implementation exposed via connection
			CallableStatement cs = conn.prepareCall(sql);

			// ResultSet Interface - represents the result set of a DB Query
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(1);
			
			while (rs.next()) {
				User temp = new User();
				temp.setId(rs.getInt(1));
				temp.setFirstName(rs.getString(2));
				temp.setLastName(rs.getString(3));
				temp.setEmail(rs.getString(4));
				temp.setPassword(rs.getString(5));
				users.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	//FINDS USER BY ID
	@Override
	public User findByID(Integer id) {
		User u = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM BANK_USER WHERE USER_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt(1));
				u.setFirstName(rs.getString(2));
				u.setLastName(rs.getString(3));
				u.setEmail(rs.getString(4));
				u.setPassword(rs.getString(5));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}

	//FINDS USER BY EMAIL
	public User findByEmail(String email) {
		User u = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM BANK_USER WHERE EMAIL = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt(1));
				u.setFirstName(rs.getString(2));
				u.setLastName(rs.getString(3));
				u.setEmail(email);
				u.setPassword(rs.getString(5));
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR! Not a valid email address!");
			System.out.println("Enter a valid email: ");
			Scanner sc = new Scanner(System.in);
			String in = sc.nextLine();
			findByEmail(in);
			sc.close();
		}
		return u;
	}
	
	//INSERTS A USER INTO DATABASE
	@Override
	public User insert(User obj) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO BANK_USER(FIRSTNAME,LASTNAME,EMAIL,PW) VALUES(?,?,?,?)";
			String[] keyNames = {"USER_ID"};
			
			PreparedStatement ps = conn.prepareStatement(sql,keyNames);
			ps.setString(1, obj.getFirstName());
			ps.setString(2, obj.getLastName());
			ps.setString(3, obj.getEmail());
			ps.setString(4, obj.getPassword());
			
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

	//UPDATE IS NOT IMPLEMENTED
	@Override
	public User update(User obj) {
		
		return null;
	}
	
	//DELETE IS NOT IMPLEMENTED
	@Override
	public void delete(User obj) {
		// TODO Auto-generated method stub
		
	}

	
	
}
