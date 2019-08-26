// generated with ast extension for cup
// version 0.8
// 25/7/2019 19:10:15


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String enumDeclName;

    public EnumDeclName (String enumDeclName) {
        this.enumDeclName=enumDeclName;
    }

    public String getEnumDeclName() {
        return enumDeclName;
    }

    public void setEnumDeclName(String enumDeclName) {
        this.enumDeclName=enumDeclName;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDeclName(\n");

        buffer.append(" "+tab+enumDeclName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDeclName]");
        return buffer.toString();
    }
}
