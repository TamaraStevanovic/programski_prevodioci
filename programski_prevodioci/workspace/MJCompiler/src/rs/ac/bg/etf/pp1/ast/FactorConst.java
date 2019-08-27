// generated with ast extension for cup
// version 0.8
// 27/7/2019 19:26:53


package rs.ac.bg.etf.pp1.ast;

public class FactorConst extends Factor {

    private TypeConst TypeConst;

    public FactorConst (TypeConst TypeConst) {
        this.TypeConst=TypeConst;
        if(TypeConst!=null) TypeConst.setParent(this);
    }

    public TypeConst getTypeConst() {
        return TypeConst;
    }

    public void setTypeConst(TypeConst TypeConst) {
        this.TypeConst=TypeConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TypeConst!=null) TypeConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypeConst!=null) TypeConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypeConst!=null) TypeConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorConst(\n");

        if(TypeConst!=null)
            buffer.append(TypeConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorConst]");
        return buffer.toString();
    }
}
