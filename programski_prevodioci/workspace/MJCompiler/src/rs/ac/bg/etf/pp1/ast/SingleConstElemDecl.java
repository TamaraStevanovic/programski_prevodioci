// generated with ast extension for cup
// version 0.8
// 26/7/2019 19:57:48


package rs.ac.bg.etf.pp1.ast;

public class SingleConstElemDecl extends ConstElem {

    private SingleConstElem SingleConstElem;

    public SingleConstElemDecl (SingleConstElem SingleConstElem) {
        this.SingleConstElem=SingleConstElem;
        if(SingleConstElem!=null) SingleConstElem.setParent(this);
    }

    public SingleConstElem getSingleConstElem() {
        return SingleConstElem;
    }

    public void setSingleConstElem(SingleConstElem SingleConstElem) {
        this.SingleConstElem=SingleConstElem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleConstElem!=null) SingleConstElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleConstElem!=null) SingleConstElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleConstElem!=null) SingleConstElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstElemDecl(\n");

        if(SingleConstElem!=null)
            buffer.append(SingleConstElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstElemDecl]");
        return buffer.toString();
    }
}
