// generated with ast extension for cup
// version 0.8
// 29/4/2019 19:31:16


package rs.ac.bg.etf.pp1.ast;

public class FormalParamDef extends FormParam {

    private String typeName;
    private String I1;

    public FormalParamDef (String typeName, String I1) {
        this.typeName=typeName;
        this.I1=I1;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName=typeName;
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
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
        buffer.append("FormalParamDef(\n");

        buffer.append(" "+tab+typeName);
        buffer.append("\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParamDef]");
        return buffer.toString();
    }
}
