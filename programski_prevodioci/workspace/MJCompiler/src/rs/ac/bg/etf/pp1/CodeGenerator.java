package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int varCount;

	private int paramCnt;

	private int mainPc;
	private boolean returnDetected;// proveriti za primenu

	private int add = 1, sub = 2, mul = 3, div = 4, mod = 5;

	public int getMainPc() {
		return mainPc;
	}

	/////////////////// METHOD
	/////////////////// (4)///////////////////////////////////////////////////////
	public void visit(MethodTypeAndName methodTypeAndName) {
		methodTypeAndName.obj.setAdr(Code.pc);
		if (methodTypeAndName.obj.getName().equals("main")) {
			Code.mainPc = Code.pc;
		}

		int fp = 0; // uradi jednom prilikom
		int v = methodTypeAndName.obj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fp);
		Code.put(v);
	}

	public void visit(MethodVoidAndName methodVoidAndName) {
		methodVoidAndName.obj.setAdr(Code.pc);
		if (methodVoidAndName.obj.getName().equals("main")) {
			Code.mainPc = Code.pc;
		}
		int fp = 0; // uradi jednom prilikom
		int v = methodVoidAndName.obj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fp);
		Code.put(fp + v);
		// potrebo je staviti i formalne parametre i lokalne var

	}

	public void visit(MethodDeclWithoutFormPars methodDeclWithoutFormPars) {
		if (methodDeclWithoutFormPars.getMethodTypeName().obj.getType() == Tab.noType) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
	}

	public void visit(MethodDeclWithFormPars methodDeclWithFormPars) {
		if (methodDeclWithFormPars.getMethodTypeName().obj.getType() == Tab.noType) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
	}

	///////////////////// PRINT /////////////////////////////////////
	public void visit(PrintStatementOneArg printStatementOneArg) {
		int len = printStatementOneArg.getN2();
		// Code.loadConst(len);
		// Code.put(Code.print);
		if (printStatementOneArg.getExpr().struct.getKind() == Tab.intType.getKind()) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}

	public void visit(PrintStatementTwoArg print) {
		if (print.getExpr().struct.getKind() == Tab.charType.getKind()) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(5);
			Code.put(Code.print);
		}
	}

//////////////////POZIVI FUNKCIJA ///////////////////////////////////////
	public void visit(DesignatorStmtWithoutParams call) {
		Obj obj = call.getDesignator().obj;
		int adr = obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put4(adr);
	}

	public void visit(FactorDesignatorWithParams call) {
		Obj obj = call.getDesignator().obj;
		int adr = obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put4(adr);
	}
//	@Override
//	public void visit(FuncCall FuncCall) {
//		Obj functionObj = FuncCall.getDesignator().obj;
//		int offset = functionObj.getAdr() - Code.pc; 
//		Code.put(Code.call);//
//		Code.put2(offset);//omogucava skok na prvu instr
//	}

	////////////////////////// return expr ////////////////////////////

	public void visit(ReturnStmtWithoutExpr ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		returnDetected = true;
	}

	public void visit(ReturnStmtWithExpr ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		returnDetected = true;
	}

//	public void visit(AddOperationPlus addOperationPlus) {
//		Code.put(Code.add);
//	}
	public void visit(AddOperationPlus op) {
		op.struct = new Struct(1);

	}

	public void visit(AddOperationMinus op) {
		op.struct = new Struct(2);
	}

	public void visit(MultiplyOpTimes op) {
		op.struct = new Struct(3);
	}

	public void visit(MultiplyOpDivide op) {
		op.struct = new Struct(4);
	}

	public void visit(MultiplyOpMod op) {
		op.struct = new Struct(5);
	}

	public void visit(ExprList exprList) {
		if (exprList.getAddop().struct.getKind() == 1) {
			Code.put(Code.add);
		} else if (exprList.getAddop().struct.getKind() == 2) {
			Code.put(Code.sub);
		}
	}

	public void visit(TermManyFactors termManyFactors) {
		if (termManyFactors.getMullop().struct.getKind() == 3) {
			Code.put(Code.mul);
		} else if (termManyFactors.getMullop().struct.getKind() == 4) {
			Code.put(Code.div);
		} else if (termManyFactors.getMullop().struct.getKind() == 5) {
			Code.put(Code.rem);
		}
	}

	public void visit(ReadStatement readStmt) {
		Code.put(Code.read);

		Code.store(readStmt.getDesignator().obj);
	}

	public void visit(DesignatorStmtINC increment) {
		Obj obj = Tab.insert(Obj.Con, "", Tab.intType);
		obj.setAdr(1);
		Code.load(obj);
		Code.put(Code.add);

		Code.store(increment.getDesignator().obj);
	}

	public void visit(DesignatorStmtDEC decrement) {
		Obj obj = Tab.insert(Obj.Con, "", Tab.intType);
		obj.setAdr(1);
		Code.load(obj);// stavljamo keca na expr stack
		Code.put(Code.sub);// satvljamo operator za oduzimanje
		Code.store(decrement.getDesignator().obj);// cuvamo u designatoru
	}

	////////// uradjeno_gore/////////////////////////////////////////

	public void visit(ExprMinusTerm exprMinusTerm) {
		// ako je usao u ovaj cvor treba da na stack stavi negativnu vrednsot od one
		// koja je tu trenutno
		Code.put(Code.neg);
	}

	public void visit(DesignatorStmtAssign designatorStmtAssign) {
		// na expr stacku je izraz i treba da ga sacuvam u designator
		// ja mislim da ovde sigurno ono sto je u designatoru t
		Obj designator = designatorStmtAssign.getDesignator().obj;
		// uzecu pretpostavku da je expr vec izracunat i da se nalazi na expr stacku
		// sada samo treba da to store-ujem u designator
		Code.store(designator);
	}

	public void visit(EnumSingleElement enumSingleElement) {
		// ovo se valjda radi samo kad se enum nadje sa desne strane tj u okviru expr-a
		Obj o = enumSingleElement.getEnumSingleElem().obj;
		Code.load(o);
	}

	/*
	 * ovo ispod su sve smene kada se ovim cvorovima pristupa iz grane expr-a tj sve
	 * mora da bude sastavni deo expr-a
	 */
	public void visit(DesignatorSingle designatorSingle) {
		//

	}

	public void visit(DesignatorWithDOT designatorWithDOT) {

	}

	public void visit(DesignatorWithExpr designatorWithExpr) {
		// nisam sigurna da ovde treba bilo sta da se uradi
	}

	public void visit(FactorConst factorConst) {
		int val = factorConst.getTypeConst().struct.getKind();
		Code.loadConst(val);
		// ?pitanje:
		// da li su ovim gore obuhvaceni svi slucajevi??
	}

	public void visit(FactorDesignator factorDesignator) {
		// to je cvor koji treba da se stavlja na stack samo kad se nadje kao deo Term-a
		// tj Expr-a
		// zato je valjda dovoljno samo da se uradi load
		Code.load(factorDesignator.getDesignator().obj);
	}
	
	/////////// iznad je u progresu//

	// ako se designator odnosi na globalnu prom, onda ce se generisati fja
	// getStatic
	// ako se odnosi na lokalnu onda instr load
	// ovako smo obezbedili ako se promenljiva nadje u nekom izrazu da n
	// }

	// @Override public void visit(VarDecl VarDecl) { varCount++; }
	
}
