import com.google.common.base.Preconditions;

import java.util.List;

/**
 *
 */
public class FoodItemJaccardSimilarity {

    /**
     * @param item1
     * @param item2
     * @return
     */
    public float doSimilarity(FoodItem item1, FoodItem item2) {
        Preconditions.checkNotNull(item1);
        Preconditions.checkNotNull(item2);

        float similarity = 0;

        List<Integer> preference1 = item1.getPreferences();
        List<Integer> preference2 = item2.getPreferences();
        int length = preference1.size() > preference2.size() ?
                preference1.size() : preference2.size();

        float numerator = 0;
        float denominator = 0;
        for (int i = 0; i < length; i++) {
            // todo
            numerator += preference1.get(i) & preference2.get(i);
            denominator += preference1.get(i) | preference2.get(i);
        }
        try {
            similarity = numerator / denominator;
        } catch (Exception e) {e.printStackTrace();}
        return similarity;
    }

    public static void main(String[] args) {
        FoodItemJaccardSimilarity fj = new FoodItemJaccardSimilarity();
        FoodItem item1 = new FoodItem(1);
        FoodItem item2 = new FoodItem(2);
        item1.addPrefence(1);
        item1.addPrefence(0);
        item1.addPrefence(1);
        item1.addPrefence(1);
        item2.addPrefence(0);
        item2.addPrefence(1);
        item2.addPrefence(1);
        item2.addPrefence(0);
        System.out.println(fj.doSimilarity(item1, item2));
    }
}
