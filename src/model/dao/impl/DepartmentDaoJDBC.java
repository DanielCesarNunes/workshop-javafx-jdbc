package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Department;
import model.dao.DepartmentDao;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
					+ "VALUES (?)",
					+ Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, department.getName());
					
			int rowsAffected = ps.executeUpdate();
					
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}
			DB.closeResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public void update(Department department) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
							"UPDATE department "
							+ "SET Name = ?"
							+ "WHERE Id = ?");
			
			ps.setString(1, department.getName());
			ps.setInt(2, department.getId());
			
			ps.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}
		

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE department.Id = ?");
			
			ps.setInt(1, id);
			
			ps.execute();
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT a.*"
					+ " FROM department a"
					+ " WHERE a.Id = ?");
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				Department department = instantiateDepartment(rs);
				return department;
			}
			else return null;			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
			DB.closeStatement(ps);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT a.* "
					+ "FROM department a "
					+ "ORDER BY a.Id");
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			while(rs.next()) {
				Department dep =  instantiateDepartment(rs);
				list.add(dep);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	
}
