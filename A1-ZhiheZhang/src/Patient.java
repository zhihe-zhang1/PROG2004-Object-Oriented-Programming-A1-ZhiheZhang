/** Stores a patient's name and mobile number. */
public class Patient {

    private final String name;
    private final String mobileNumber;

    /** Checks the patient details when a new patient is created. */
    public Patient(String name, String mobileNumber) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be empty.");
        }
        if (!isValidPhone(mobileNumber)) {
            throw new IllegalArgumentException("Mobile number must contain 8 to 15 digits.");
        }
        this.name = name.trim();
        this.mobileNumber = mobileNumber.trim();
    }

    /** Checks that a mobile number contains only digits. */
    private boolean isValidPhone(String number) {
        if (number == null) {
            return false;
        }
        // Keep the number as text because it may begin with zero.
        String trimmed = number.trim();
        if (trimmed.length() < 8 || trimmed.length() > 15) {
            return false;
        }
        for (int i = 0; i < trimmed.length(); i++) {
            char character = trimmed.charAt(i);
            if (character < '0' || character > '9') {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public String toString() {
        return "Patient: " + name + " | Mobile: " + mobileNumber;
    }
}
