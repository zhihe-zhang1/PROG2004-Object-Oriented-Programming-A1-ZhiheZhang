import java.util.ArrayList;

/**
 * Keeps track of today's appointments.
 * Handles booking, searching, sorting and cancelling appointments.
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

    /** Returns a new list so other code cannot change the stored bookings. */
    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<Appointment>(appointments);
    }

    /** Finds all appointments for a particular health professional. */
    public ArrayList<Appointment> getAppointmentsByProfessionalId(int professionalId) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId() == professionalId) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    /** Finds appointments using the patient's mobile number. */
    public ArrayList<Appointment> getAppointmentsByPatientPhone(String mobileNumber) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            return matches;
        }

        String phone = mobileNumber.trim();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getMobileNumber().equals(phone)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    /** Prints all bookings in a list or a warning if there are none. */
    public void displayAppointments(ArrayList<Appointment> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("WARNING: No appointments found.");
            return;
        }

        // Display the list in the order provided by the caller.
        for (Appointment appointment : results) {
            System.out.println(appointment.getSummary());
        }
    }

    /** Gets all bookings in time order without changing the stored list. */
    public ArrayList<Appointment> getSortedAppointments() {
        return getSortedAppointments(getAllAppointments());
    }

    /** Sorts either a full list or a filtered list by appointment time. */
    public ArrayList<Appointment> getSortedAppointments(ArrayList<Appointment> selected) {
        ArrayList<Appointment> sorted = new ArrayList<Appointment>();
        if (selected == null) {
            System.out.println("WARNING: No appointment list was supplied.");
            return sorted;
        }

        sorted.addAll(selected);
        // Appointment.compareTo compares the starting times.
        java.util.Collections.sort(sorted);
        return sorted;
    }

    /** Cancels one booking using the professional ID and appointment time. */
    public boolean cancelByProfessionalAndTime(int professionalId, String time) {
        int minutes;
        try {
            minutes = ClinicSchedule.parseSlot(time);
        } catch (IllegalArgumentException exception) {
            System.out.println("WARNING: Cannot cancel appointment: " + exception.getMessage());
            return false;
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getProfessional().getId() == professionalId
                    && appointment.getTimeInMinutes() == minutes) {
                appointments.remove(i);
                System.out.println("SUCCESS: Cancelled " + appointment.getSummary());
                return true;
            }
        }

        System.out.println("WARNING: No appointment found for professional ID "
                + professionalId + " at " + time + ".");
        return false;
    }

    /** Cancels all bookings associated with a patient's mobile number. */
    public boolean cancelByPatientPhone(String mobileNumber) {
        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            System.out.println("WARNING: A patient phone number is required.");
            return false;
        }

        String phone = mobileNumber.trim();
        int removed = 0;

        // Go backwards so removing a booking does not skip the next one.
        for (int i = appointments.size() - 1; i >= 0; i--) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatient().getMobileNumber().equals(phone)) {
                appointments.remove(i);
                removed++;
            }
        }

        if (removed == 0) {
            System.out.println("WARNING: No appointment found for phone number " + phone + ".");
            return false;
        }

        System.out.println("SUCCESS: Cancelled " + removed
                + " appointment(s) for phone number " + phone + ".");
        return true;
    }
}
