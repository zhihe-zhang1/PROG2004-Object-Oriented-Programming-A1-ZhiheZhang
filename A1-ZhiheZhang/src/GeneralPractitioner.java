/** A general practitioner, with a room and years of experience. */
public class GeneralPractitioner extends HealthProfessional {

    private final String consultationRoom;
    private final int yearsOfExperience;

    /** Creates a GP with their normal details and consultation room. */
    public GeneralPractitioner(int id, String name, String[] workingDays,
                               String consultationRoom, int yearsOfExperience) {
        super(id, name, workingDays);
        if (consultationRoom == null || consultationRoom.trim().isEmpty()) {
            throw new IllegalArgumentException("Consultation room is required.");
        }
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Experience cannot be negative.");
        }
        this.consultationRoom = consultationRoom.trim();
        this.yearsOfExperience = yearsOfExperience;
    }

    /** Uses a default room and experience if they are not supplied. */
    public GeneralPractitioner(int id, String name, String[] workingDays) {
        this(id, name, workingDays, "General Clinic", 0);
    }

    public String getConsultationRoom() {
        return consultationRoom;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public String getProfessionalType() {
        return "General Practitioner";
    }

    @Override
    public String performDuty() {
        return getName() + " provides a general consultation and may prescribe medication.";
    }

    public String giveGeneralAdvice() {
        return getName() + " gives general health advice.";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Room: " + consultationRoom
                + " | Experience: " + yearsOfExperience + " years";
    }
}
