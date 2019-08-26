// generated with ast extension for cup
// version 0.8
// 26/7/2019 19:57:48


package rs.ac.bg.etf.pp1.ast;

public class ManyConstElemDecl extends ConstElem {

    private ConstElem ConstElem;
    private SingleConstElem SingleConstElem;

    public ManyConstElemDecl (ConstElem ConstElem, SingleConstElem SingleConstElem) {
        this.ConstElem=ConstElem;
        if(ConstElem!=null) ConstElem.setParent(this);
        this.SingleConstElem=SingleConstElem;
        if(SingleConstElem!=null) SingleConstElem.setParent(this);
    }

    public ConstElem getConstElem() {
        return ConstElem;
    }

    public void setConstElem(ConstElem ConstElem) {
        this.ConstElem=ConstElem;
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
        if(ConstElem!=null) ConstElem.accept(visitor);
        if(SingleConstElem!=null) SingleConstElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstElem!=null) ConstElem.traverseTopDown(visitor);
        if(SingleConstElem!=null) SingleConstElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstElem!=null) ConstElem.traverseBottomUp(visitor);
        if(SingleConstElem!=null) SingleConstElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ManyConstElemDecl(\n");

        if(ConstElem!=null)
            buffer.append(ConstElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleConstElem!=null)
            buffer.append(SingleConstElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ManyConstElemDecl]");
        return buffer.toString();
    }
}
