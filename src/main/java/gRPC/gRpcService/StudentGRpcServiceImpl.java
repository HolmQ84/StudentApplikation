package gRPC.gRpcService;

import com.studentApplicationGRpc.stubs.student.StudentGRpcRequest;
import com.studentApplicationGRpc.stubs.student.StudentGRpcResponse;
import com.studentApplicationGRpc.stubs.student.StudentGRpcServiceGrpc;
import gRPC.gRpcDao.StudentGRpcDao;
import gRPC.gRpcDomain.StudentGRpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentGRpcServiceImpl extends StudentGRpcServiceGrpc.StudentGRpcServiceImplBase {

    private static final Logger logger = Logger.getLogger(StudentGRpcServiceImpl.class.getName());

    //Data access object that fetches data from the db and returns it
    private StudentGRpcDao studentGRpcDao = new StudentGRpcDao();

    @Override
    public void getStudentInfo(StudentGRpcRequest request, StreamObserver<StudentGRpcResponse> responseObserver) {
        int studentId = (int) request.getStudentId();

        try{
            StudentGRpc studentGRpc = studentGRpcDao.findById(studentId);


            StudentGRpcResponse studentGRpcResponse = StudentGRpcResponse.newBuilder()
                    .setId(studentId)
                    .setPhone(studentGRpc.getPhone()) //Using Getters from target folder
                    .setAddress(studentGRpc.getAddress()) //Using Getters from target folder
                    .build();

            //System.out.println("Student repsone object: \n"+ studentGRpcResponse);

            responseObserver.onNext(studentGRpcResponse); // This send the data to port 8081 so bloom can fetch the data
            responseObserver.onCompleted();
        }catch (NoSuchElementException e){
            logger.log(Level.SEVERE, "NO STUDENT FOUND WITH THE STUDENT ID: "+studentId);

            // If some error occurs with status not_found
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
    }
}
