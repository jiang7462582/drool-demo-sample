package modles;

/**
 * Created by jiang on 16/7/25.
 */
public class UserCostRule {
    public static Double userCost(Long distance ,Long waitTime){

        Double distanceCost = distance<=3 ? 8 :(distance-3)*1.5;
        Double timeCost = distance<=5 ? 0 : (waitTime-5)*0.1;

        return distanceCost+timeCost;
    }
}
