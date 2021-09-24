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
    private Long sid;
    private String sname;
    private String smail;

    public Student() {
        super();
    }
}