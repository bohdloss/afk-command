package com.aylanj123.afkcommand.afkstate.capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum StateSource {
    SELF_APPLY,
    OPERATOR_APPLIED,
    LOGIN_APPLIED,
    IDLED_TOO_LONG;

    public @NotNull String intoString() {
        String val = null;

        switch(this) {
            case SELF_APPLY -> {
                val = "SELF_APPLY";
            }
            case OPERATOR_APPLIED -> {
                val = "OPERATOR_APPLIED";
            }
            case LOGIN_APPLIED -> {
                val = "LOGIN_APPLIED";
            }
            case IDLED_TOO_LONG -> {
                val = "IDLED_TOO_LONG";
            }
        }

        return val;
    }

    public static @Nullable StateSource fromString(@NotNull String val) {
        switch(val) {
            case "SELF_APPLY" -> {
                return SELF_APPLY;
            }
            case "OPERATOR_APPLIED" -> {
                return OPERATOR_APPLIED;
            }
            case "LOGIN_APPLIED" -> {
                return LOGIN_APPLIED;
            }
            case "IDLED_TOO_LONG" -> {
                return IDLED_TOO_LONG;
            }
        }

        return null;
    }
}
