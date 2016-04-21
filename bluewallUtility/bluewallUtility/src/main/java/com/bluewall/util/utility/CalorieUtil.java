package com.bluewall.util.utility;

import com.bluewall.util.common.ActivityLevel;
import com.bluewall.util.common.Gender;

public class CalorieUtil {

    /**
     * Calculates and returns the daily calorific needs of a
     * person. This doesn't involve energy burnt through exercising.
     * 
     * @param weightInKg person's weight in kilo grams.
     * @param heightInCm person's height in centimeters.
     * @param age in years
     * @param gender
     * @param activityLevel
     * @return calories needed everyday.
     */
    public static double calculateDailyCalorieNeeds(float weightInKg, float heightInCm,
                                             int age, Gender gender, ActivityLevel activityLevel) {
        return calculateBMR(weightInKg, heightInCm, age, gender) * activityLevel.getActivityFactor();
    }

    private static double calculateBMR(float weightInKg, float heightInCm, int age, Gender gender) {
        return 9.99 * weightInKg + 6.25 * heightInCm - 4.92 * age + gender.getBRMConstant();
    }
}
