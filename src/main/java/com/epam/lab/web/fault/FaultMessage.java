package com.epam.lab.web.fault;


public enum FaultMessage {
    NO_BOOK_WITH_NAME("There are no book with  name [%s]"), NOT_ENOUGH_BOOKS_OF_AUTHOR("There ara only [%d] books of [%s] but required [%d]");

    private String messageExpression;
    private FaultMessage(String messageExpression) {
        this.messageExpression = messageExpression;
    }
    public String getMessageExpression(){
        return this.messageExpression;
    }
}
