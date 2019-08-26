// generated with ast extension for cup
// version 0.8
// 25/7/2019 19:10:15


package rs.ac.bg.etf.pp1.ast;

public class EnumSingleDef extends EnumSingleElem {

    private String enumName;
    private Integer val;

    public EnumSingleDef (String enumName, Integer val) {
        this.enumName=enumName;
        this.val=val;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName=enumName;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val=val;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumSingleDef(\n");

        buffer.append(" "+tab+enumName);
        buffer.append("\n");

        buffer.append(" "+tab+val);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumSingleDef]");
        return buffer.toString();
    }
}
