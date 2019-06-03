// generated with ast extension for cup
// version 0.8
// 29/4/2019 19:31:16


package rs.ac.bg.etf.pp1.ast;

public class GlobVarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String varType;
    private GlobVarElem GlobVarElem;

    public GlobVarDecl (String varType, GlobVarElem GlobVarElem) {
        this.varType=varType;
        this.GlobVarElem=GlobVarElem;
        if(GlobVarElem!=null) GlobVarElem.setParent(this);
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType=varType;
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
        if(GlobVarElem!=null) GlobVarElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobVarElem!=null) GlobVarElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobVarElem!=null) GlobVarElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobVarDecl(\n");

        buffer.append(" "+tab+varType);
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
