package com.epam.learning.backendservices.grpcservice.bean;

public class PingPongEntity {
    private int sn;

    public PingPongEntity(int sn, String subject) {
        this.sn = sn;
        this.subject = subject;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subject;
}
