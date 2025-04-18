package com.mentorshipwise.balanceservicestudy.utils;

public class RegexUtil {
    public static final String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static final String STRONG_PASSWORD =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&+\\-])[A-Za-z\\d@$!%*?&+\\-]{8,}$";
}
