package in.medit.codemed.chatmet;

import java.util.Date;

public class ChatMessages {

    private String messageText;
    private String messageUser;
    private long messageTime;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ChatMessages(String messageText, String messageUser, String userName) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.userName = userName;


        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessages(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}