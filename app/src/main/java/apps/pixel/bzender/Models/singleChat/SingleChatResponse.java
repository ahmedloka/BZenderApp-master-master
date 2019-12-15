package apps.pixel.bzender.Models.singleChat;

import java.util.List;

public class SingleChatResponse {
    private List<ChatList> ChatList;

    public List<ChatList> getChatList() {
        return ChatList;
    }

    public void setChatList(List<ChatList> ChatList) {
        this.ChatList = ChatList;
    }

    @Override
    public String toString() {
        return "ClassPojo [ChatList = " + ChatList + "]";
    }
}
