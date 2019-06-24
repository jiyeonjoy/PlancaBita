package org;

public class ChatListItem {

    String loginId;
    String id;
    String name;
    String image;
    String chat;

    String getLoginId(){
        return this.loginId;
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

    ChatListItem(String loginId, String id, String name, String image, String chat){

        this.loginId=loginId;
        this.id=id;
        this.name=name;
        this.image=image;
        this.chat=chat;

    }

}