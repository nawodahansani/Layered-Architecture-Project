package lk.ijse.fishmarketing.bo.custom.impl;

import lk.ijse.fishmarketing.bo.custom.EmployeeBo;
import lk.ijse.fishmarketing.dao.DAOFactory;
import lk.ijse.fishmarketing.dao.custom.EmployeeDAO;
import lk.ijse.fishmarketing.entity.Employee;
import lk.ijse.fishmarketing.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.Employee);
    @Override
    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException{
        List<Employee> employeeList = employeeDAO.getAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : employeeList){
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(),employee.getName(), employee.getAddress(), employee.getTel());
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }
    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException{
        return employeeDAO.save(new Employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getAddress(), employeeDTO.getTel()));
    }
    @Override
    public  boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException{
        return employeeDAO.update(new Employee(employeeDTO.getId(),employeeDTO.getName(), employeeDTO.getAddress(), employeeDTO.getTel()));
    }
    @Override
    public Employee searchByEmployeeId(String id) throws SQLException, ClassNotFoundException{
        return employeeDAO.searchById(id);
    }
    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException{
        return employeeDAO.delete(id);
    }
    @Override
    public int getEmployeeCount() throws SQLException, ClassNotFoundException{
        return employeeDAO.getCount();
    }
}
