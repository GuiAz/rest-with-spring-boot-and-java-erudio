package br.com.erudio.integrationtests.testcontainers;

import org.testcontainers.containers.MySQLContainer;

public class TesteDocker {
    public static void main(String[] args) {
        MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");
        mysql.start();
        System.out.println("JDBC URL: " + mysql.getJdbcUrl());
        mysql.stop();
    }

    static {
        System.setProperty(
                "testcontainers.docker.client.strategy",
                "org.testcontainers.dockerclient.DockerClientProviderStrategy$WindowsNamedPipe"
        );
    }

}