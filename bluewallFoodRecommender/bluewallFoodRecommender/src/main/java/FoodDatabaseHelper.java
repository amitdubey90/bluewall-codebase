import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FoodDatabaseHelper {

    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> resultList = new ArrayList<FoodItem>();

        String query = "select * from FoodLabel";
        Connection connection = MySqlConnectionManager.getConnection();

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            FoodItem item = null;
            while (rs.next()) {
                item = new FoodItem(rs.getInt("foodId"));
                item.addPrefence(rs.getInt("isSweet"));
                item.addPrefence(rs.getInt("isSour"));
                item.addPrefence(rs.getInt("isHot"));
                item.addPrefence(rs.getInt("isFruit"));
                item.addPrefence(rs.getInt("isCheesy"));
                item.addPrefence(rs.getInt("isGreens"));
                item.addPrefence(rs.getInt("isFried"));
                item.addPrefence(rs.getInt("isMeat"));
                item.addPrefence(rs.getInt("isBreakfast"));

                resultList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void dumpSimilarities(List<FoodSimilarity> list) {
        Preconditions.checkNotNull(list);
        try {
            log.info("Dumping {} records", list.size());
            String query = "insert into FoodSimilarity (foodA, foodB, similarity) values (?, ?, ?)";

            Connection connection = MySqlConnectionManager.getConnection();

            PreparedStatement pst = connection.prepareStatement(query);
            int colIdx = 1;
            FoodSimilarity fs = null;
            for (int i = 0; i < list.size(); i++) {
                fs = list.get(i);
                if (fs != null) {
                    colIdx = 1;
                    pst.setInt(colIdx++, fs.getFoodA());
                    pst.setInt(colIdx++, fs.getFoodB());
                    pst.setFloat(colIdx, fs.getSimilarity());
                    pst.addBatch();
                } else {
                    log.warn("Null similarity!!");
                }
            }
            pst.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
