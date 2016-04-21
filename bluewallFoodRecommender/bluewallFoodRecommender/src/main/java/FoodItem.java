import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoodItem {

    private int foodId;
    private List<Integer> preferences;

    FoodItem(int foodId) {
        this.foodId = foodId;
        this.preferences = new ArrayList<Integer>();
    }

    public void addPrefence(int p) {
        this.preferences.add(p);
    }
}
