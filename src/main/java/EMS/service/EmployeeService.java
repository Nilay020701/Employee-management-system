package EMS.service;

import EMS.model.Employee;
import EMS.repositry.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee(int page, int size){

        List<Employee> employees=new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, size);
        employeeRepository.findAll(pageRequest).forEach(employees::add);
        return employees;
    }

    public Optional<Employee> getEmployee(int id){
        return employeeRepository.findById(id);
    }

    public void addEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee,int id){
        employeeRepository.save(employee);
    }

    public void deleteEmployee(int id){
        employeeRepository.deleteById(id);
    }


}
