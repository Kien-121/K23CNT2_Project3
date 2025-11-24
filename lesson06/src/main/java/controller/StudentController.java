package controller;

import entity.Student;
import service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public String getStudent(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/student-list";
    }
    @GetMapping
    public String addNewStudents(Model model) {
        model.addAttribute("students", new Student());
        return "student/student-add";
    }
    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        StudentDTO student = studentService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "student/student-edit";
    }
    @PostMapping
    public String saveStudent(@ModelAttribute("student") StudentDTO student) {
        studentService.save(student);
        return "redirect:/student";
        
    }
    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable(value = "id") Long id, @ModelAttribute("student") StudentDTO student) {
        studentService.updateStudentById(id, student);
        return "redirect:/student";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(value = "id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student";
    }
}

