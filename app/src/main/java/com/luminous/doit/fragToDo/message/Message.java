package com.luminous.doit.fragToDo.message;

/**
 * Created by 90418 on 2017-05-30.
 */

public class Message {
    private String timeStart;
    private String timeStop;
    private String message;
    private String Date;
    private String finish;
    private Message(){}
    public static class Builder{
        private final Message message;
        public Builder(){
            message = new Message();
        }
        public Builder setTimeStart(String timeStart){
            message.setTimeStart(timeStart);
            return this;
        }
        public Builder setTimeStop(String timeStop){
            message.setTimeStop(timeStop);
            return this;
        }
        public Builder setMessage(String messages){
            message.setMessage(messages);
            return this;
        }
        public Builder setDate(String date){
            message.setDate(date);
            return this;
        }
        public Builder setFinish(String finish){
            message.setFinish(finish);
            return this;
        }
        public Message build(){
            return message;
        }
    }
    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(String timeStop) {
        this.timeStop = timeStop;
    }
    public String getFinish() {
        return finish;
    }
    public void setFinish(String finish) {
        this.finish = finish;
    }
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
