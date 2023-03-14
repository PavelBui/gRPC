package com.epam.learning.backendservices.service;

import com.epam.learning.backendservices.grpcservice.PingPongServiceGrpc;
import com.epam.learning.backendservices.grpcservice.PingRequest;
import com.epam.learning.backendservices.grpcservice.PongResponce;
import io.grpc.stub.StreamObserver;

public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {

    @Override
    public void getPing(PingRequest request, StreamObserver<PongResponce> responseObserver) {

        int snIn = request.getSn();
        String ping = request.getPing();
        int snOut = 0;
        String pong = "Out";
        if (ping.equals("Ping")) {
            snOut = snIn + 1;
            pong = "Pong";
        }
        PongResponce.Builder pongResponseBuilder = PongResponce.newBuilder()
                .setSn(snOut)
                .setPong(pong);
        PongResponce pongResponse = pongResponseBuilder.build();
        responseObserver.onNext(pongResponse);
        responseObserver.onCompleted();
    }
}
