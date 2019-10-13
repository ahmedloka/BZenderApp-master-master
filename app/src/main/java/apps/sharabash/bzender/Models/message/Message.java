package apps.sharabash.bzender.Models.message;

public class Message {

    // we will use MSG_TYPE_SENT or MSG_TYPE_RECEIVED as a parameter in the constructor (here,....)-- first parameter --
    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";
    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";

    // Message content.
    private String msgContent;

    private String name;

    // Message type.
    private String msgType;

    private String catId;

    public Message(String msgType, String msgContent, String name) {
        this.msgType = msgType;
        this.msgContent = msgContent;
        this.name = name;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
