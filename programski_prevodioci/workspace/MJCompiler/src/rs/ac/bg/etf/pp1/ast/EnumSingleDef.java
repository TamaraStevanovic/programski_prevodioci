// generated with ast extension for cup
// version 0.8
// 12/5/2019 23:8:3


package rs.ac.bg.etf.pp1.ast;

public class EnumSingleDef extends EnumSingleElem {

    private String enumName;
    private Integer N1;

    public EnumSingleDef (String enumName, Integer N1) {
        this.enumName=enumName;
        this.N1=N1;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName=enumName;
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
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

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumSingleDef]");
        return buffer.toString();
    }
}
