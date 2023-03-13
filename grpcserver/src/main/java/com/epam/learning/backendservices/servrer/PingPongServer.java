package com.epam.learning.backendservices.servrer;

import com.epam.learning.backendservices.service.PingPongServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingPongServer {

    private static final Logger logger = Logger.getLogger(PingPongServer.class.getName());
    private Server server;

    public void startServer() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("grpcserver.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        int port = Integer.parseInt(properties.getProperty("grpc.port"));
        try {
            server = ServerBuilder
                    .forPort(port)
                    .addService(new PingPongServiceImpl())
                    .build()
                    .start();
            logger.info("Server started on port " + port);
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    logger.info("Clean server shutdown in case JVM was shutdown");
                    try {
                        PingPongServer.this.stopServer();
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, "Server shutdown interrupted", e);
                    }
                }
            });
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server didn't start!", e);
        }
    }

    public void stopServer() throws InterruptedException {
        if (server != null) {
            server.shutdown()
                    .awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        PingPongServer pingPongServer = new PingPongServer();
        pingPongServer.startServer();
        pingPongServer.blockUntilShutdown();
    }
}
