package cn.bmkp.jiang.droolscost;

/**
 * Created by jiang on 16/8/19.
 */
public class Person implements java.io.Serializable
{

    static final long serialVersionUID = 1L;

    private java.lang.Integer age;
    @org.kie.api.definition.type.Description("1->young,2->old")
    private java.lang.Integer type;

    public Person()
    {
    }

    public java.lang.Integer getAge()
    {
        return this.age;
    }

    public void setAge(java.lang.Integer age)
    {
        this.age = age;
    }

    public java.lang.Integer getType()
    {
        return this.type;
    }

    public void setType(java.lang.Integer type)
    {
        this.type = type;
    }

    public Person(java.lang.Integer age, java.lang.Integer type)
    {
        this.age = age;
        this.type = type;
    }

}