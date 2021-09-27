package si.msh.studentapplikation.controller;

import gRPC.gRpcClient.StudentGRpcClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import si.msh.studentapplikation.exception.StudentNotFoundException;
import si.msh.studentapplikation.model.Student;
import si.msh.studentapplikation.model.StudentGradeDTO;
import si.msh.studentapplikation.repository.StudentRepository;
import si.msh.studentapplikation.wsdl.GeoIPServiceLocator;
import si.msh.studentapplikation.wsdl.GeoIPServiceSoap_PortType;

import javax.xml.rpc.ServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository repository;

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<Student> retrieveStudent(@PathVariable long id)
    {
        Optional<Student> student = repository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("id: " + id);
        }
        EntityModel<Student> resource = EntityModel.of(student.get()); 						// get the resource
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllStudents());      // get link
        resource.add(linkTo.withRel("all-students"));										// append the link

        Link selfLink = linkTo(methodOn(this.getClass()).retrieveStudent(id)).withSelfRel(); //add also link to self
        resource.add(selfLink);
        return resource;

    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
        repository.deleteById(id);
    }

    // Create a new resource and remember its unique location in the hypermedia
    @PostMapping("/")
    public ResponseEntity<Object> createStudent(@RequestBody Student student)
    {
        Student savedStudent = repository.save(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStudent.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable long id)
    {
        Optional<Student> studentOptional = repository.findById(id);
        if (studentOptional.isEmpty())
            return ResponseEntity.notFound().build();
        student.setId(id);
        repository.save(student);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping("/exercise")
    public ModelAndView exercise() throws RemoteException {
        ModelAndView exercise = new ModelAndView();
        // exercise.addObject("students", repository.findAll());
        exercise.setViewName("exercise");
        return exercise;
    }

    @RequestMapping("/iplocator")
    public String calculator() throws IOException {
        String stringbuilder = "IP: ";
        String ip = null;
        try
        {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            ip = in.readLine();

            GeoIPServiceLocator locator = new GeoIPServiceLocator();
            GeoIPServiceSoap_PortType service = locator.getGeoIPServiceSoap();
            stringbuilder += ip;
            String country = (service.getIpLocation(ip)).substring(16,18);
            stringbuilder += " - Located in " + service.getCountryNameByISO2(country);
        }
        catch (ServiceException | RemoteException ex)
        {
            ex.printStackTrace();
        }
        return stringbuilder;
    }

    @GetMapping("/test/{id}")
    public StudentGradeDTO retrieveStudentFromGRPC(@PathVariable long id)
    {
        Optional<Student> student = repository.findById(id); // Get Data from H2
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8074")
                .usePlaintext()
                .build();

        System.out.println("Channel should have resultResponse"+ channel);
        StudentGRpcClient resultClient = new StudentGRpcClient(channel);

        System.out.println("\nShowing Data From Repository: \n");
        System.out.println(repository.findById(id));
        System.out.println("\nShowing Data From GRPC: \n");
        System.out.println(resultClient.getResults(id));

        StudentGradeDTO studentGradeDto;

        if (student.isPresent()) {
            studentGradeDto = new StudentGradeDTO
                    (
                            id,
                            student.get().getName(),
                            student.get().getMail(),
                            resultClient.getResults(id).get(0),
                            resultClient.getResults(id).get(1)
                    );
        } else {
            return null;
        }

        return studentGradeDto;
    }

    @GetMapping("/test/all")
    public List<StudentGradeDTO> retrieveAllStudentsFromGRPC() {
        List<Student> students = repository.findAll();
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8074")
                .usePlaintext()
                .build();
        List<StudentGradeDTO> studentgrades = new ArrayList<StudentGradeDTO>();
        StudentGRpcClient resultClient = new StudentGRpcClient(channel);
        for (Student current: students) {
            StudentGradeDTO studentGradeDto;
                studentGradeDto = new StudentGradeDTO
                        (
                                current.getId(),
                                current.getName(),
                                current.getMail(),
                                resultClient.getResults(current.getId()).get(0),
                                resultClient.getResults(current.getId()).get(1)
                        );
            assert false;
            studentgrades.add(studentGradeDto);
            }
        return studentgrades;
    }
}
