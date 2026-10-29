import java.util.Calendar;

/**
 * Stores the details that all health professionals have in common.
 * A professional must be a specific type, so this class is abstract.
 *
 * @author Zhihe Zhang
 */
public abstract class HealthProfessional {

    private final int id;
    private final String name;
    private final String[] workingDays;

    /** Creates a professional and checks that the basic details are valid. */
    public HealthProfessional(int id, String name, String[] workingDays) {
        if (id <= 0) {
            throw new IllegalArgumentException("Professional ID must be greater than zero.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Professional name cannot be empty.");
        }
        if (workingDays == null || workingDays.length == 0) {
            throw new IllegalArgumentException("At least one working day is required.");
        }

        this.id = id;
        this.name = name.trim();
        this.workingDays = new String[workingDays.length];

        // Copy the days one at a time so the original array cannot change our data.
        for (int i = 0; i < workingDays.length; i++) {
            String day = normaliseDay(workingDays[i]);
            for (int j = 0; j < i; j++) {
                if (this.workingDays[j].equals(day)) {
                    throw new IllegalArgumentException("Repeated working day: " + day);
                }
            }
            this.workingDays[i] = day;
        }
    }

    /** Uses Monday to Friday when no working days are given. */
    public HealthProfessional(int id, String name) {
        this(id, name, new String[] {
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
        });
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /** Returns a copy of the working days, not the original array. */
    public String[] getWorkingDays() {
        String[] copy = new String[workingDays.length];
        for (int i = 0; i < workingDays.length; i++) {
            copy[i] = workingDays[i];
        }
        return copy;
    }

    /** Checks if this person works on the given weekday. */
    public boolean worksOn(String day) {
        if (day == null) {
            return false;
        }
        for (String workingDay : workingDays) {
            if (workingDay.equalsIgnoreCase(day.trim())) {
                return true;
            }
        }
        return false;
    }

    /** Checks whether the professional is working today. */
    public boolean worksToday() {
        return worksOn(getTodayName());
    }

    /** Gets today's weekday as a word such as Monday. */
    public static String getTodayName() {
        String[] days = {
                "", "Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday"
        };
        int number = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return days[number];
    }

    /** Changes a weekday to its usual spelling, or rejects an invalid day. */
    private String normaliseDay(String day) {
        if (day == null) {
            throw new IllegalArgumentException("Working day cannot be null.");
        }
        String[] days = {
                "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"
        };
        for (String item : days) {
            if (item.equalsIgnoreCase(day.trim())) {
                return item;
            }
        }
        throw new IllegalArgumentException("Invalid working day: " + day);
    }

    /** Each staff type provides its own name and description of its work. */
    public abstract String getProfessionalType();

    public abstract String performDuty();

    /** Two objects with the same staff ID refer to the same professional. */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional professional = (HealthProfessional) other;
        // Names can be the same, so the ID is the important part here.
        return this.id == professional.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        String days = "";
        for (int i = 0; i < workingDays.length; i++) {
            if (i > 0) {
                days += ", ";
            }
            days += workingDays[i];
        }
        return "Type: " + getProfessionalType()
                + " | ID: " + id
                + " | Name: " + name
                + " | Working days: " + days;
    }
}
