// generated with ast extension for cup
// version 0.8
// 26/7/2019 19:57:48


package rs.ac.bg.etf.pp1.ast;

public class EnumSingleDefDefault extends EnumSingleElem {

    private String enumName;

    public EnumSingleDefDefault (String enumName) {
        this.enumName=enumName;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName=enumName;
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
        buffer.append("EnumSingleDefDefault(\n");

        buffer.append(" "+tab+enumName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumSingleDefDefault]");
        return buffer.toString();
    }
}
