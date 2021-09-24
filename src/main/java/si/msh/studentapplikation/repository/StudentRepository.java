package si.msh.studentapplikation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.msh.studentapplikation.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
