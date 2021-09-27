package gRPC.gRpcClient;

import com.studentApplicationGRpc.stubs.student.StudentGRpcRequest;
import com.studentApplicationGRpc.stubs.student.StudentGRpcResponse;
import com.studentApplicationGRpc.stubs.student.StudentGRpcServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;

public class StudentGRpcClient {
    private StudentGRpcServiceGrpc.StudentGRpcServiceBlockingStub studentGRpcServiceBlockingStub;


    public StudentGRpcClient(Channel channel){
        studentGRpcServiceBlockingStub = StudentGRpcServiceGrpc.newBlockingStub(channel);
    }

    public List<String> getResults(long studentId){
        // Creating the request object
        StudentGRpcRequest studentGRpcRequest = StudentGRpcRequest.newBuilder().setStudentId(studentId).build();
        // Getting the response back
        StudentGRpcResponse studentGRpcResponse = studentGRpcServiceBlockingStub.getStudentInfo(studentGRpcRequest);

        // Send it to the caller, in an appropriate manner in this case as a list.
        List<String> results = new ArrayList<>();

        results.add(studentGRpcResponse.getPhone());
        results.add(studentGRpcResponse.getAddress());
        return results;
    }
}