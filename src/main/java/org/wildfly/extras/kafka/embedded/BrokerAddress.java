package org.wildfly.extras.kafka.embedded;

import kafka.cluster.BrokerEndPoint;

/**
 * Encapsulates the address of a Kafka broker.
 *
 * @author Marius Bogoevici
 * @author Gary Russell
 */
public class BrokerAddress {

    public static final int DEFAULT_PORT = 9092;

    private final String host;

    private final int port;

    public BrokerAddress(String host, int port) {
        Assert.hasText(host, "Host cannot be empty");
        this.host = host;
        this.port = port;
    }

    public BrokerAddress(String host) {
        this(host, DEFAULT_PORT);
    }

    public BrokerAddress(BrokerEndPoint broker) {
        Assert.notNull(broker, "Broker cannot be null");
        this.host = broker.host();
        this.port = broker.port();
    }

    public static BrokerAddress fromAddress(String address) {
        String[] split = address.split(":");
        if (split.length == 0 || split.length > 2) {
            throw new IllegalArgumentException("Expected format <host>[:<port>]");
        }
        if (split.length == 2) {
            return new BrokerAddress(split[0], Integer.parseInt(split[1]));
        }
        else {
            return new BrokerAddress(split[0]);
        }
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public int hashCode() {
        return 31 * this.host.hashCode() + this.port; // NOSONAR magic #
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BrokerAddress brokerAddress = (BrokerAddress) o;

        return this.port == brokerAddress.port && this.host.equals(brokerAddress.host);
    }

    @Override
    public String toString() {
        return this.host + ":" + this.port;
    }

}


