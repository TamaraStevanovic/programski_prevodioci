// generated with ast extension for cup
// version 0.8
// 12/0/2020 14:51:12


package rs.ac.bg.etf.pp1.ast;

public class EnumDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private EnumDeclName EnumDeclName;
    private EnumElem EnumElem;

    public EnumDecl (EnumDeclName EnumDeclName, EnumElem EnumElem) {
        this.EnumDeclName=EnumDeclName;
        if(EnumDeclName!=null) EnumDeclName.setParent(this);
        this.EnumElem=EnumElem;
        if(EnumElem!=null) EnumElem.setParent(this);
    }

    public EnumDeclName getEnumDeclName() {
        return EnumDeclName;
    }

    public void setEnumDeclName(EnumDeclName EnumDeclName) {
        this.EnumDeclName=EnumDeclName;
    }

    public EnumElem getEnumElem() {
        return EnumElem;
    }

    public void setEnumElem(EnumElem EnumElem) {
        this.EnumElem=EnumElem;
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
        if(EnumDeclName!=null) EnumDeclName.accept(visitor);
        if(EnumElem!=null) EnumElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumDeclName!=null) EnumDeclName.traverseTopDown(visitor);
        if(EnumElem!=null) EnumElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumDeclName!=null) EnumDeclName.traverseBottomUp(visitor);
        if(EnumElem!=null) EnumElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDecl(\n");

        if(EnumDeclName!=null)
            buffer.append(EnumDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumElem!=null)
            buffer.append(EnumElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDecl]");
        return buffer.toString();
    }
}
