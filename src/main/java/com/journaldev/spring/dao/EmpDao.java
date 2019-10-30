package com.journaldev.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.model.Employee;

@Repository
public class EmpDao {

	@Autowired
	private DataSource postgresDS;
	private JdbcTemplate jdbcTemplate; // implementing datasource and jdbctemplate
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource postgresDS) {
		this.postgresDS = postgresDS;
		this.jdbcTemplate = new JdbcTemplate(postgresDS);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(postgresDS);
	}
	
	public Employee getDummyEmpDetails() {
		Employee emp = new Employee();
		emp.setId(9999);
		emp.setName("Dummy");
		emp.setCreatedDate(new Date());
		return emp;
	}

	public Employee getEmpDetailsById(int empId) {
		String sql = "select * from emp where id=?";
		Employee emp=new Employee();
		try {
		 emp = jdbcTemplate.queryForObject(sql, new Object[] { empId }, new EmpMapper());
			
			return emp;
		}catch (Exception e) {
			return emp;
		}
	}
	
	public static final class EmpMapper implements RowMapper<Employee> {
		@Override
		public Employee mapRow(ResultSet rs, int rownum) throws SQLException {
			Employee emp = new Employee();
			emp.setId(rs.getInt("id"));
			emp.setName(rs.getString("name"));
			emp.setCreatedDate(new Date());
			return emp;
		}
	}

	public List<Employee> getAllEmpDetails() {
		
		String sql = "select * from emp";
		List<Employee> list=new ArrayList<Employee>();
		try {
		 list =jdbcTemplate.query(sql,new Object[] {},new EmpMapper());
			
			return list;
		}catch (Exception e) {
			return list;
		}
	}

	public int createEmp(Employee emp) {
		String sql = "insert into emp values(?,?,now())";
	
		try {
		int st =jdbcTemplate.update(sql, new Object[]{emp.getId(),emp.getName()});
			
			return st;
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Object deleteEmpById(int empId) {
		String sql = "delete from emp where id =?";
		try {
		int st =jdbcTemplate.update(sql, new Object[]{empId},new EmpMapper());
			return st;
		}catch (Exception e) {
			return -1;
		}
	}

}
