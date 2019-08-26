// generated with ast extension for cup
// version 0.8
// 26/7/2019 19:57:48


package rs.ac.bg.etf.pp1.ast;

public class MultiplyOpTimes extends Mullop {

    public MultiplyOpTimes () {
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
        buffer.append("MultiplyOpTimes(\n");

        buffer.append(tab);
        buffer.append(") [MultiplyOpTimes]");
        return buffer.toString();
    }
}
