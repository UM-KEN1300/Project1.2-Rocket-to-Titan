package code.utils;

import org.apache.poi.ss.formula.functions.T;

public class Time {
    double year;
    double months;
    double days;
    double hours;
    double minutes;
    double seconds;


    public Time(double year, double months, double days, double hours, double minutes, double seconds) {
        this(year, months, days, hours, minutes);
        this.seconds = seconds;
        if (isNotValidDate()) System.out.println("The date is wrong");
    }


    public Time(double year, double months, double days, double hours, double minutes) {
        this(year, months, days, hours);
        this.minutes = minutes;
        this.seconds = 0;
        if (isNotValidDate()) System.out.println("The date is wrong");
    }

    public Time(double year, double months, double days, double hours) {
        this(year, months, days);
        this.hours = hours;
        this.minutes = 0;
        this.seconds = 0;
        if (isNotValidDate()) System.out.println("The date is wrong");
    }

    public Time(double year, double months, double days) {
        this.year = year;
        this.months = months;
        this.days = days;
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        if (isNotValidDate()) System.out.println("The date is wrong");
    }

    public Time(double[] time) {
        this.year = time[0];
        this.months = time[1];
        this.days = time[2];
        this.hours = time[3];
        this.minutes = time[4];
        this.seconds = time[5];
        if (isNotValidDate()) System.out.println("The date is wrong");
    }

    public boolean sameHour(Time other) {
        return year == other.year &&
                months == other.months &&
                days == other.days &&
                hours == other.hours;
    }

    public void addSeconds(int additionalSeconds) {
        seconds += additionalSeconds;

        while (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }

        while (minutes >= 60) {
            minutes -= 60;
            hours++;
        }

        while (hours >= 24) {
            hours -= 24;
            days++;
            if (days > getDaysInMonth(months, year)) {
                days = 1;
                months++;
                if (months > 12) {
                    months = 1;
                    year++;
                }
            }
        }
    }

    public boolean isNotValidDate() {
        // Check if the year is within a valid range
        if (year < 1) {
            return true;
        }

        // Check if the month is within a valid range
        if (months < 1 || months > 12) {
            return true;
        }

        // Check if the day is within a valid range for the given month and year
        if (days < 1 || days > getDaysInMonth(months, year)) {
            return true;
        }

        // Check if hours, minutes, and seconds are within a valid range
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
            return true;
        }

        // All checks passed, the date is valid
        return false;
    }

    private int getDaysInMonth(double month, double year) {
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    private boolean isLeapYear(double year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public double[] getTimeArr() {
        return new double[]{year, months, days, hours, minutes, seconds};
    }

    public Time timeCopy() {
        return new Time(year, months, days, hours, minutes, seconds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Time other = (Time) obj;
        return days == other.days &&
                months == other.months &&
                year == other.year &&
                hours == other.hours &&
                minutes == other.minutes &&
                seconds == other.seconds;
    }
}
