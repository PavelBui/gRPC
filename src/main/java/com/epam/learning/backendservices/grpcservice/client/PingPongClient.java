package com.epam.learning.backendservices.grpcservice.client;

import com.epam.learning.backendservices.grpcservice.PingPongServiceGrpc;
import com.epam.learning.backendservices.grpcservice.PingRequest;
import com.epam.learning.backendservices.grpcservice.PongResponce;
import com.epam.learning.backendservices.grpcservice.bean.PingPongEntity;
import com.epam.learning.backendservices.grpcservice.servrer.PingPongServer;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.logging.Logger;

public class PingPongClient {

    private PingPongServiceGrpc.PingPongServiceBlockingStub pingPongServiceBlockingStub;
    private static final Logger logger = Logger.getLogger(PingPongServer.class.getName());

    public PingPongClient(Channel channel) {
        pingPongServiceBlockingStub = PingPongServiceGrpc.newBlockingStub(channel);
    }

    public PingPongEntity getPong(PingPongEntity pingPongEntity) {
        int snIn = pingPongEntity.getSn();
        String ping = pingPongEntity.getSubject();
        PingRequest pingRequest = PingRequest.newBuilder()
                .setSn(snIn)
                .setPing(ping).build();
        PongResponce pongResponce = pingPongServiceBlockingStub.getPing(pingRequest);
        int snOut = pongResponce.getSn();
        String pong = pongResponce.getPong();
        if (snIn + 1 != snOut) {
            return new PingPongEntity(0, "PingPong protocol error!");
        } else {
            return new PingPongEntity(snOut, pong);
        }
    }

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext().build();
        PingPongClient pingPongClient = new PingPongClient(channel);
        PingPongEntity pingPongEntityIn = new PingPongEntity(17, "Ping");
        PingPongEntity pingPongEntityOut = pingPongClient.getPong(pingPongEntityIn);
        logger.info("Ping-pong result:\n" +
                         "   Ping: " + pingPongEntityIn.getSn() + " - " + pingPongEntityIn.getSubject() + "\n" +
                         "   Pong: " + pingPongEntityOut.getSn() + " - " + pingPongEntityOut.getSubject());

    }
}
