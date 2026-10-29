/** A specialist doctor who works in a particular department. */
public class Specialist extends HealthProfessional {

    private final String specialty;
    private final String department;

    /** Creates a specialist with their specialty and department. */
    public Specialist(int id, String name, String[] workingDays,
                      String specialty, String department) {
        super(id, name, workingDays);
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialty is required.");
        }
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department is required.");
        }
        this.specialty = specialty.trim();
        this.department = department.trim();
    }

    /** Uses the specialty name as the department if none is given. */
    public Specialist(int id, String name, String[] workingDays, String specialty) {
        this(id, name, workingDays, specialty, specialty);
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getProfessionalType() {
        return "Specialist";
    }

    @Override
    public String performDuty() {
        return getName() + " assesses conditions in " + specialty + ".";
    }

    public String makeSpecialistAssessment() {
        return getName() + " performs a specialist assessment.";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Specialty: " + specialty
                + " | Department: " + department;
    }
}
