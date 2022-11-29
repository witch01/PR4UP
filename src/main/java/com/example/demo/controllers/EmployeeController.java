package com.example.demo.controllers;


import com.example.demo.models.Employee;
import com.example.demo.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/emp")
    public String blogMain(Model model)
    {
        Iterable<Employee> employee = employeeRepository.findAll();
        model.addAttribute("employee", employee);
        return "employee-main";
    }

    @GetMapping("/add")
    public String EmployeeAdd( Employee employee, Model model)
    {
        return "employee-add";
    }

//    @PostMapping("/add")
//    public String employeePostAdd(@RequestParam(defaultValue = "0") String surname,
//                                  @RequestParam(defaultValue ="0") double salary,
//                                  @RequestParam(defaultValue = "false") boolean gender,
//                              @RequestParam(defaultValue = "0") int children,
//                              @RequestParam(defaultValue = "2022.11.11") Date dr, Model model)
//    {
//
//        Employee employee = new Employee(surname, dr, gender, salary, children);
//        employeeRepository.save(employee);
//        return "redirect:/employee/emp";
//    }

    @PostMapping("/add")
    public String employeePostAdd(@ModelAttribute("employee") @Valid  Employee employee, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "employee-add";
        }
        employeeRepository.save(employee);
        return "redirect:/employee/emp";
    }

    @GetMapping("/filter")
    public String employeeFilter(Model model)
    {
        return "employee-filter";
    }

    @PostMapping("/filter/result")
    public String blogResult(@RequestParam String surname, Model model)
    {
        List<Employee> result = employeeRepository.findBySurnameContains(surname);
        List<Employee> result1 = employeeRepository.findBySurname(surname);
//        List<Post> result = postRepository.findLikeTitle(title);
        model.addAttribute("result", result);
        return "employee-filter";
    }

    @GetMapping("/{id}")
    public String employeeView(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Employee> employee = employeeRepository.findById(id);
        ArrayList<Employee> res = new ArrayList<>();
        employee.ifPresent(res::add);
        model.addAttribute("employee", res);
        if(!employeeRepository.existsById(id))
        {
            return "redirect:/";
        }
        return "employee-view";

    }

    @GetMapping("/{id}/edit")
    public String employeeEdit(@PathVariable("id")long id,
                               Model model)
    {
        Employee employee = employeeRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid students Id" + id));
        model.addAttribute("employee",employee);

        return "employee-view";
    }

    @PostMapping("/{id}/edit")
    public String employeeUpdate(@ModelAttribute("employee")@Valid Employee employee, BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        employee.setId(id);
        if (bindingResult.hasErrors()) {
            return "employee-view";
        }
        employeeRepository.save(employee);
        return "redirect:/employee/emp";
    }

    @PostMapping("/{id}/remove")
    public String employeeDelete(@PathVariable("id") long id, Model model){
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.delete(employee);
        return "redirect:/employee/emp";
    }
}
