package com.hyrax.backend.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ErrorMessage {

    @XmlElement(name = "statusCode")
    private int statusCode;

    @XmlElement(name = "errorCode")
    private int errorCode;

    @XmlElement(name = "clientMessage")
    private String clientMessage;

    public ErrorMessage(HyraxException e) {
        this.setStatusCode(e.getStatus().value());
        this.setErrorCode(e.getErrorCode());
        this.setClientMessage(e.getClientMessage());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }
}
