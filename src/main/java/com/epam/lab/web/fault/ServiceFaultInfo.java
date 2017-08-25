package com.epam.lab.web.fault;

public class ServiceFaultInfo {
    private String message;

    public ServiceFaultInfo(FaultMessage expression, Object ...args ){
        setMessage(String.format(expression.getMessageExpression(), args));

    }
    public ServiceFaultInfo(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
