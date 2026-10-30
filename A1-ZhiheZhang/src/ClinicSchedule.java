import java.util.ArrayList;

/**
 * Holds the clinic opening hours and checks appointment times.
 * Using minutes after midnight makes the time checks easier.
 */
public class ClinicSchedule {

    public static final int OPEN_HOUR = 8;
    public static final int CLOSE_HOUR = 16;
    public static final int SLOT_MINUTES = 30;

    /** No objects are needed because these methods are shared helpers. */
    private ClinicSchedule() {
    }

    /**
     * Reads a time like 09:30 and returns the number of minutes since midnight.
     * Only start times on the clinic timetable are allowed.
     */
    public static int parseSlot(String time) {
        if (time == null || time.length() != 5 || time.charAt(2) != ':') {
            throw new IllegalArgumentException("Time must use HH:mm, for example 09:30.");
        }
        for (int i = 0; i < time.length(); i++) {
            if (i == 2) {
                continue;
            }
            if (time.charAt(i) < '0' || time.charAt(i) > '9') {
                throw new IllegalArgumentException("Time must contain digits in HH:mm format.");
            }
        }

        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        if (hour > 23 || minute > 59) {
            throw new IllegalArgumentException("Invalid hour or minute in time: " + time);
        }

        int totalMinutes = hour * 60 + minute;
        int opening = OPEN_HOUR * 60;
        int closing = CLOSE_HOUR * 60;

        // A 16:00 start would finish after closing, so it is not allowed.
        if (totalMinutes < opening || totalMinutes + SLOT_MINUTES > closing) {
            throw new IllegalArgumentException("Time is outside clinic hours ("
                    + formatTime(opening) + "-" + formatTime(closing) + ").");
        }
        // The remainder must be zero for 08:00, 08:30, 09:00 and so on.
        if ((totalMinutes - opening) % SLOT_MINUTES != 0) {
            throw new IllegalArgumentException("Appointments must start every 30 minutes.");
        }
        return totalMinutes;
    }

    /** Displays the stored minutes as a time such as 08:30. */
    public static String formatTime(int totalMinutes) {
        int hour = totalMinutes / 60;
        int minute = totalMinutes % 60;
        String hourText = hour < 10 ? "0" + hour : "" + hour;
        String minuteText = minute < 10 ? "0" + minute : "" + minute;
        return hourText + ":" + minuteText;
    }

    /** Builds the list of appointment start times for one day. */
    public static ArrayList<String> getAvailableSlots() {
        ArrayList<String> slots = new ArrayList<String>();
        for (int time = OPEN_HOUR * 60;
             time + SLOT_MINUTES <= CLOSE_HOUR * 60;
             time += SLOT_MINUTES) {
            slots.add(formatTime(time));
        }
        return slots;
    }
}
