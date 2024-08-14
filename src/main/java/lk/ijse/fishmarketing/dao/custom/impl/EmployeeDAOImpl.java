package lk.ijse.fishmarketing.dao.custom.impl;

import lk.ijse.fishmarketing.dao.SQLUtil;
import lk.ijse.fishmarketing.dao.custom.EmployeeDAO;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.Employee;
import lk.ijse.fishmarketing.model.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee");
        List<Employee> empList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);

            Employee employee = new Employee(id, name, address, tel);
            empList.add(employee);
        }
        return empList;
    }

   @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        List<String> idList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT eId FROM employee");
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee VALUES(?, ?, ?, ?)",employee.getId(),employee.getName(),employee.getAddress(),employee.getTel());
    }

    @Override
    public Employee searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE eId = ?",id);
        if (resultSet.next()) {
            String empoyee_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);

            Employee employee = new Employee(empoyee_id, name, address, tel);
            return employee;
        }
        return null;
    }

    @Override
    public  boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET name = ?, address = ?, telNumber = ? WHERE eId = ?",employee.getName(),employee.getAddress(),employee.getTel(),employee.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE eId = ?",id);
    }
    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet  = SQLUtil.execute("SELECT COUNT(*) AS employee_count FROM employee");

        if (resultSet.next()) {
            return resultSet.getInt("employee_count");
        }
        return 0;
    }

}
