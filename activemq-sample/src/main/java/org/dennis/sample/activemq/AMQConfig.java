package org.dennis.sample.activemq;

/**
 * @author deng.zhang
 * @since 1.0.0
 */
public class AMQConfig {
    private String brokerUrl;
    private String user;
    private String password;

    public AMQConfig() {
        this("tcp://localhost:61616", null, null);
    }

    public AMQConfig(String brokerUrl, String user, String password) {
        this.brokerUrl = brokerUrl;
        this.user = user;
        this.password = password;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
