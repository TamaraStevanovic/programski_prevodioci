// generated with ast extension for cup
// version 0.8
// 26/7/2019 19:57:48


package rs.ac.bg.etf.pp1.ast;

public class EnumManyElements extends EnumElem {

    private EnumElem EnumElem;
    private EnumSingleElem EnumSingleElem;

    public EnumManyElements (EnumElem EnumElem, EnumSingleElem EnumSingleElem) {
        this.EnumElem=EnumElem;
        if(EnumElem!=null) EnumElem.setParent(this);
        this.EnumSingleElem=EnumSingleElem;
        if(EnumSingleElem!=null) EnumSingleElem.setParent(this);
    }

    public EnumElem getEnumElem() {
        return EnumElem;
    }

    public void setEnumElem(EnumElem EnumElem) {
        this.EnumElem=EnumElem;
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
        if(EnumElem!=null) EnumElem.accept(visitor);
        if(EnumSingleElem!=null) EnumSingleElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumElem!=null) EnumElem.traverseTopDown(visitor);
        if(EnumSingleElem!=null) EnumSingleElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumElem!=null) EnumElem.traverseBottomUp(visitor);
        if(EnumSingleElem!=null) EnumSingleElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumManyElements(\n");

        if(EnumElem!=null)
            buffer.append(EnumElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumSingleElem!=null)
            buffer.append(EnumSingleElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumManyElements]");
        return buffer.toString();
    }
}
