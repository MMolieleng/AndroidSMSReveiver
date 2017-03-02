package com.messagereader;

/**
 * Created by vodacominnovationpark on 3/2/17.
 */

public class MessageObj {

    private String message;
    private String added_date;
    private String status;

    public MessageObj(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
