/** One appointment, with a patient, a staff member and a start time. */
public class Appointment implements Comparable<Appointment> {

    private final Patient patient;
    private final HealthProfessional professional;
    private final int timeInMinutes;

    /** Creates a booking only when the patient, staff and time are valid. */
    public Appointment(Patient patient, HealthProfessional professional, String time) {
        if (patient == null) {
            throw new IllegalArgumentException("An appointment requires a patient.");
        }
        if (professional == null) {
            throw new IllegalArgumentException("An appointment requires a professional.");
        }
        this.patient = patient;
        this.professional = professional;
        this.timeInMinutes = ClinicSchedule.parseSlot(time);
    }

    public Patient getPatient() {
        return patient;
    }

    public HealthProfessional getProfessional() {
        return professional;
    }

    public String getTime() {
        return ClinicSchedule.formatTime(timeInMinutes);
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    /** Compares the start times so the appointments can be sorted. */
    @Override
    public int compareTo(Appointment other) {
        if (this.timeInMinutes < other.timeInMinutes) {
            return -1;
        }
        if (this.timeInMinutes > other.timeInMinutes) {
            return 1;
        }
        // Two bookings can start at the same time for different staff.
        return 0;
    }

    /** Gives one line of details for the appointment list. */
    public String getSummary() {
        return getTime() + " | " + patient.getName()
                + " (" + patient.getMobileNumber() + ")"
                + " | " + professional.getProfessionalType()
                + ": " + professional.getName() + " (ID " + professional.getId() + ")";
    }

    /** Shows the patient and the full staff details together. */
    @Override
    public String toString() {
        return "Appointment for today at " + getTime() + "\n"
                + "  " + patient.toString() + "\n"
                + "  Professional: " + professional.toString();
    }
}
