By Martin Holmqvist, Nicholas Tureczek & Patrick Jønnson.

To start application:

1. Make sure Maven plugin registry is activated.
- Settings --> Build, Execution, Deployment --> Build Tools --> Maven --> Activate "Use Plugin Registry"

3. Run src/main/java/gRPC/gRpcServer/StudentGRpcServer.java

GRPC server runs on localhost:8074/
To test with BloomRPC, copy proto from "src/main/proto/student.proto" into BloomRPC.

3. Run src/main/java/si/msh/studentapplikation/StudentApplikationApplication.java

Rest application runs on localhost:8080/

4. SOAP implementation is a IP Locator

Goto localhost:8080/students/ipLocator