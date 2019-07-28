package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	public void visit(PrintStatementOneArg printStatementOneArg) {
		int len = printStatementOneArg.getN2();
		Code.loadConst(len);
		Code.put(Code.print);
	}
	
	public void visit(FactorConst factorConst) {
		int val = factorConst.getTypeConst().struct.getKind();
		Code.loadConst(val);
	}
	
	public void visit(FactorDesignator factorDesignator) {
		Code.load(factorDesignator.getDesignator().obj);
	}
	
	public void visit(MethodVoidAndName methodVoidAndName) {
		methodVoidAndName.obj.setAdr(Code.pc);
		if (methodVoidAndName.obj.getName().equals("main")) {
			Code.mainPc = Code.pc;
		}
		int fp = 0;
		int v = 0;
		Code.put(Code.enter);
		Code.put(fp);
		Code.put(v);
	}
	
	public void visit(MethodDeclWithoutFormPars methodDeclWithoutFormPars) {
		Code.put(Code.exit);
		if (methodDeclWithoutFormPars.getMethodTypeName().obj.getType() == Tab.noType) {
			Code.put(Code.return_);
		}
	}
	
	public void visit(DesignatorStmtAssign designatorStmtAssign) {
		Obj designator = designatorStmtAssign.getDesignator().obj;
		Code.store(designator);
	}
	
	/*
	
	private int varCount;
	
	private int paramCnt;
	
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	@Override
	public void visit(MethodTypeName MethodTypeName) {
		if ("main".equalsIgnoreCase(MethodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		MethodTypeName.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = MethodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	@Override
	public void visit(VarDecl VarDecl) {
		varCount++;
	}

	@Override
	public void visit(FormalParamDecl FormalParam) {
		paramCnt++;
	}	
	
	@Override
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnExpr ReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnNoExpr ReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Assignment Assignment) {
		Code.store(Assignment.getDesignator().obj);
	}
	
	@Override
	public void visit(Const Const) {
		Code.load(new Obj(Obj.Con, "$", Const.struct, Const.getN1(), 0));
	}
	
	@Override
	public void visit(Designator Designator) {
		SyntaxNode parent = Designator.getParent();
		if (Assignment.class != parent.getClass() && FuncCall.class != parent.getClass()) {
			Code.load(Designator.obj);
		}
	}
	
	@Override
	public void visit(FuncCall FuncCall) {
		Obj functionObj = FuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc; 
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(PrintStmt PrintStmt) {
		Code.put(Code.const_5);
		Code.put(Code.print);
	}
	
	@Override
	public void visit(AddExpr AddExpr) {
		Code.put(Code.add);
	}
	
	*/
}
