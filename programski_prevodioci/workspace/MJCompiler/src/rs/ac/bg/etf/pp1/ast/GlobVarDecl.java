// generated with ast extension for cup
// version 0.8
// 12/0/2020 14:51:12


package rs.ac.bg.etf.pp1.ast;

public class GlobVarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private GlobVarElem GlobVarElem;

    public GlobVarDecl (Type Type, GlobVarElem GlobVarElem) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.GlobVarElem=GlobVarElem;
        if(GlobVarElem!=null) GlobVarElem.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public GlobVarElem getGlobVarElem() {
        return GlobVarElem;
    }

    public void setGlobVarElem(GlobVarElem GlobVarElem) {
        this.GlobVarElem=GlobVarElem;
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
        if(Type!=null) Type.accept(visitor);
        if(GlobVarElem!=null) GlobVarElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(GlobVarElem!=null) GlobVarElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(GlobVarElem!=null) GlobVarElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobVarDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobVarElem!=null)
            buffer.append(GlobVarElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobVarDecl]");
        return buffer.toString();
    }
}
