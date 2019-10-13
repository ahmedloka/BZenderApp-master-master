package apps.sharabash.bzender.Models.singleChat;

public class ChatList {

    public ChatList(String messageDate, String id, String senderId, String body , String nickName) {
        MessageDate = messageDate;
        Id = id;
        SenderId = senderId;
        Body = body;
        Nickname = nickName ;

    }

    private String MessageDate;

    private String Id;

    private String Nickname ;

    private String SenderId;

    private String Body;

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getMessageDate() {
        return MessageDate;
    }

    public void setMessageDate(String MessageDate) {
        this.MessageDate = MessageDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String SenderId) {
        this.SenderId = SenderId;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String Body) {
        this.Body = Body;
    }

    @Override
    public String toString() {
        return "ClassPojo [MessageDate = " + MessageDate + ", Id = " + Id + ", SenderId = " + SenderId + ", Body = " + Body + "]";
    }
}

