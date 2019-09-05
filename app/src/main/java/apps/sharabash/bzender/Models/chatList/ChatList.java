package apps.sharabash.bzender.Models.chatList;

public class ChatList {
    private String TenderName;

    private String LastMessage;

    private String SenderName;

    private String StatusId;

    private int PagesCount ;

    private String RoomId;

    private String SenderId;

    public int getPagesCount() {
        return PagesCount;
    }

    public void setPagesCount(int pagesCount) {
        PagesCount = pagesCount;
    }

    public String getTenderName ()
    {
        return TenderName;
    }

    public void setTenderName (String TenderName)
    {
        this.TenderName = TenderName;
    }

    public String getLastMessage ()
    {
        return LastMessage;
    }

    public void setLastMessage (String LastMessage)
    {
        this.LastMessage = LastMessage;
    }

    public String getSenderName ()
    {
        return SenderName;
    }

    public void setSenderName (String SenderName)
    {
        this.SenderName = SenderName;
    }

    public String getStatusId ()
    {
        return StatusId;
    }

    public void setStatusId (String StatusId)
    {
        this.StatusId = StatusId;
    }

    public String getRoomId ()
    {
        return RoomId;
    }

    public void setRoomId (String RoomId)
    {
        this.RoomId = RoomId;
    }

    public String getSenderId ()
    {
        return SenderId;
    }

    public void setSenderId (String SenderId)
    {
        this.SenderId = SenderId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [TenderName = "+TenderName+", LastMessage = "+LastMessage+", SenderName = "+SenderName+", StatusId = "+StatusId+", RoomId = "+RoomId+", SenderId = "+SenderId+"]";
    }
}

