// generated with ast extension for cup
// version 0.8
// 27/7/2019 19:26:53


package rs.ac.bg.etf.pp1.ast;

public class SingleConstElem implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String constName;
    private TypeConst TypeConst;

    public SingleConstElem (String constName, TypeConst TypeConst) {
        this.constName=constName;
        this.TypeConst=TypeConst;
        if(TypeConst!=null) TypeConst.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public TypeConst getTypeConst() {
        return TypeConst;
    }

    public void setTypeConst(TypeConst TypeConst) {
        this.TypeConst=TypeConst;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("SingleConstElem(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(TypeConst!=null)
            buffer.append(TypeConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstElem]");
        return buffer.toString();
    }
}
