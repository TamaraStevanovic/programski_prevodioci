// generated with ast extension for cup
// version 0.8
// 27/7/2019 19:26:53


package rs.ac.bg.etf.pp1.ast;

public class DesignatorName extends DesignatorIdentity {

    private String designatorName;

    public DesignatorName (String designatorName) {
        this.designatorName=designatorName;
    }

    public String getDesignatorName() {
        return designatorName;
    }

    public void setDesignatorName(String designatorName) {
        this.designatorName=designatorName;
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
        buffer.append("DesignatorName(\n");

        buffer.append(" "+tab+designatorName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorName]");
        return buffer.toString();
    }
}
