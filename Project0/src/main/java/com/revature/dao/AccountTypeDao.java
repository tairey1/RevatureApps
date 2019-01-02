package com.revature.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.AccountType;
import com.revature.util.ConnectionFactory;


public class AccountTypeDao implements Dao<AccountType, Integer> {

	//USES STATEMENT
	@Override
	public List<AccountType> findAll() {
		List<AccountType> ats = new ArrayList<AccountType>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String query = "SELECT * FROM ACCOUNT_TYPE"; //no semicolon
			
			//STATEMENT INTERFACE - implementation exposed via connection
			Statement statement = conn.createStatement();
			
			//ResultSet Interface - represents the result set of a DB Query
			ResultSet rs = statement.executeQuery(query);
			
			while (rs.next()) {
				AccountType temp = new AccountType();
				temp.setId(rs.getInt(1));
				temp.setName(rs.getString(2));
				ats.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ats;
	}

	@Override
	public AccountType findByID(Integer id) {
		AccountType at = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ACCOUNT_TYPE WHERE ACC_TYPE_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				at = new AccountType();
				at.setId(rs.getInt(1));
				at.setName(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return at;
	}

	@Override
	public AccountType insert(AccountType obj) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO ACCOUNT_TYPE(NAME) VALUES(?)";
			String[] keyNames = {"ACC_TYPE_ID"};
			
			PreparedStatement ps = conn.prepareStatement(sql,keyNames);
			ps.setString(1, obj.getName());
			
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
	public AccountType findByEmail(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountType update(AccountType obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(AccountType obj) {
		// TODO Auto-generated method stub
		
	}

}
