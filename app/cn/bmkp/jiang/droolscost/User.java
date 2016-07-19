package cn.bmkp.jiang.droolscost;

/**
 * Created by jiang on 16/7/19.
 */
public class User implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    public Long uid;

    public Long distance;

    public Long waitTime;

    public Double cost;

    public User()
    {

    }

    public Long getUid()
    {
        return uid;
    }

    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public Long getDistance()
    {
        return distance;
    }

    public void setDistance(Long distance)
    {
        this.distance = distance;
    }

    public Long getWaitTime()
    {
        return waitTime;
    }

    public void setWaitTime(Long waitTime)
    {
        this.waitTime = waitTime;
    }

    public Double getCost()
    {
        return cost;
    }

    public void setCost(Double cost)
    {
        this.cost = cost;
    }

    public User(java.lang.Long uid, java.lang.Long distance,
                java.lang.Long waitTime, java.lang.Double cost)
    {
        this.uid = uid;
        this.distance = distance;
        this.waitTime = waitTime;
        this.cost = cost;
    }

}