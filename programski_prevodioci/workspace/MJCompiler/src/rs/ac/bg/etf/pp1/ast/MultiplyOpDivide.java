// generated with ast extension for cup
// version 0.8
// 27/7/2019 19:26:53


package rs.ac.bg.etf.pp1.ast;

public class MultiplyOpDivide extends Mullop {

    public MultiplyOpDivide () {
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
        buffer.append("MultiplyOpDivide(\n");

        buffer.append(tab);
        buffer.append(") [MultiplyOpDivide]");
        return buffer.toString();
    }
}
