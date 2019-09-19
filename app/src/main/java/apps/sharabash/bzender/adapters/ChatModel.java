package apps.sharabash.bzender.adapters;

public class ChatModel {

    private String senderId;
    private int RoomId;
    //private String img;
    private String Name;
    private String TenderName;
    private String statusID;
    private String msg;
    private String userChatStatus;
    private int PagesCount;

    //, String img

    public ChatModel(String senderId, int RoomId, String name, String msg, String TenderName, String statusID, int PagesCount, String userChatStatus) {
        this.senderId = senderId;
        this.RoomId = RoomId;
        //this.img = img;
        Name = name;
        this.msg = msg;
        this.PagesCount = PagesCount;
        this.statusID = statusID;
        this.TenderName = TenderName;
        this.userChatStatus = userChatStatus;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTenderName() {
        return TenderName;
    }

    public void setTenderName(String tenderName) {
        TenderName = tenderName;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserChatStatus() {
        return userChatStatus;
    }

    public void setUserChatStatus(String userChatStatus) {
        this.userChatStatus = userChatStatus;
    }

    public int getPagesCount() {
        return PagesCount;
    }

    public void setPagesCount(int pagesCount) {
        PagesCount = pagesCount;
    }
}
