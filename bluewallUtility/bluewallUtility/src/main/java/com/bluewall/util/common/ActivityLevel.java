package com.bluewall.util.common;

public enum ActivityLevel {
    SEDENTARY(1.2),
    LIGHTLY_ACTIVE(1.375),
    MODERATELY_ACTIVE(1.55),
    VERY_ACTIVE(1.725),
    EXTREMELY_ACTIVE(1.99);

    private double activityFactor;

    ActivityLevel(double activityFactor) {
        this.activityFactor = activityFactor;
    }

    public double getActivityFactor() {
        return activityFactor;
    }
}
