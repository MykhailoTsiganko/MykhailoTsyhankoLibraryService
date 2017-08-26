package com.epam.lab.web.fault;


public enum FaultMessage {
    NO_BOOK_WITH_NAME("There is no book with  name [%s]"),
    NOT_ENOUGH_BOOKS_OF_AUTHOR("There are only [%d] books of [%s], [%d] have bean required"),
    SUCH_BOOK_ALREADY_EXIST("[%s] book already exist");

    private String messageExpression;

    private FaultMessage(String message) {
        this.messageExpression = message;
    }

    public String getMessageExpression() {
        return this.messageExpression;
    }
}
