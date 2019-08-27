// generated with ast extension for cup
// version 0.8
// 27/7/2019 19:26:53


package rs.ac.bg.etf.pp1.ast;

public class DesignatorWithDOT extends Designator {

    private String enumIdentity;
    private String enumVal;

    public DesignatorWithDOT (String enumIdentity, String enumVal) {
        this.enumIdentity=enumIdentity;
        this.enumVal=enumVal;
    }

    public String getEnumIdentity() {
        return enumIdentity;
    }

    public void setEnumIdentity(String enumIdentity) {
        this.enumIdentity=enumIdentity;
    }

    public String getEnumVal() {
        return enumVal;
    }

    public void setEnumVal(String enumVal) {
        this.enumVal=enumVal;
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
        buffer.append("DesignatorWithDOT(\n");

        buffer.append(" "+tab+enumIdentity);
        buffer.append("\n");

        buffer.append(" "+tab+enumVal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorWithDOT]");
        return buffer.toString();
    }
}
