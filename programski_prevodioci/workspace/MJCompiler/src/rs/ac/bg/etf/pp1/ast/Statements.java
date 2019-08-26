// generated with ast extension for cup
// version 0.8
// 25/7/2019 19:10:15


package rs.ac.bg.etf.pp1.ast;

public class Statements extends StmtList {

    private StmtList StmtList;
    private Stmt Stmt;

    public Statements (StmtList StmtList, Stmt Stmt) {
        this.StmtList=StmtList;
        if(StmtList!=null) StmtList.setParent(this);
        this.Stmt=Stmt;
        if(Stmt!=null) Stmt.setParent(this);
    }

    public StmtList getStmtList() {
        return StmtList;
    }

    public void setStmtList(StmtList StmtList) {
        this.StmtList=StmtList;
    }

    public Stmt getStmt() {
        return Stmt;
    }

    public void setStmt(Stmt Stmt) {
        this.Stmt=Stmt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StmtList!=null) StmtList.accept(visitor);
        if(Stmt!=null) Stmt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StmtList!=null) StmtList.traverseTopDown(visitor);
        if(Stmt!=null) Stmt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StmtList!=null) StmtList.traverseBottomUp(visitor);
        if(Stmt!=null) Stmt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statements(\n");

        if(StmtList!=null)
            buffer.append(StmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Stmt!=null)
            buffer.append(Stmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statements]");
        return buffer.toString();
    }
}
