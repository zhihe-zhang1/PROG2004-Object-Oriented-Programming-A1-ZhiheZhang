import java.util.ArrayList;

/**
 * Runs a simple demonstration of the health appointment system.
 *
 * @author Zhihe Zhang
 */
public class AssignmentOne {

    public static void main(String[] args) {

        // Use all seven days so the examples also work at the weekend.
        String[] everyDay = {
                "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"
        };

        GeneralPractitioner gp1 = new GeneralPractitioner(
                101, "Dr Alice Green", everyDay, "Room 1", 8);
        GeneralPractitioner gp2 = new GeneralPractitioner(
                102, "Dr Ben Lee", everyDay, "Room 2", 5);
        GeneralPractitioner gp3 = new GeneralPractitioner(
                103, "Dr Clara Wong", everyDay);

        Specialist specialist1 = new Specialist(
                201, "Dr David Chen", everyDay, "Cardiology", "Heart Clinic");
        Specialist specialist2 = new Specialist(
                202, "Dr Emma Smith", everyDay, "Paediatrics");

        Nurse nurse1 = new Nurse(
                301, "Olivia Brown", everyDay, "Registered Nurse", true);
        Nurse nurse2 = new Nurse(
                302, "Noah Wilson", everyDay, "Enrolled Nurse", false);

        Dietitian dietitian1 = new Dietitian(
                401, "Sophie Taylor", everyDay, "Sports Nutrition", 4);
        Dietitian dietitian2 = new Dietitian(
                402, "Liam Martin", everyDay, "General Nutrition");

        // A parent-class array can hold all four types of professionals.
        HealthProfessional[] professionals = {
                gp1, gp2, gp3, specialist1, specialist2,
                nurse1, nurse2, dietitian1, dietitian2
        };

        System.out.println("Health professionals");
        for (HealthProfessional professional : professionals) {
            System.out.println(professional);
            System.out.println(professional.performDuty());
            System.out.println();
        }

        System.out.println("Other work examples");
        System.out.println(gp1.giveGeneralAdvice());
        System.out.println(specialist1.makeSpecialistAssessment());
        System.out.println(nurse1.takeBloodSample());
        System.out.println(nurse2.takeBloodSample());
        System.out.println(dietitian1.prepareMealPlan());

        System.out.println("\nClinic hours and appointment times");
        System.out.println("Today is " + HealthProfessional.getTodayName() + ".");
        System.out.println("Available start times: " + ClinicSchedule.getAvailableSlots());

        AppointmentManager manager = new AppointmentManager();

        Patient alice = new Patient("Alice Parker", "0412345678");
        Patient ben = new Patient("Ben Johnson", "0498765432");
        Patient mia = new Patient("Mia Chen", "0411223344");
        Patient ruby = new Patient("Ruby Davis", "0422334455");

        System.out.println("\nAdding appointments");

        // Add bookings out of order to make the later sorting example clearer.
        Appointment firstAppointment = new Appointment(alice, gp1, "11:30");
        manager.addAppointment(firstAppointment);
        manager.addAppointment(ben, gp2, "08:00");
        manager.addAppointment(new Appointment(mia, specialist1, "10:00"));
        manager.addAppointment("Victor Hall", "0433445566", nurse1, "09:00");
        manager.addAppointment(new Appointment(mia, dietitian1, "09:30"));
        manager.addAppointment(new Appointment(mia, nurse2, "12:00"));
        manager.addAppointment("Daniel White", "0444556677", gp3, "14:30");
        manager.addAppointment(new Appointment(ruby, specialist2, "15:30"));
        manager.addAppointment("Grace Kim", "0455667788", dietitian2, "13:00");

        // Two different doctors can have bookings at the same time.
        manager.addAppointment("James Cooper", "0466778899", specialist2, "08:00");

        System.out.println("\nOne full appointment record");
        System.out.println(firstAppointment);

        System.out.println("\nAppointments in the order they were added");
        manager.displayAppointments(manager.getAllAppointments());

        System.out.println("\nAll appointments sorted by time");
        manager.displayAppointments(manager.getSortedAppointments());

        System.out.println("\nAppointments for professional ID 201");
        manager.displayAppointments(manager.getAppointmentsByProfessionalId(201));

        System.out.println("\nAppointments for patient phone 0411223344");
        ArrayList<Appointment> miaAppointments =
                manager.getAppointmentsByPatientPhone("0411223344");
        manager.displayAppointments(miaAppointments);

        System.out.println("\nThe same patient's appointments sorted by time");
        manager.displayAppointments(manager.getSortedAppointments(miaAppointments));

        System.out.println("\nSearch with no matching result");
        manager.displayAppointments(manager.getAppointmentsByProfessionalId(999));

        System.out.println("\nBooking checks");

        // The first doctor already has an appointment at 11:30.
        manager.addAppointment("Peter Moore", "0477889900", gp1, "11:30");
        manager.addAppointment("Peter Moore", "0477889900", gp1, "08:15");
        manager.addAppointment("Peter Moore", "0477889900", gp1, "16:00");
        manager.addAppointment("Peter Moore", "not-a-phone", gp1, "13:30");
        manager.addAppointment((Appointment) null);
        manager.addAppointment("Peter Moore", "0477889900", null, "13:30");

        // Choose a different weekday to test booking someone who is off today.
        String otherDay = HealthProfessional.getTodayName().equals("Monday")
                ? "Tuesday" : "Monday";
        GeneralPractitioner offDutyDoctor = new GeneralPractitioner(
                104, "Dr Henry Adams", new String[] {otherDay});
        manager.addAppointment("Peter Moore", "0477889900", offDutyDoctor, "13:30");

        System.out.println("\nCancelling appointments");
        manager.cancelByProfessionalAndTime(101, "11:30");
        manager.cancelByPatientPhone("0411223344");

        // Show what happens when the requested booking does not exist.
        manager.cancelByProfessionalAndTime(999, "14:00");
        manager.cancelByProfessionalAndTime(102, "08:15");
        manager.cancelByPatientPhone("0400000000");

        System.out.println("\nAppointments after cancellation");
        manager.displayAppointments(manager.getSortedAppointments());

        System.out.println("\nChecking cancelled appointments");
        manager.displayAppointments(manager.getAppointmentsByProfessionalId(101));
        manager.displayAppointments(manager.getAppointmentsByPatientPhone("0411223344"));
    }
}
