package com.ecobenchmark.kotlinspringjpa.controllers

import com.google.protobuf.Empty
import io.grpc.Status
import io.grpc.StatusException
import io.grpc.health.v1.HealthCheckRequest
import io.grpc.health.v1.HealthCheckResponse
import io.grpc.health.v1.HealthGrpc
import io.grpc.stub.ServerCalls
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class Healthcheck:ecobenchmark.HealthGrpcKt.HealthCoroutineImplBase() {

    override suspend fun getHealthCheck(request: Empty): Empty {
        return request
    }
}
