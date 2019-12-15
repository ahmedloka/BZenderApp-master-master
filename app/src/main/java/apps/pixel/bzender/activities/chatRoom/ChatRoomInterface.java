package apps.pixel.bzender.activities.chatRoom;

import java.util.List;

import apps.pixel.bzender.Models.singleChat.ChatList;

public interface ChatRoomInterface {
    void getChatRoomData(List<ChatList> chatLists);
    void finishGetDate();
}
