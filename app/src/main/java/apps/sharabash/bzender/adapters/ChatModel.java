package apps.sharabash.bzender.adapters;

public class ChatModel {

    private String senderId;
    private String RoomId;
    //private String img;
    private String Name;
    private String TenderName;
    private String statusID;
    private String msg;
    private int PagesCount;

    //, String img

    public ChatModel(String senderId, String RoomId, String name, String msg, String TenderName, String statusID, int PagesCount) {
        this.senderId = senderId;
        this.RoomId = RoomId;
        //this.img = img;
        Name = name;
        this.msg = msg;
        this.PagesCount = PagesCount;
        this.statusID = statusID;
        this.TenderName = TenderName;
    }


    public int getPagesCount() {
        return PagesCount;
    }

    public void setPagesCount(int pagesCount) {
        PagesCount = pagesCount;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return RoomId;
    }

    public void setReceiverId(String receiverId) {
        this.RoomId = receiverId;
    }

//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }


    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getTenderName() {
        return TenderName;
    }

    public void setTenderName(String tenderName) {
        TenderName = tenderName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
