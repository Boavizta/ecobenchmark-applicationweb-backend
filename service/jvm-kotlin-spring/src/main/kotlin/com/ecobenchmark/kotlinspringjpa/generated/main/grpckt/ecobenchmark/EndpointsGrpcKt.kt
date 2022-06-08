package ecobenchmark

import com.google.protobuf.Empty
import ecobenchmark.HealthGrpc.getServiceDescriptor
import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Holder for Kotlin coroutine-based client and server APIs for ecobenchmark.Health.
 */
public object HealthGrpcKt {
  public const val SERVICE_NAME: String = HealthGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = HealthGrpc.getServiceDescriptor()

  public val getHealthCheckMethod: MethodDescriptor<Empty, Empty>
    @JvmStatic
    get() = HealthGrpc.getGetHealthCheckMethod()

  /**
   * A stub for issuing RPCs to a(n) ecobenchmark.Health service as suspending coroutines.
   */
  @StubFor(HealthGrpc::class)
  public class HealthCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<HealthCoroutineStub>(channel, callOptions) {
    public override fun build(channel: Channel, callOptions: CallOptions): HealthCoroutineStub =
        HealthCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun getHealthCheck(request: Empty, headers: Metadata = Metadata()): Empty =
        unaryRpc(
      channel,
      HealthGrpc.getGetHealthCheckMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the ecobenchmark.Health service based on Kotlin coroutines.
   */
  public abstract class HealthCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for ecobenchmark.Health.GetHealthCheck.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getHealthCheck(request: Empty): Empty = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ecobenchmark.Health.GetHealthCheck is unimplemented"))

    public final override fun bindService(): ServerServiceDefinition =
        builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = HealthGrpc.getGetHealthCheckMethod(),
      implementation = ::getHealthCheck
    )).build()
  }
}

/**
 * Holder for Kotlin coroutine-based client and server APIs for ecobenchmark.TodoList.
 */
public object TodoListGrpcKt {
  public const val SERVICE_NAME: String = TodoListGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = TodoListGrpc.getServiceDescriptor()

  public val addAccountMethod:
      MethodDescriptor<Endpoints.addAccountInput, Endpoints.addAccountOutput>
    @JvmStatic
    get() = TodoListGrpc.getAddAccountMethod()

  public val addListToAccountMethod:
      MethodDescriptor<Endpoints.addListInput, Endpoints.addListOutput>
    @JvmStatic
    get() = TodoListGrpc.getAddListToAccountMethod()

  public val addTaskToListMethod: MethodDescriptor<Endpoints.addTaskInput, Endpoints.addTaskOutput>
    @JvmStatic
    get() = TodoListGrpc.getAddTaskToListMethod()

  public val getListsWithTasksMethod:
      MethodDescriptor<Endpoints.getListInput, Endpoints.getListOutput>
    @JvmStatic
    get() = TodoListGrpc.getGetListsWithTasksMethod()

  public val getStatsMethod: MethodDescriptor<Empty, Endpoints.getStatsOutput>
    @JvmStatic
    get() = TodoListGrpc.getGetStatsMethod()

  /**
   * A stub for issuing RPCs to a(n) ecobenchmark.TodoList service as suspending coroutines.
   */
  @StubFor(TodoListGrpc::class)
  public class TodoListCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<TodoListCoroutineStub>(channel, callOptions) {
    public override fun build(channel: Channel, callOptions: CallOptions): TodoListCoroutineStub =
        TodoListCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun addAccount(request: Endpoints.addAccountInput, headers: Metadata =
        Metadata()): Endpoints.addAccountOutput = unaryRpc(
      channel,
      TodoListGrpc.getAddAccountMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun addListToAccount(request: Endpoints.addListInput, headers: Metadata =
        Metadata()): Endpoints.addListOutput = unaryRpc(
      channel,
      TodoListGrpc.getAddListToAccountMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun addTaskToList(request: Endpoints.addTaskInput, headers: Metadata =
        Metadata()): Endpoints.addTaskOutput = unaryRpc(
      channel,
      TodoListGrpc.getAddTaskToListMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun getListsWithTasks(request: Endpoints.getListInput, headers: Metadata =
        Metadata()): Endpoints.getListOutput = unaryRpc(
      channel,
      TodoListGrpc.getGetListsWithTasksMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun getStats(request: Empty, headers: Metadata = Metadata()):
        Endpoints.getStatsOutput = unaryRpc(
      channel,
      TodoListGrpc.getGetStatsMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the ecobenchmark.TodoList service based on Kotlin coroutines.
   */
  public abstract class TodoListCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for ecobenchmark.TodoList.AddAccount.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun addAccount(request: Endpoints.addAccountInput):
        Endpoints.addAccountOutput = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ecobenchmark.TodoList.AddAccount is unimplemented"))

    /**
     * Returns the response to an RPC for ecobenchmark.TodoList.AddListToAccount.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun addListToAccount(request: Endpoints.addListInput):
        Endpoints.addListOutput = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ecobenchmark.TodoList.AddListToAccount is unimplemented"))

    /**
     * Returns the response to an RPC for ecobenchmark.TodoList.AddTaskToList.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun addTaskToList(request: Endpoints.addTaskInput): Endpoints.addTaskOutput
        = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ecobenchmark.TodoList.AddTaskToList is unimplemented"))

    /**
     * Returns the response to an RPC for ecobenchmark.TodoList.GetListsWithTasks.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getListsWithTasks(request: Endpoints.getListInput):
        Endpoints.getListOutput = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ecobenchmark.TodoList.GetListsWithTasks is unimplemented"))

    /**
     * Returns the response to an RPC for ecobenchmark.TodoList.GetStats.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getStats(request: Empty): Endpoints.getStatsOutput = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ecobenchmark.TodoList.GetStats is unimplemented"))

    public final override fun bindService(): ServerServiceDefinition =
        builder(TodoListGrpc.getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = TodoListGrpc.getAddAccountMethod(),
      implementation = ::addAccount
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = TodoListGrpc.getAddListToAccountMethod(),
      implementation = ::addListToAccount
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = TodoListGrpc.getAddTaskToListMethod(),
      implementation = ::addTaskToList
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = TodoListGrpc.getGetListsWithTasksMethod(),
      implementation = ::getListsWithTasks
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = TodoListGrpc.getGetStatsMethod(),
      implementation = ::getStats
    )).build()
  }
}
