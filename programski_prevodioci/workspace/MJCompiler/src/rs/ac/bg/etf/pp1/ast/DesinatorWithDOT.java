// generated with ast extension for cup
// version 0.8
// 12/5/2019 23:8:3


package rs.ac.bg.etf.pp1.ast;

public class DesinatorWithDOT extends Designator {

    private EnumIdent EnumIdent;
    private EnumValue EnumValue;

    public DesinatorWithDOT (EnumIdent EnumIdent, EnumValue EnumValue) {
        this.EnumIdent=EnumIdent;
        if(EnumIdent!=null) EnumIdent.setParent(this);
        this.EnumValue=EnumValue;
        if(EnumValue!=null) EnumValue.setParent(this);
    }

    public EnumIdent getEnumIdent() {
        return EnumIdent;
    }

    public void setEnumIdent(EnumIdent EnumIdent) {
        this.EnumIdent=EnumIdent;
    }

    public EnumValue getEnumValue() {
        return EnumValue;
    }

    public void setEnumValue(EnumValue EnumValue) {
        this.EnumValue=EnumValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumIdent!=null) EnumIdent.accept(visitor);
        if(EnumValue!=null) EnumValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumIdent!=null) EnumIdent.traverseTopDown(visitor);
        if(EnumValue!=null) EnumValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumIdent!=null) EnumIdent.traverseBottomUp(visitor);
        if(EnumValue!=null) EnumValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesinatorWithDOT(\n");

        if(EnumIdent!=null)
            buffer.append(EnumIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumValue!=null)
            buffer.append(EnumValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesinatorWithDOT]");
        return buffer.toString();
    }
}
