package apps.pixel.bzender.Models.chatList;

import java.util.List;

public class AllUserMessage {
    private List<ChatList> ChatList;

    public List<ChatList> getChatList ()
    {
        return ChatList;
    }

    public void setChatList (List<ChatList> ChatList)
    {
        this.ChatList = ChatList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ChatList = "+ChatList+"]";
    }
}

