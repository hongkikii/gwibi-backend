package com.mju.gwibi.entity;

import static java.time.LocalDate.*;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Status {
    ING("ing"),
    COMPLETE("complete"),
    NONE("none");

    private final String statusStr;

    public static Status getNowStatus(LocalDate endDate) {
        if(endDate.equals(now()) || endDate.isBefore(now())) {
            return COMPLETE;
        }
        if(endDate.isAfter(now())) {
            return ING;
        }
        return NONE;
    }
}
