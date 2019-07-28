// generated with ast extension for cup
// version 0.8
// 12/5/2019 23:8:3


package rs.ac.bg.etf.pp1.ast;

public class EnumIdentity extends EnumIdent {

    private String identity;

    public EnumIdentity (String identity) {
        this.identity=identity;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity=identity;
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
        buffer.append("EnumIdentity(\n");

        buffer.append(" "+tab+identity);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumIdentity]");
        return buffer.toString();
    }
}
