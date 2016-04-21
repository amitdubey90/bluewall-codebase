import java.util.ArrayList;
import java.util.List;

public class Runner {
    private List<FoodSimilarity> similarities;
    private FoodDatabaseHelper dbDumper;
    private FoodItemJaccardSimilarity sim;

    Runner() {
        similarities = new ArrayList<FoodSimilarity>();
        sim = new FoodItemJaccardSimilarity();
        dbDumper = new FoodDatabaseHelper();
    }

    public void run() {
        List<FoodItem> allItems = dbDumper.getAllFoodItems();
        FoodSimilarity fs = null;

        for (int i = 0; i < allItems.size(); i++) {
            FoodItem itemA = allItems.get(i);
            for (int j = i + 1; j < allItems.size(); j++) {
                if (i == j)
                    continue;
                FoodItem itemB = allItems.get(j);

                float s = sim.doSimilarity(itemA, itemB);
                if (s > 0) {
                    fs = FoodSimilarity.builder()
                            .foodA(itemA.getFoodId())
                            .foodB(itemB.getFoodId())
                            .similarity(s)
                            .build();
                }

                similarities.add(fs);

                if (similarities.size() == 10000) {
                    dbDumper.dumpSimilarities(similarities);
                    similarities.clear();
                }
            }
        }
        dbDumper.dumpSimilarities(similarities);
    }

    public static void main(String[] args) {

        new Runner().run();
    }
}
