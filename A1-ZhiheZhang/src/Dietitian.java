/** A dietitian who gives advice in a nutrition area. */
public class Dietitian extends HealthProfessional {

    private final String nutritionArea;
    private final int yearsOfExperience;

    /** Creates a dietitian with their nutrition area and experience. */
    public Dietitian(int id, String name, String[] workingDays,
                     String nutritionArea, int yearsOfExperience) {
        super(id, name, workingDays);
        if (nutritionArea == null || nutritionArea.trim().isEmpty()) {
            throw new IllegalArgumentException("Nutrition area is required.");
        }
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Experience cannot be negative.");
        }
        this.nutritionArea = nutritionArea.trim();
        this.yearsOfExperience = yearsOfExperience;
    }

    public Dietitian(int id, String name, String[] workingDays, String nutritionArea) {
        this(id, name, workingDays, nutritionArea, 0);
    }

    public String getNutritionArea() {
        return nutritionArea;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public String getProfessionalType() {
        return "Dietitian";
    }

    @Override
    public String performDuty() {
        return getName() + " gives advice about " + nutritionArea + ".";
    }

    public String prepareMealPlan() {
        return getName() + " prepares a personalised meal plan.";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Nutrition area: " + nutritionArea
                + " | Experience: " + yearsOfExperience + " years";
    }
}
