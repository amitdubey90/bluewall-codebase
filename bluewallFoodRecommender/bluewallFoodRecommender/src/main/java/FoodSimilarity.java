import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodSimilarity {
    private int foodA;
    private int foodB;
    private float similarity;
}
