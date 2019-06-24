package org;

public class ChatItem {

    String loginId;
    String chatId;
    String id;
    String name;
    String image;
    String chat;

    String getLoginId(){
        return this.loginId;
    }

    String getChatId(){
        return this.chatId;
    }

    String getId(){
        return this.id;
    }

    String getName(){
        return this.name;
    }

    String getImage(){
        return this.image;
    }

    String getChat(){
        return this.chat;
    }

    ChatItem(String loginId, String chatId, String id, String name, String image, String chat){

        this.loginId=loginId;
        this.chatId=chatId;
        this.id=id;
        this.name=name;
        this.image=image;
        this.chat=chat;

    }

}