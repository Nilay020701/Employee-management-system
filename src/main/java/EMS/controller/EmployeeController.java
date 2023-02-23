package EMS.controller;

import EMS.model.Employee;
import EMS.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashBoard")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public List<Employee> getAllEmployee(@RequestParam int page, @RequestParam int size){
        return employeeService.getAllEmployee(page, size);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable int id){
        return employeeService.getEmployee(id).orElseGet(null);
    }

    @PostMapping("/employee")
    public void addEmployee(@RequestBody Employee employee){
        employeeService.addEmployee(employee);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping("/employee/{id}")
    public void updateEmployee(@RequestBody Employee employee,@PathVariable int id){
        employeeService.updateEmployee(employee,id);
    }
}
