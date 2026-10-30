import java.util.ArrayList;

/**
 * Keeps track of today's appointments.
 * This first version adds bookings and checks for conflicts.
 */
public class AppointmentManager {

    private final ArrayList<Appointment> appointments;

    public AppointmentManager() {
        appointments = new ArrayList<Appointment>();
    }

    /** Tries to add a booking and returns true if it was added. */
    public boolean addAppointment(Appointment appointment) {
        if (appointment == null) {
            System.out.println("WARNING: An appointment must be supplied.");
            return false;
        }

        HealthProfessional professional = appointment.getProfessional();

        // Even a free time slot cannot be booked if the person is off today.
        if (!professional.worksToday()) {
            System.out.println("WARNING: " + professional.getName()
                    + " does not work today (" + HealthProfessional.getTodayName() + ").");
            return false;
        }

        // Check both the staff ID and the time before adding a new booking.
        for (Appointment existing : appointments) {
            boolean sameProfessional = existing.getProfessional().equals(professional);
            boolean sameTime = existing.getTimeInMinutes() == appointment.getTimeInMinutes();
            if (sameProfessional && sameTime) {
                System.out.println("WARNING: Double booking rejected. Professional ID "
                        + professional.getId() + " already has an appointment at "
                        + appointment.getTime() + ".");
                return false;
            }
        }

        appointments.add(appointment);
        System.out.println("SUCCESS: Added " + appointment.getSummary());
        return true;
    }

    /**
     * Another way to add a booking using the patient details directly.
     * A bad input prints a warning instead of stopping the whole program.
     */
    public boolean addAppointment(String patientName, String mobileNumber,
                                  HealthProfessional professional, String time) {
        try {
            Patient patient = new Patient(patientName, mobileNumber);
            Appointment appointment = new Appointment(patient, professional, time);
            return addAppointment(appointment);
        } catch (IllegalArgumentException exception) {
            System.out.println("WARNING: Cannot add appointment: " + exception.getMessage());
            return false;
        }
    }
}
