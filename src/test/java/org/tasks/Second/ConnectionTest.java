package org.tasks.Second;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {

    private final Connection connection = new Connection();

    @Test
    void emptyInput() {
        assertFalse(connection.connect("", 80));
    }

    @Test
    void invalidServerAddress() {
        assertFalse(connection.connect("nonexistent.example.com", 80));
    }

    @Test
    void validServerAddress() {
        assertTrue(connection.connect("www.google.com", 80));
    }

    @Test
    void unavailableServer() {
        assertFalse(connection.connect("192.0.2.0", 80));
        // Это недоступный адрес из диапазона TEST-NET-1
    }

    @Test
    void availableServer() {
        assertTrue(connection.connect("www.example.com", 80));
    }

    @Test
    void reconnect() {
        assertTrue(connection.connect("www.example.com", 80));
        assertTrue(connection.connect("www.example.com", 80));
    }

    @Test
    void httpServer() {
        assertTrue(connection.connect("httpbin.org", 80));
    }

    @Test
    void httpsServer() {
        assertTrue(connection.connect("httpbin.org", 443));
    }

    @Test
    void gameServer() {
        // Для примера используется сервер Minecraft
        assertTrue(connection.connect("mc.hypixel.net", 25565));
    }

    @Test
    void domainName() {
        assertTrue(connection.connect("www.google.com", 80));
    }

    @Test
    void IPv4Address() {
        assertTrue(connection.connect("8.8.8.8", 53));
    }

    @Test
    void IPv6Address() {
        assertTrue(connection.connectUDP("2001:4860:4860::8888", 53));
    }


    @Test
    void smallTimeout() {
        assertFalse(connection.connect("www.example.com", 81));
        // Неоткрытый порт
    }

    @Test
    void largeTimeout() {
        assertTrue(connection.connect("www.example.com", 80));
    }

    @Test
    void negativePort() {
        assertThrows(IllegalArgumentException.class, () -> connection.connect("www.google.com", -1));
    }


    @Test
    void exceedingMaxPort() {
        assertFalse(connection.connect("www.google.com", 65535));
    }

    @Test
    void maxPort() {
        assertThrows(IllegalArgumentException.class, () -> connection.connect("www.google.com", 65536));
    }


    @Test
    void TCPConnection() {
        assertTrue(connection.connectTCP("www.google.com", 80, 1000));
    }

    @Test
    void UDPConnection() {
        assertTrue(connection.connectUDP("8.8.8.8", 53));
    }

    @Test
    void TCPConnectionWithSSL() {
        assertTrue(connection.connectTCPWithSSL("www.google.com", 443, 1000, false));
    }

    @Test
    void TCPConnectionWithInvalidSSL() {
        assertFalse(connection.connectTCPWithSSL("untrusted-root.badssl.com", 443, 1000, false));
    }

    @Test
    void TCPConnectionWithTrustedInvalidSSL() {
        assertTrue(connection.connectTCPWithSSL("untrusted-root.badssl.com", 443, 1000, true));
    }

}
