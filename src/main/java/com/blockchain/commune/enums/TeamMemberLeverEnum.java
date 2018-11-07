package com.blockchain.commune.enums;

public enum TeamMemberLeverEnum {
    //普通会员
    COMMON(0),
    //会长
    PRESIDENT(9),
    //生态会长
    ECOLOGICAL(10);

    int code;
    TeamMemberLeverEnum(int i) {
        code = i;
    }

    public int getCode() {
        return code;
    }
}
