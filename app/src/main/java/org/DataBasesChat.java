package org;

import android.provider.BaseColumns;

public final class DataBasesChat {


    public static final class CreateDB implements BaseColumns {

        public static final String LOGINID = "loginId";       // 현재 로그인 된 아이디

        public static final String CHATID = "chatId";         // 채팅하는 상대

        public static final String ID = "id";                 // 메시지 보낸 아이디

        public static final String NAME = "name";             // 메시지 보낸 사람 이름

        public static final String IMAGE= "image";            // 메시지 보낸 사람 프사

        public static final String CHAT = "chat";             // 메시지

        public static final String _TABLENAME0 = "chattable";                                          // 테이블이름 각자 다르게 설정해줘야됨.

        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("

                +_ID+" integer primary key autoincrement, "

                +LOGINID+" text not null , "

                +CHATID+" text not null , "

                +ID+" text not null , "

                +NAME+" text not null , "

                +IMAGE+" text not null , "

                +CHAT+" text not null );";

    }

}
