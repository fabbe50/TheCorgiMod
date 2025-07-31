package com.fabbe50.corgimod.data;

public enum DayTime {
    MORNING(0),
    NOON(6000),
    EVENING(13000),
    MIDNIGHT(18000),
    END_OF_DAY(24000);

    private final long timeTick;
    DayTime(long timeTick) {
        this.timeTick = timeTick;
    }

    public long getTimeTick() {
        return timeTick;
    }

    public static boolean isBetween(DayTime dayTime1, DayTime dayTime2, long timeOfDay) {
        return isAfter(dayTime1, timeOfDay) && isBefore(dayTime2, timeOfDay);
    }

    public static boolean isBefore(DayTime dayTime, long timeOfDay) {
        return timeOfDay < dayTime.getTimeTick();
    }

    public static boolean isAfter(DayTime dayTime, long timeOfDay) {
        return timeOfDay > dayTime.getTimeTick();
    }
}
