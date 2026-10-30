/** A nurse with a qualification and blood-sampling permission. */
public class Nurse extends HealthProfessional {

    private final String qualification;
    private final boolean canTakeBloodSamples;

    /** Creates a nurse and records whether they can take blood samples. */
    public Nurse(int id, String name, String[] workingDays,
                 String qualification, boolean canTakeBloodSamples) {
        super(id, name, workingDays);
        if (qualification == null || qualification.trim().isEmpty()) {
            throw new IllegalArgumentException("Nursing qualification is required.");
        }
        this.qualification = qualification.trim();
        this.canTakeBloodSamples = canTakeBloodSamples;
    }

    public Nurse(int id, String name, String[] workingDays, String qualification) {
        this(id, name, workingDays, qualification, true);
    }

    public String getQualification() {
        return qualification;
    }

    public boolean canTakeBloodSamples() {
        return canTakeBloodSamples;
    }

    @Override
    public String getProfessionalType() {
        return "Nurse";
    }

    @Override
    public String performDuty() {
        return getName() + " provides nursing care.";
    }

    public String takeBloodSample() {
        // Not every nurse in the example has permission for this task.
        if (canTakeBloodSamples) {
            return getName() + " takes a blood sample.";
        }
        return getName() + " is not authorised to take blood samples.";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Qualification: " + qualification
                + " | Blood sampling: " + (canTakeBloodSamples ? "Yes" : "No");
    }
}
