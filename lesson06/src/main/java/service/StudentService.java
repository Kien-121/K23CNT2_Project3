package service;

import entity.Student;
import repository.StudentRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Lấy tất cả sinh viên
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    // Tìm theo ID
    public Optional<StudentDTO> findById(long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return Optional.empty();
        }

        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());

        return Optional.of(dto);
    }

    // Lưu sinh viên
    public Boolean save(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAge(studentDTO.getAge());

        try {
            studentRepository.save(student);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Cập nhật sinh viên theo ID
    public Student updateStudentById(Long id, StudentDTO updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setAge(updatedStudent.getAge());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
    }

    // Xóa sinh viên theo ID
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
