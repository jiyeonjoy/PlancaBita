package org;

import android.provider.BaseColumns;

public final class DataBasesChatList {


    public static final class CreateDB implements BaseColumns {

        public static final String LOGINID = "loginId";       // 현재 로그인 된 아이디

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String IMAGE= "image";

        public static final String CHAT = "chat";

        public static final String _TABLENAME0 = "chatlisttable";                                          // 테이블이름 각자 다르게 설정해줘야됨.

        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("

                +_ID+" integer primary key autoincrement, "

                +LOGINID+" text not null , "

                +ID+" text not null , "

                +NAME+" text not null , "

                +IMAGE+" text not null , "

                +CHAT+" text not null );";

    }

}
