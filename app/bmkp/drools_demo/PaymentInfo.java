package bmkp.drools_demo;

/**
 * Created by jiang on 16/7/19.
 */
public class PaymentInfo implements java.io.Serializable {

    static final long serialVersionUID = 1L;
    private int moneyAmount = 0;
    private String decisionPath = "";

    public void setMoneyAmount(int amount) {
        this.moneyAmount = amount;
    }

    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    public void setDecisionPath(String path) {
        this.decisionPath = path;
    }

    public String getDecisionPath() {
        return this.decisionPath;
    }

}