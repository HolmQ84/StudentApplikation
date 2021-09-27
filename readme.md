By Martin Holmqvist, Nicholas Tureczek & Patrick Jønnson.

The project consists of two parts.
A RESTful api which is representing some students. This program also takes advantage og SOAP, with the Geo IP Service locator
- which can tell you, where in the world you are sitting, while using the application.
  The second part of the program, is the gRPC part. Here we are connecting the information from the H2 database which the
  RESTful application also uses, and extracting it via the student.proto proto file.
  While running the applications simultaneously, and then go to the endpoint "http://localhost:8080/students/test/1"
  you can see that 5 different objects are precentet. Here phone numer and Address are being presentet through the gRPC, while id, name and mail,
  is presented via the RESTful application.

To start the application:

1. Make sure Maven plugin registry is activated.
- Settings --> Build, Execution, Deployment --> Build Tools --> Maven --> Activate "Use Plugin Registry"

3. Run src/main/java/gRPC/gRpcServer/StudentGRpcServer.java

GRPC server runs on localhost:8074/
To test with BloomRPC, copy proto from "src/main/proto/student.proto" into BloomRPC.

3. Run src/main/java/si/msh/studentapplikation/StudentApplikationApplication.java

Rest application runs on localhost:8080/

4. SOAP implementation is a IP Locator (Requires to have imported framework support of Apache Axis.)

Goto localhost:8080/students/ipLocator