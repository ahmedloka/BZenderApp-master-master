package apps.pixel.bzender.Models.chatList;

public class ChatList {
    private String TenderName;

    private String LastMessage;

    private String SenderName;

    private String StatusId;

    private String PagesCount;

    private String CategoryId ;

    private int RoomId;

    private String SenderId;

    private String UserChatStatus;

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getTenderName() {
        return TenderName;
    }

    public void setTenderName(String TenderName) {
        this.TenderName = TenderName;
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String LastMessage) {
        this.LastMessage = LastMessage;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String SenderName) {
        this.SenderName = SenderName;
    }

    public String getStatusId() {
        return StatusId;
    }

    public void setStatusId(String StatusId) {
        this.StatusId = StatusId;
    }

    public String getPagesCount() {
        return PagesCount;
    }

    public void setPagesCount(String PagesCount) {
        this.PagesCount = PagesCount;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int RoomId) {
        this.RoomId = RoomId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String SenderId) {
        this.SenderId = SenderId;
    }

    public String getUserChatStatus() {
        return UserChatStatus;
    }

    public void setUserChatStatus(String UserChatStatus) {
        this.UserChatStatus = UserChatStatus;
    }

    @Override
    public String toString() {
        return "ClassPojo [TenderName = " + TenderName + ", LastMessage = " + LastMessage + ", SenderName = " + SenderName + ", StatusId = " + StatusId + ", PagesCount = " + PagesCount + ", RoomId = " + RoomId + ", SenderId = " + SenderId + ", UserChatStatus = " + UserChatStatus + "]";
    }
}

