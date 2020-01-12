package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import JFlex.Out;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.factory.SymbolTableFactory;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;
	// int printCallCount = 0;
	boolean returnFound = false;

	Struct currentType;

	private int lastEnumVal = 0;

	private boolean mainDef = false;
	private Obj currMethod = null;

	public static Map<String, List<Obj>> hashForm = new LinkedHashMap<String, List<Obj>>();

	private Struct returnStmtType = null;

	////////////////// DATE METODE //////////////////////////////////////////////
	Logger log = Logger.getLogger(getClass());
	Struct boolType;
	private Struct currentEnumType;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	/////////////////////////// PROGRAM //////////////////////////////////////////
	public void visit(Program program) {
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		if (mainDef == false)
			report_error("Funkcija main ne postoji", null);
	}

	public void visit(ProgName progName) {
		boolType = new Struct(Struct.Bool);
		Obj boolObj = Tab.insert(Obj.Type, "boolean", boolType);
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	/////////////////////////////////////////////////////////////////////////////
	public void visit(Type Type) {
		currentType = Tab.noType;
		String typeName = Type.getTypeName();
		Obj typeObj = Tab.find(typeName);
		if (typeObj != Tab.noObj) {
			if (typeObj.getKind() == Obj.Type) {
				currentType = typeObj.getType();
			} else {
				report_error(typeObj.getName() + "nije tip", Type);
			}
		} else {
			report_error("Nedefinisan tip " + typeName, Type);
		}

		Type.struct = currentType;
	}

	/* *************************************************** */
	public void visit(DesignatorSingle DesignatorSingle) {
		DesignatorSingle.obj = Tab.noObj;
		String name = DesignatorSingle.getName();
		Obj o = Tab.find(name);
		if (o != Tab.noObj) {
			// uspeh
			System.out.println("DesignatorSingle " + DesignatorSingle.getName());
			DesignatorSingle.obj = o;
		} else {
			report_error("Nije definisan simbol " + name, DesignatorSingle);
		}
	}

	public void visit(DesignatorWithDOT designatorWithDOT) {
		Obj obj = Tab.noObj;
		obj = Tab.find(designatorWithDOT.getEnumIdentity());
		if (obj == Tab.noObj || obj.getType().getKind() != Struct.Enum) {
			report_error("Ne postoji trazeni enum!", designatorWithDOT);
			return;
		}
		// nadjen je ovaj enum, sad treba proveriti i konstantu da li je unutar njega
		boolean constExist = false;
		Iterator<Obj> it = obj.getLocalSymbols().iterator();
		for (Obj o : obj.getLocalSymbols()) {
			if (o.getName().equals(designatorWithDOT.getEnumVal())) {
				constExist = true;
				designatorWithDOT.obj = o;
				report_info("Obradjuje se enum naredba", designatorWithDOT);
				return;
			}
		}
		designatorWithDOT.obj = new Obj(Obj.Var, " ", Tab.noType);
		report_error("Ne postoji trazena constanta u ovom enumu!", designatorWithDOT);
		return;

	}

	public void visit(DesignatorName designatorName) {
		// u njegov objektni cvor upisujemo cvor iz TSa
		designatorName.obj = Tab.find(designatorName.getDesignatorName());

	}

	public void visit(DesignatorWithExpr designatorWithExpr) {
		Obj o = designatorWithExpr.getDesignatorIdentity().obj;
		// nasao ga je u TS
		if (o.getType().getKind() == Struct.Array) {
			designatorWithExpr.obj = new Obj(Obj.Elem, o.getName(), o.getType().getElemType());
			if (designatorWithExpr.getExpr().struct != Tab.intType
					&& designatorWithExpr.getExpr().struct.getKind() != Struct.Enum) {
				report_error("Expression nije tipa integer ", designatorWithExpr);
				return;
			}
			report_info("Pronadjen je simbol niza ", designatorWithExpr);
		} else {
			report_error(o.getName() + " nije niz", designatorWithExpr);

		}

	}
	/* ********** CONSTANTS *********** */

	public void visit(SingleConstElem SingleConstElem) {
		String name = SingleConstElem.getConstName();
		if (Tab.find(name) != Tab.noObj) {
			report_error("Visestruko definisanje simbola " + name, SingleConstElem);
		}
		Obj o = Tab.insert(Obj.Con, name, currentType);
		int address = SingleConstElem.getTypeConst().struct.getKind();
		o.setAdr(address);

	}

	public void visit(CharacterConst CharacterConst) {
		int value = CharacterConst.getValue();
		Struct s = new Struct(value);
		s.setElementType(Tab.charType);
		CharacterConst.struct = s;
	}

	public void visit(BooleanConst BooleanConst) {
		boolean value = BooleanConst.getValue();
		Struct s = new Struct(value ? 1 : 0);
		s.setElementType(boolType);
		BooleanConst.struct = s;
	}

	public void visit(NumberConst NumberConst) {
		// kada posecujemo ovaj cvor napravimo struct cvor sa njegovom vrednoscu
		// i onda ce se sa SingleConstElem.getTypeConst().struct.getKind();
		// dohvatiti ta vrednost
		int value = NumberConst.getValue();
		// System.out.println("Stampam vrednosttt" + value);
		Struct s = new Struct(value);
		s.setElementType(Tab.intType);
		NumberConst.struct = s;
	}

	/* ************** VARIJABLE **************** */
	public void visit(GlobVarSingleDefWithoutBracket GlobVarSingleDefWithoutBracket) {
		String name = GlobVarSingleDefWithoutBracket.getVarName();
		if (Tab.currentScope().findSymbol(name) == null) {
			GlobVarSingleDefWithoutBracket.obj = Tab.insert(Obj.Var, name, currentType);
		} else {
			report_error("Visestruko definisanje simbola " + name, GlobVarSingleDefWithoutBracket);
			GlobVarSingleDefWithoutBracket.obj = Tab.noObj;
		}
	}

	// izmeniti
	public void visit(GlobVarSingleDefWithBracket GlobVarSingleDefWithBracket) {
		String name = GlobVarSingleDefWithBracket.getVarName();
		if (Tab.currentScope().findSymbol(name) == null) {
			GlobVarSingleDefWithBracket.obj = Tab.insert(Obj.Var, name, new Struct(Struct.Array, currentType));
		} else {
			report_error("Visestruko definisanje simbola " + name, GlobVarSingleDefWithBracket);
			GlobVarSingleDefWithBracket.obj = Tab.noObj;
		}
	}

	/* ***************** METODE ***************** */
	public void visit(MethodTypeAndName methodTypeAndName) {
		Struct type = methodTypeAndName.getType().struct;
		String name = methodTypeAndName.getMethodName();
		if (Tab.find(name) != Tab.noObj) {
			report_error("U programu je vec definisana metoda " + name, methodTypeAndName);
		}
		currMethod = methodTypeAndName.obj = Tab.insert(Obj.Meth, name, type);
		Tab.openScope();
		returnFound = false;
	}

	public void visit(MethodVoidAndName methodVoidAndName) {
		// proveriti da li je pitanju metoda main()
		// ona sme da bude definisana samo jednom
		Struct type = Tab.noType;
		String name = methodVoidAndName.getMethodName();
		if (name.contentEquals("main")) {
			mainDef = true;
			// da ubelezimo ako smo definisali main metod
		}

		if (Tab.find(name) != Tab.noObj) {
			report_error("U programu je vec definisana metoda " + name, methodVoidAndName);
		}
		currMethod = methodVoidAndName.obj = Tab.insert(Obj.Meth, name, type);
		Tab.openScope();
		returnFound = false;
	}

	public void visit(MethodDeclWithoutFormPars methodDeclWithoutFormPars) {
		Tab.chainLocalSymbols(methodDeclWithoutFormPars.getMethodTypeName().obj);
		Tab.closeScope();
		if (!returnFound && currMethod.getType() != Tab.noType) {
			report_error("Nedostaje return naredba funkciji " + currMethod.getName(), methodDeclWithoutFormPars);
		}
	}

	public void visit(MethodDeclWithFormPars methodDeclWithFormPars) {
		currMethod.setFpPos(methodDeclWithFormPars.getFormPars().obj.getFpPos());
		Tab.chainLocalSymbols(methodDeclWithFormPars.getMethodTypeName().obj);
		Tab.closeScope();
		if (!returnFound && currMethod.getType() != Tab.noType) {
			report_error("Nedostaje return naredba funkciji " + currMethod.getName(), methodDeclWithFormPars);
		}
	}

	/* ************************* ENUMI /************************** */
	public void visit(EnumDeclName EnumDeclName) {
		Obj typeObj = Tab.find(EnumDeclName.getEnumDeclName());
		if (typeObj != Tab.noObj) {
			report_error("Vec postoji enum sa imenom" + typeObj.getName(), EnumDeclName);
		} else {
			// nije nadjen i treba ga dodati
			// tip nije int, nego new Struct(Struct.ENUM)!
			// Obj enumNode= Tab.insert(Obj.Type, typeObj.getName(), Tab.intType);
			Obj enumNode = Tab.insert(Obj.Type, EnumDeclName.getEnumDeclName(),
					currentEnumType = new Struct(Struct.Enum));
			EnumDeclName.obj = enumNode;
			Tab.openScope();
		}
	}

	public void visit(EnumDecl enumDecl) {
		lastEnumVal = 0;
		Tab.chainLocalSymbols(enumDecl.getEnumDeclName().obj);
		Tab.closeScope();
	}

	public void visit(EnumSingleDef enumSingleDef) {
		Obj typeObj = Tab.currentScope.findSymbol(enumSingleDef.getEnumName());
		// da li treba obican find ili ovaj sa scope-om?
		if (typeObj != null) {
			// vec postoji ovaj simbol u ovom opsegu
//			if (typeObj.getType().getKind() == Struct.Enum) {
				report_error("Vec postoji konstanta u tekucem enumu sa imenom" + typeObj.getName(), enumSingleDef);
//			}
		} else {
			Obj obj = Tab.insert(Obj.Con, enumSingleDef.getEnumName(), currentEnumType);
			enumSingleDef.obj = obj;
			report_info("Deklarisana je konstanta " + enumSingleDef.getEnumName() + " sa vrednoscu "
					+ enumSingleDef.getVal(), enumSingleDef);
		}
		enumSingleDef.obj.setAdr(enumSingleDef.getVal());
		lastEnumVal = enumSingleDef.getVal() + 1;

	}

	public void visit(EnumSingleDefDefault enumSingleDefDefault) {
		// jedino ogranicenje je da vidimo da li je konkretna const vec definisana u
		// ovom enumu
		// pretazicemo ceo scope, al cemo proveriti da li je tipa trenutnog enuma
		Obj typeObj = Tab.currentScope.findSymbol(enumSingleDefDefault.getEnumName());
		if (typeObj != null) {
			// vec postoji simbol sa ovim imenom, ali on je mozda i neka druga var/const itd
//			if (typeObj.getType().getKind() == Struct.Enum) {
				report_error("Vec postoji konstanta u tekucem enumu sa imenom" + typeObj.getName(),
						enumSingleDefDefault);
//			}
		} else {
			Obj obj = Tab.insert(Obj.Con, enumSingleDefDefault.getEnumName(), currentEnumType);
			enumSingleDefDefault.obj = obj;
			report_info(
					"Deklarisana je konstanta " + enumSingleDefDefault.getEnumName() + " sa vrednoscu " + lastEnumVal,
					enumSingleDefDefault);
		}
		enumSingleDefDefault.obj.setAdr(lastEnumVal++);
	}

	/* /**************************** PAREMETRI FUNKCIJE ***************** */

	public void visit(FormalParamDef formalParamDef) {
		// poukusavamo da nadjemo datu promenljivu u trenutnom scope
		// ovde takodje zelimo da imamo pokazivac na Obj
		Obj obj = Tab.currentScope.findSymbol(formalParamDef.getParamName());

		if (obj != null) {
			report_error("Postoji definisana promenljiva sa imenom " + formalParamDef.getParamName(), formalParamDef);
		} else {
			Obj node = Tab.insert(Obj.Var, formalParamDef.getParamName(), formalParamDef.getType().struct);
			formalParamDef.obj = node;// ovu liniju sam dodala danas

		}
	}

	public void visit(FormalParamArrayDef formalParamArrayDef) {
		Obj obj = Tab.currentScope.findSymbol(formalParamArrayDef.getParamName());

		if (obj != null) {
			report_error("Postoji definisana promenljiva sa imenom " + formalParamArrayDef.getParamName(),
					formalParamArrayDef);
		} else {

			Obj node = Tab.insert(Obj.Var, formalParamArrayDef.getParamName(),
					new Struct(Struct.Array, formalParamArrayDef.getType().struct));
			formalParamArrayDef.obj = node;// ovu liniju sam dodala danas
		}
	}
	
	public void visit(FormalParameterOne formalParameterOne) {
		Obj fp = formalParameterOne.getFormParam().obj;
		fp.setFpPos(1);
		formalParameterOne.obj = fp;
	}
	
	public void visit(FormalParametersMany formalParametersMany) {
		Obj fpars = formalParametersMany.getFormPars().obj;
		Obj fp = formalParametersMany.getFormParam().obj;
		fp.setFpPos(fpars.getFpPos() + 1);
		formalParametersMany.obj = fp;
	}

	/* ******************* STATEMENTS******************** */
	public void visit(ReadStatement readStatement) {
		Obj obj = readStatement.getDesignator().obj;
		
		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			// Elem je koriscenje niza i taj cvor se pravi kad posecujemo DesignatorWithExpr
			// Pri citanju to mora bit ili Var ili element niza
			report_error(obj.getName() + "nije promenljiva ni element niza!", readStatement);
		}
		if (obj.getType().getKind() != Struct.Bool && obj.getType().getKind() != Struct.Int
				&& obj.getType().getKind() != Struct.Char) {
			// ta varijabla koja se koristi mora biti int char ili bool
			report_error(obj.getName() + "nije tipa int,char ili bool!", readStatement);
		}
	}

	public void visit(PrintStatementTwoArg printStatementTwoArg) {
		Struct exprType = printStatementTwoArg.getExpr().struct;

		if (!testKind(exprType)) {
			report_error("nije tipa int,char ili bool!", printStatementTwoArg);
		}
		report_info("Obradjuje se print naredba", printStatementTwoArg);
	}

	public void visit(PrintStatementOneArg printStatementOneArg) {
		Struct exprType = printStatementOneArg.getExpr().struct;

		if (!testKind(exprType)) {
			report_error("nije tipa int,char ili bool!", printStatementOneArg);
		}
		report_info("Obradjuje se print naredba", printStatementOneArg);
	}

	public void visit(ReturnStmtWithoutExpr returnStmtWithoutExpr) {
		if (currMethod.getType() != Tab.noType) {
			report_error("Metoda " + currMethod.getName() + " nije void", returnStmtWithoutExpr);
		}
		returnStmtType = Tab.noType;
		returnFound = true;
	}

	public void visit(ReturnStmtWithExpr returnStmtWithExpr) {
		if (currMethod.getType() != returnStmtWithExpr.getExpr().struct) {
			report_error("Povratna vrednost je loseg tipa", returnStmtWithExpr);
		}
		returnStmtType = returnStmtWithExpr.getExpr().struct;
		returnFound = true;
	}

	/* ***pomocne metode *** */
	private boolean testArrayKind(Struct str) {
		if (str.getKind() == Struct.Array && testKind(str))
			return true;
		return false;
	}

	private boolean testKind(Struct str) {
		if (str.getKind() == Tab.charType.getKind() || str.getKind() == Tab.intType.getKind()
				|| str.getKind() == Struct.Bool)
			return true;
		return false;
	}

	/* *** designatorStmts*** */
	public void visit(DesignatorStmtAssign designatorStmtAssign) {
		// ovde smo nakon sto smo posetili Designator(tu je utvrdjeno da postoji)
		// i nakon sto smo procitali Expr
		// treba da vidimo da li je designator promenljiva, element niza
		// sada samo treba da vidimo da li su kompatibillnih tipova

		if (designatorStmtAssign.getDesignator().obj.getKind() != Obj.Var
				&& designatorStmtAssign.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Designator nije ni promenljiva ni element niza ", designatorStmtAssign);
		}
		Struct desType = designatorStmtAssign.getDesignator().obj.getType();
		Struct exprType = designatorStmtAssign.getExpr().struct;


		if (!desType.assignableTo(exprType) && !(desType == Tab.intType && exprType.getKind() == Struct.Enum)) {
			report_error("Tipovi nisu kompatibilni za dodelu", designatorStmtAssign);
		}
		// ako je Designator tipa int onda mozemo da desno imamo i ENUM i INT
		// ako je Designator tipa ENUM, onda desno mozemo da imamo samo elemente TOG ENUMA
	
	}

	public void visit(DesignatorStmtINC designatorStmtINC) {
		
		// designator mora biti promenljiva ili element niza
		// elementi niza se ubacuju kao Var
		Obj obj = designatorStmtINC.getDesignator().obj;
	
			// nadjen je ali treba proveriti da li je tipa int
			if (obj.getType() != Tab.intType || (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem)) {
				report_error(obj.getName() + "nie tipa integer i ne moze da se radi INC", designatorStmtINC);
			}
		
	}

	public void visit(DesignatorStmtDEC designatorStmtDEC) {
		// designator mora biti promenljiva ili element niza tj Var ili Elem, zar ne???
		// elementi niza se ubacuju kao Var
		Obj obj = designatorStmtDEC.getDesignator().obj;
		
		// nadjen je ali treba proveriti da li je tipa int
		if (obj.getType() != Tab.intType || (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem)) {
			report_error(obj.getName() + "nije tipa integer i ne moze da se radi DEC", designatorStmtDEC);
		}
	}

	public void visit(DesignatorStmtWithoutParams designatorStmtWithoutParams) {
		Obj obj = designatorStmtWithoutParams.getDesignator().obj;
		if (obj.getKind() != Obj.Meth) {
			report_error("Nadjeni poziv nije metoda!", designatorStmtWithoutParams);
		} else {
			if (obj.getFpPos() > 0) {
				report_error("Poziv funkcije "+obj.getName()+"bez parametara a treba " + obj.getFpPos(), designatorStmtWithoutParams);
			}
		}
	}

	public void visit(DesignatorStmtWithParams designatorStmtWithParams) {
		Obj obj = designatorStmtWithParams.getDesignator().obj;
		if (obj.getKind() != Obj.Meth) {
			report_error("Nadjeni poziv nije metoda!", designatorStmtWithParams);
		} else {
			// jeste metod, treba da vidimo slaganje po parametrima

			// for(Obj o: designatorStmtWithParams.getDesignator().obj.getLocalSymbols()) {
			// }

			List<Struct> actParsList = designatorStmtWithParams.getActPars().list;
			for(Obj formPar: obj.getLocalSymbols()) {
				if (formPar.getFpPos() == 0) {
					break;
				}
				if (actParsList.isEmpty()) {
					report_error("Premalo argumenata", designatorStmtWithParams);
					break;
				}
				Struct actPar = actParsList.remove(0);
				if (!actPar.assignableTo(formPar.getType())) {
					report_error("Prosledjeni parametri se ne poklapaju sa argumentima!", designatorStmtWithParams);
				}
			}
			if (!actParsList.isEmpty()) {
				report_error("Previse argumenata", designatorStmtWithParams);
			}


		}
	}

	//////////////////////// ACT PARAMETERS ////////////////////////////
	public void visit(SingleActParameter singleActParameter) {
		singleActParameter.list = new ArrayList<Struct>();
		singleActParameter.list.add(singleActParameter.getExpr().struct);
	}

	public void visit(ActParameters actParameters) {
		actParameters.list = actParameters.getActPars().list;
		actParameters.list.add(actParameters.getExpr().struct);
	}

	////////////////////////////////// TERM//////////////////////////////////////////
	public void visit(ExprList exprList) {
		// (ExprList)Expr Addop Term
		System.out.println("sad si u ExprList");
		if ((exprList.getExpr().struct != Tab.intType && exprList.getExpr().struct.getKind() != Struct.Enum)
				|| (exprList.getTerm().struct != Tab.intType && exprList.getTerm().struct.getKind() != Struct.Enum)) {
				//slucaj da hocemo da sabiramo nesto sto nisu enumi ili integeri
			exprList.struct = Tab.noType;
			report_error("(ExprList)Tipovi nisu kompatibilni", exprList);
			return;
		}
		// ako je sve u redu:
		exprList.struct = Tab.intType;
	}

	public void visit(ExprMinusTerm exprMinusTerm) {
		exprMinusTerm.struct = exprMinusTerm.getTerm().struct;
		if (exprMinusTerm.struct.getElemType() != Tab.intType.getElemType()) {
			report_error("Expression nije tipa integer!", exprMinusTerm);
		}
	}

	public void visit(ExprTerm exprTerm) {
		exprTerm.struct = exprTerm.getTerm().struct;
		System.out.println("sad si u ExprTerm");
//		if (exprTerm.struct != Tab.intType) {
//			report_error("Expression nije tipa integer!", exprTerm);
//		}
	}

	public void visit(TermManyFactors termManyFactors) {

		if ((termManyFactors.getTerm().struct != Tab.intType
				&& termManyFactors.getTerm().struct.getKind() != Struct.Enum)
				|| (termManyFactors.getFactor().struct != Tab.intType
						&& termManyFactors.getFactor().struct.getKind() != Struct.Enum)) {
			termManyFactors.struct = Tab.noType;
			report_error("Clanovi izraza nisu integeri", termManyFactors);
			return;
		}
		termManyFactors.struct = Tab.intType;
	}

	public void visit(TermSingleFactor termSingleFactor) {
		termSingleFactor.struct = termSingleFactor.getFactor().struct;
		// System.out.println("TTTTTTTTTTermSingleFactor ima sledeci kind " +
		// termSingleFactor.struct.getKind());
	}

	//////////////////////// FACTORS //////////////////////////////////////////////
	// ++
	public void visit(FactorDesignator factorDesignator) {
		report_info("sad si u factorDesignator ", factorDesignator);
		// ovde je bitan samo tip Designatora, zar ne?

		// PITANJE: DA LI OVDE DA PRAVIM NOVI SKROZ ILI NE??
		factorDesignator.struct = factorDesignator.getDesignator().obj.getType();
	}

	public void visit(FactorRegularExpr factorRegularExpr) {
		factorRegularExpr.struct = factorRegularExpr.getExpr().struct;
	}

	// ++
	public void visit(FactorConst factorConst) {

		factorConst.struct = factorConst.getTypeConst().struct.getElemType();
		report_info("tip strukture je sledeci " + factorConst.struct.getKind(), factorConst);
	}

	public void visit(FactorDesingatorWithoutParams factorDesingatorWithoutParams) {
		// ?? da li je ovo dobro
		// bitno je samo da upisem tip, a kind je nebitan
		Obj o = factorDesingatorWithoutParams.getDesignator().obj;
		if (o.getKind() == Obj.Meth) {
			report_info("Pronajdena je metoda " + o.getName(), factorDesingatorWithoutParams);
		} else {
			report_error("Pronajden poziv " + o.getName() + "nije metoda!", factorDesingatorWithoutParams);

		}
		factorDesingatorWithoutParams.struct = factorDesingatorWithoutParams.getDesignator().obj.getType();
	}

	public void visit(FactorDesignatorWithParams factorDesignatorWithParams) {
		Obj o = factorDesignatorWithParams.getDesignator().obj;
		if (o.getKind() != Obj.Meth) {
			report_error("Pronadjen poziv nije metoda!", factorDesignatorWithParams);
		}
		
		List<Struct> actParsList = factorDesignatorWithParams.getActPars().list;
		for(Obj formPar: o.getLocalSymbols()) {
			if (formPar.getFpPos() == 0) {
				break;
			}
			if (actParsList.isEmpty()) {
				report_error("Premalo argumenata", factorDesignatorWithParams);
				break;
			}
			Struct actPar = actParsList.remove(0);
			if (!actPar.assignableTo(formPar.getType())) {
				report_error("Prosledjeni parametri se ne poklapaju sa argumentima!", factorDesignatorWithParams);
			}
		}
		if (!actParsList.isEmpty()) {
			report_error("Previse argumenata", factorDesignatorWithParams);
		}

		
//		// proveriti da li se poklapaju tipovi stvarnih i prosledjenih argumenata
//		if (factorDesignatorWithParams.getActPars().list.size() != o.getLocalSymbols().size()) {
//			report_error("Nije isti broj formalnih parametara i argumenata poziva funkcije!",
//					factorDesignatorWithParams);
//		}
//
//		Iterator<Obj> it = o.getLocalSymbols().iterator();
//		ArrayList<Obj> lista = (ArrayList<Obj>) factorDesignatorWithParams.getActPars().list;
//		for (int i = 0; i < o.getLocalSymbols().size(); i++) {
//			if (it.next().getType().getKind() != lista.get(i).getType().getKind()) {
//				report_error("Tipovi formalnih i stvarnih argumenata se ne poklapaju!", factorDesignatorWithParams);
//				return;
//			}
//		}

		report_info("Pronadjena je metoda " + o.getName(), factorDesignatorWithParams);
		// da li ovde sme da se stavi None? da li je uopste bitno sta je?
		factorDesignatorWithParams.struct = factorDesignatorWithParams.getDesignator().obj.getType();
	}

	public void visit(FactorNew factorNew) {
		// ne treba, jer nije podrzan za semanticku analizu u A fazi
	}

	public void visit(FactorNewArray factorNewArray) {
		// new int[1+2+x]
		if (factorNewArray.getExpr().struct != Tab.intType
				&& factorNewArray.getExpr().struct.getKind() != Struct.Enum) {
			report_error("Expression za alokaciju niza nije tipa int!", factorNewArray);
		}
		// proveri zasto Klac koristi newOpFlag
		Struct type = factorNewArray.getType().struct;
		factorNewArray.struct = new Struct(Struct.Array, type);
	}

	///////////////////////////////////////////////////////////////////////

	public boolean passed() {
		return !errorDetected;
	}

}
