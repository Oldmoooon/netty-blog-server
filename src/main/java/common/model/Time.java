package common.model;

import java.util.Date;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class Time {
    private long timeMillis;

    public Time() {
        this.timeMillis = System.currentTimeMillis();
    }

    public Time(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    @Override
    public String toString() {
        return new Date(timeMillis).toString();
    }
}
