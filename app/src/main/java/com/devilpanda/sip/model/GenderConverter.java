package com.devilpanda.sip.model;

import androidx.room.TypeConverter;

public class GenderConverter {
    @TypeConverter
    public static Gender toGender(String gender) {
        if (gender.equals(Gender.MAN.toString())) {
            return Gender.MAN;
        } else if (gender.equals(Gender.WOMAN.toString())) {
            return Gender.WOMAN;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static String toString(Gender gender) {
        return gender.toString();
    }
}
