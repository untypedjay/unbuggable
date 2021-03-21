package io.untypedjay.unbuggable.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import io.untypedjay.unbuggable.dao.EmployeeDao;
import io.untypedjay.unbuggable.domain.Employee;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class EmployeeDaoJdbc implements EmployeeDao {

  private static class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int i) throws SQLException {
      var employee = new Employee();
      employee.setId(rs.getLong(1));
      employee.setFirstName(rs.getString(2));
      employee.setLastName(rs.getString(3));
      employee.setDateOfBirth(rs.getDate(4).toLocalDate());
      return employee;
    }
  }

  private DataSource dataSource;
  private JdbcTemplate jdbcTemplate;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Employee findById(Long id) throws DataAccessException {
    String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE where ID=?";
    List<Employee> employees = jdbcTemplate.query(sql, new EmployeeMapper(), id);
    if (employees.size() == 0) {
      return null;
    } else if (employees.size() == 1) {
      return employees.get(0);
    } else {
      throw new IncorrectResultSizeDataAccessException(1, employees.size());
    }
  }

  @Override
  public List<Employee> findAll() {
    String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE";
    return jdbcTemplate.query(sql, new EmployeeMapper());
  }

  // Version 1: Data access code without Spring
  public void insert1(final Employee e) throws DataAccessException {
    final String sql =
      "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
      + "values (?, ?, ?)";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, e.getFirstName());
      stmt.setString(2, e.getLastName());
      stmt.setDate(3, Date.valueOf(e.getDateOfBirth()));
      stmt.executeUpdate();
    }
    catch (SQLException ex) {
      System.err.println(ex);
    }
  }

  // version 2: template method

  public void insert2(final Employee e) throws DataAccessException {
    final String sql =
      "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
        + "values (?, ?, ?)";

    jdbcTemplate.update(sql, (PreparedStatement ps) -> {
        ps.setString(1, e.getFirstName());
        ps.setString(2, e.getLastName());
        ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
    });
  }

  // version 3
  @Override
  public void insert(final Employee e) throws DataAccessException {
    final String sql =
      "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
        + "values (?, ?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update((Connection con) -> {
        PreparedStatement ps = con.prepareStatement(sql, new String[] {"ID"});
        ps.setString(1, e.getFirstName());
        ps.setString(2, e.getLastName());
        ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
        return ps;
      },
      keyHolder);

    e.setId(keyHolder.getKey().longValue());
  }

  @Override
  public Employee merge(Employee entity) {
    return null;
  }
}
