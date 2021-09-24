package si.msh.studentapplikation.model;

import lombok.Data;

import javax.persistence.*;

// Calls Lombok to make getters and setters for the entity
@Data
// Tells JPA that it is a entity, so it builds it.
@Entity
public class Student {

    @TableGenerator(name = "Id_Gen", initialValue = 6)
    @Id
    @GeneratedValue(generator = "Id_Gen")
    private Long studentId;
    private String studentName;
    private String studentMale;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMale() {
        return studentMale;
    }

    public void setStudentMale(String studentMale) {
        this.studentMale = studentMale;
    }

    public Student() {
        super();
    }
}