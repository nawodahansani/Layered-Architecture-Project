package lk.ijse.fishmarketing.bo.custom;

import lk.ijse.fishmarketing.bo.SuperBo;
import lk.ijse.fishmarketing.entity.Employee;
import lk.ijse.fishmarketing.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBo extends SuperBo {
    List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;

    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    Employee searchByEmployeeId(String id) throws SQLException, ClassNotFoundException;

    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    int getEmployeeCount() throws SQLException, ClassNotFoundException;
}
