package cn.bmkp.jiang.droolscost;

/**
 * Created by jiang on 16/8/22.
 */
public class Car implements java.io.Serializable
{

    static final long serialVersionUID = 1L;

    @org.kie.api.definition.type.Description("1-100,2->1000")
    private java.lang.Integer carType;
    private int price;

    public Car()
    {
    }

    public java.lang.Integer getCarType()
    {
        return this.carType;
    }

    public void setCarType(java.lang.Integer carType)
    {
        this.carType = carType;
    }

    public int getPrice()
    {
        return this.price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public Car(java.lang.Integer carType, int price)
    {
        this.carType = carType;
        this.price = price;
    }

}