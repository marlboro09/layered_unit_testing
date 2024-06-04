package com.prac.music.domain.user.entity;

public enum UserStatusEnum {
    NORMAL(Authority.NORMAL),
    SECESSION(Authority.SECESSION);

    private final String authority;

    UserStatusEnum(String authority){
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }

    public static class Authority{
        public static final String NORMAL = "NORMAL";
        public static final String SECESSION = "SECESSION";
    }


}
