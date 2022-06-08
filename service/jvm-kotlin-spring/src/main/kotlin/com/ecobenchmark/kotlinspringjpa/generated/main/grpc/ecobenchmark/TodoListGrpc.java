package ecobenchmark;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.47.0)",
    comments = "Source: endpoints.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TodoListGrpc {

  private TodoListGrpc() {}

  public static final String SERVICE_NAME = "ecobenchmark.TodoList";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addAccountInput,
      ecobenchmark.Endpoints.addAccountOutput> getAddAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddAccount",
      requestType = ecobenchmark.Endpoints.addAccountInput.class,
      responseType = ecobenchmark.Endpoints.addAccountOutput.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addAccountInput,
      ecobenchmark.Endpoints.addAccountOutput> getAddAccountMethod() {
    io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addAccountInput, ecobenchmark.Endpoints.addAccountOutput> getAddAccountMethod;
    if ((getAddAccountMethod = TodoListGrpc.getAddAccountMethod) == null) {
      synchronized (TodoListGrpc.class) {
        if ((getAddAccountMethod = TodoListGrpc.getAddAccountMethod) == null) {
          TodoListGrpc.getAddAccountMethod = getAddAccountMethod =
              io.grpc.MethodDescriptor.<ecobenchmark.Endpoints.addAccountInput, ecobenchmark.Endpoints.addAccountOutput>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.addAccountInput.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.addAccountOutput.getDefaultInstance()))
              .setSchemaDescriptor(new TodoListMethodDescriptorSupplier("AddAccount"))
              .build();
        }
      }
    }
    return getAddAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addListInput,
      ecobenchmark.Endpoints.addListOutput> getAddListToAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddListToAccount",
      requestType = ecobenchmark.Endpoints.addListInput.class,
      responseType = ecobenchmark.Endpoints.addListOutput.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addListInput,
      ecobenchmark.Endpoints.addListOutput> getAddListToAccountMethod() {
    io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addListInput, ecobenchmark.Endpoints.addListOutput> getAddListToAccountMethod;
    if ((getAddListToAccountMethod = TodoListGrpc.getAddListToAccountMethod) == null) {
      synchronized (TodoListGrpc.class) {
        if ((getAddListToAccountMethod = TodoListGrpc.getAddListToAccountMethod) == null) {
          TodoListGrpc.getAddListToAccountMethod = getAddListToAccountMethod =
              io.grpc.MethodDescriptor.<ecobenchmark.Endpoints.addListInput, ecobenchmark.Endpoints.addListOutput>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddListToAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.addListInput.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.addListOutput.getDefaultInstance()))
              .setSchemaDescriptor(new TodoListMethodDescriptorSupplier("AddListToAccount"))
              .build();
        }
      }
    }
    return getAddListToAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addTaskInput,
      ecobenchmark.Endpoints.addTaskOutput> getAddTaskToListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddTaskToList",
      requestType = ecobenchmark.Endpoints.addTaskInput.class,
      responseType = ecobenchmark.Endpoints.addTaskOutput.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addTaskInput,
      ecobenchmark.Endpoints.addTaskOutput> getAddTaskToListMethod() {
    io.grpc.MethodDescriptor<ecobenchmark.Endpoints.addTaskInput, ecobenchmark.Endpoints.addTaskOutput> getAddTaskToListMethod;
    if ((getAddTaskToListMethod = TodoListGrpc.getAddTaskToListMethod) == null) {
      synchronized (TodoListGrpc.class) {
        if ((getAddTaskToListMethod = TodoListGrpc.getAddTaskToListMethod) == null) {
          TodoListGrpc.getAddTaskToListMethod = getAddTaskToListMethod =
              io.grpc.MethodDescriptor.<ecobenchmark.Endpoints.addTaskInput, ecobenchmark.Endpoints.addTaskOutput>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddTaskToList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.addTaskInput.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.addTaskOutput.getDefaultInstance()))
              .setSchemaDescriptor(new TodoListMethodDescriptorSupplier("AddTaskToList"))
              .build();
        }
      }
    }
    return getAddTaskToListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ecobenchmark.Endpoints.getListInput,
      ecobenchmark.Endpoints.getListOutput> getGetListsWithTasksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetListsWithTasks",
      requestType = ecobenchmark.Endpoints.getListInput.class,
      responseType = ecobenchmark.Endpoints.getListOutput.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ecobenchmark.Endpoints.getListInput,
      ecobenchmark.Endpoints.getListOutput> getGetListsWithTasksMethod() {
    io.grpc.MethodDescriptor<ecobenchmark.Endpoints.getListInput, ecobenchmark.Endpoints.getListOutput> getGetListsWithTasksMethod;
    if ((getGetListsWithTasksMethod = TodoListGrpc.getGetListsWithTasksMethod) == null) {
      synchronized (TodoListGrpc.class) {
        if ((getGetListsWithTasksMethod = TodoListGrpc.getGetListsWithTasksMethod) == null) {
          TodoListGrpc.getGetListsWithTasksMethod = getGetListsWithTasksMethod =
              io.grpc.MethodDescriptor.<ecobenchmark.Endpoints.getListInput, ecobenchmark.Endpoints.getListOutput>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetListsWithTasks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.getListInput.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.getListOutput.getDefaultInstance()))
              .setSchemaDescriptor(new TodoListMethodDescriptorSupplier("GetListsWithTasks"))
              .build();
        }
      }
    }
    return getGetListsWithTasksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ecobenchmark.Endpoints.getStatsOutput> getGetStatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStats",
      requestType = com.google.protobuf.Empty.class,
      responseType = ecobenchmark.Endpoints.getStatsOutput.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ecobenchmark.Endpoints.getStatsOutput> getGetStatsMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, ecobenchmark.Endpoints.getStatsOutput> getGetStatsMethod;
    if ((getGetStatsMethod = TodoListGrpc.getGetStatsMethod) == null) {
      synchronized (TodoListGrpc.class) {
        if ((getGetStatsMethod = TodoListGrpc.getGetStatsMethod) == null) {
          TodoListGrpc.getGetStatsMethod = getGetStatsMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, ecobenchmark.Endpoints.getStatsOutput>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ecobenchmark.Endpoints.getStatsOutput.getDefaultInstance()))
              .setSchemaDescriptor(new TodoListMethodDescriptorSupplier("GetStats"))
              .build();
        }
      }
    }
    return getGetStatsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TodoListStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoListStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoListStub>() {
        @java.lang.Override
        public TodoListStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoListStub(channel, callOptions);
        }
      };
    return TodoListStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TodoListBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoListBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoListBlockingStub>() {
        @java.lang.Override
        public TodoListBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoListBlockingStub(channel, callOptions);
        }
      };
    return TodoListBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TodoListFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoListFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoListFutureStub>() {
        @java.lang.Override
        public TodoListFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoListFutureStub(channel, callOptions);
        }
      };
    return TodoListFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TodoListImplBase implements io.grpc.BindableService {

    /**
     */
    public void addAccount(ecobenchmark.Endpoints.addAccountInput request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addAccountOutput> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddAccountMethod(), responseObserver);
    }

    /**
     */
    public void addListToAccount(ecobenchmark.Endpoints.addListInput request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addListOutput> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddListToAccountMethod(), responseObserver);
    }

    /**
     */
    public void addTaskToList(ecobenchmark.Endpoints.addTaskInput request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addTaskOutput> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddTaskToListMethod(), responseObserver);
    }

    /**
     */
    public void getListsWithTasks(ecobenchmark.Endpoints.getListInput request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.getListOutput> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetListsWithTasksMethod(), responseObserver);
    }

    /**
     */
    public void getStats(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.getStatsOutput> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStatsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ecobenchmark.Endpoints.addAccountInput,
                ecobenchmark.Endpoints.addAccountOutput>(
                  this, METHODID_ADD_ACCOUNT)))
          .addMethod(
            getAddListToAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ecobenchmark.Endpoints.addListInput,
                ecobenchmark.Endpoints.addListOutput>(
                  this, METHODID_ADD_LIST_TO_ACCOUNT)))
          .addMethod(
            getAddTaskToListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ecobenchmark.Endpoints.addTaskInput,
                ecobenchmark.Endpoints.addTaskOutput>(
                  this, METHODID_ADD_TASK_TO_LIST)))
          .addMethod(
            getGetListsWithTasksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ecobenchmark.Endpoints.getListInput,
                ecobenchmark.Endpoints.getListOutput>(
                  this, METHODID_GET_LISTS_WITH_TASKS)))
          .addMethod(
            getGetStatsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                ecobenchmark.Endpoints.getStatsOutput>(
                  this, METHODID_GET_STATS)))
          .build();
    }
  }

  /**
   */
  public static final class TodoListStub extends io.grpc.stub.AbstractAsyncStub<TodoListStub> {
    private TodoListStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoListStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoListStub(channel, callOptions);
    }

    /**
     */
    public void addAccount(ecobenchmark.Endpoints.addAccountInput request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addAccountOutput> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addListToAccount(ecobenchmark.Endpoints.addListInput request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addListOutput> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddListToAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addTaskToList(ecobenchmark.Endpoints.addTaskInput request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addTaskOutput> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddTaskToListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getListsWithTasks(ecobenchmark.Endpoints.getListInput request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.getListOutput> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetListsWithTasksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStats(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.getStatsOutput> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStatsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TodoListBlockingStub extends io.grpc.stub.AbstractBlockingStub<TodoListBlockingStub> {
    private TodoListBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoListBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoListBlockingStub(channel, callOptions);
    }

    /**
     */
    public ecobenchmark.Endpoints.addAccountOutput addAccount(ecobenchmark.Endpoints.addAccountInput request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public ecobenchmark.Endpoints.addListOutput addListToAccount(ecobenchmark.Endpoints.addListInput request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddListToAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public ecobenchmark.Endpoints.addTaskOutput addTaskToList(ecobenchmark.Endpoints.addTaskInput request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddTaskToListMethod(), getCallOptions(), request);
    }

    /**
     */
    public ecobenchmark.Endpoints.getListOutput getListsWithTasks(ecobenchmark.Endpoints.getListInput request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetListsWithTasksMethod(), getCallOptions(), request);
    }

    /**
     */
    public ecobenchmark.Endpoints.getStatsOutput getStats(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStatsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TodoListFutureStub extends io.grpc.stub.AbstractFutureStub<TodoListFutureStub> {
    private TodoListFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoListFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoListFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ecobenchmark.Endpoints.addAccountOutput> addAccount(
        ecobenchmark.Endpoints.addAccountInput request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ecobenchmark.Endpoints.addListOutput> addListToAccount(
        ecobenchmark.Endpoints.addListInput request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddListToAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ecobenchmark.Endpoints.addTaskOutput> addTaskToList(
        ecobenchmark.Endpoints.addTaskInput request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddTaskToListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ecobenchmark.Endpoints.getListOutput> getListsWithTasks(
        ecobenchmark.Endpoints.getListInput request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetListsWithTasksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ecobenchmark.Endpoints.getStatsOutput> getStats(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStatsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_ACCOUNT = 0;
  private static final int METHODID_ADD_LIST_TO_ACCOUNT = 1;
  private static final int METHODID_ADD_TASK_TO_LIST = 2;
  private static final int METHODID_GET_LISTS_WITH_TASKS = 3;
  private static final int METHODID_GET_STATS = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TodoListImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TodoListImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_ACCOUNT:
          serviceImpl.addAccount((ecobenchmark.Endpoints.addAccountInput) request,
              (io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addAccountOutput>) responseObserver);
          break;
        case METHODID_ADD_LIST_TO_ACCOUNT:
          serviceImpl.addListToAccount((ecobenchmark.Endpoints.addListInput) request,
              (io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addListOutput>) responseObserver);
          break;
        case METHODID_ADD_TASK_TO_LIST:
          serviceImpl.addTaskToList((ecobenchmark.Endpoints.addTaskInput) request,
              (io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.addTaskOutput>) responseObserver);
          break;
        case METHODID_GET_LISTS_WITH_TASKS:
          serviceImpl.getListsWithTasks((ecobenchmark.Endpoints.getListInput) request,
              (io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.getListOutput>) responseObserver);
          break;
        case METHODID_GET_STATS:
          serviceImpl.getStats((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<ecobenchmark.Endpoints.getStatsOutput>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TodoListBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TodoListBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ecobenchmark.Endpoints.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TodoList");
    }
  }

  private static final class TodoListFileDescriptorSupplier
      extends TodoListBaseDescriptorSupplier {
    TodoListFileDescriptorSupplier() {}
  }

  private static final class TodoListMethodDescriptorSupplier
      extends TodoListBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TodoListMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TodoListGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TodoListFileDescriptorSupplier())
              .addMethod(getAddAccountMethod())
              .addMethod(getAddListToAccountMethod())
              .addMethod(getAddTaskToListMethod())
              .addMethod(getGetListsWithTasksMethod())
              .addMethod(getGetStatsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
