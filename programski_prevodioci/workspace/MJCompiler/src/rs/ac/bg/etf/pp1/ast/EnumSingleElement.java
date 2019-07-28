// generated with ast extension for cup
// version 0.8
// 12/5/2019 23:8:3


package rs.ac.bg.etf.pp1.ast;

public class EnumSingleElement extends EnumElem {

    private EnumSingleElem EnumSingleElem;

    public EnumSingleElement (EnumSingleElem EnumSingleElem) {
        this.EnumSingleElem=EnumSingleElem;
        if(EnumSingleElem!=null) EnumSingleElem.setParent(this);
    }

    public EnumSingleElem getEnumSingleElem() {
        return EnumSingleElem;
    }

    public void setEnumSingleElem(EnumSingleElem EnumSingleElem) {
        this.EnumSingleElem=EnumSingleElem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumSingleElem!=null) EnumSingleElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumSingleElem!=null) EnumSingleElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumSingleElem!=null) EnumSingleElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumSingleElement(\n");

        if(EnumSingleElem!=null)
            buffer.append(EnumSingleElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumSingleElement]");
        return buffer.toString();
    }
}
