package apps.sharabash.bzender.activities.chatRoom;

import java.util.List;

import apps.sharabash.bzender.Models.singleChat.ChatList;
import apps.sharabash.bzender.Models.singleChat.SingleChatResponse;

public interface ChatRoomInterface {
    void getChatRoomData(List<ChatList> chatLists);
    void finishGetDate();
}
