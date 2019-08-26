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
	int printCallCount = 0;
	boolean returnFound = false;
	int nVars;

	Struct currentType;
	private List<Obj> enums = new ArrayList<Obj>();// da tu ulancam sve objektne cvorove razlicitih enuma na koje
													// naidjem
	// private List<Obj> currEnum = new ArrayList<Obj>();// da ulancavam constante
	// trenutnog enuma koji obradjujem
	// ovo je znak da je jedan enum deklarisan i da treba da se resetuje brojanje
	private int lastEnumVal = 0;

	// List<Obj> formParams = new ArrayList<Obj>();
	private boolean mainDef = false;
	private Obj currMethod = null;

	public static Map<String, List<Obj>> hashForm = new LinkedHashMap<String, List<Obj>>();

	private Struct returnStmtType = null;

	////////////////// DATE METODE
	////////////////// ///////////////////////////////////////////////////////////////////
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
		// obj je dva nivoa dole od neterminala Program;
		// na dole uvek mozemo da dohvatamo sta nam treba
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
		System.out.println("******** Type");
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

	//////////////////////// VARIJABLE ////////////////////////
	public void visit(GlobVarSingleDefWithoutBracket GlobVarSingleDefWithoutBracket) {
		String name = GlobVarSingleDefWithoutBracket.getVarName();
		if (Tab.currentScope().findSymbol(name) == null) {
			Tab.insert(Obj.Var, name, currentType);
		} else {
			report_error("Visestruko definisanje simbola " + name, GlobVarSingleDefWithoutBracket);
		}
	}

	// izmeniti
	public void visit(GlobVarSingleDefWithBracket GlobVarSingleDefWithBracket) {
		String name = GlobVarSingleDefWithBracket.getVarName();
		if (Tab.currentScope().findSymbol(name) == null) {
			Tab.insert(Obj.Var, name, new Struct(Struct.Array, currentType));
		} else {
			report_error("Visestruko definisanje simbola " + name, GlobVarSingleDefWithBracket);
		}
	}
	//////////////////// CONSTANTS
	//////////////////// /////////////////////////////////////////////////////////////

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
		// a posle ce TypeConst biti bas taj cvor ako je u pitanju NUMBER
		// i onda ce se sa SingleConstElem.getTypeConst().struct.getKind();
		// dohvatiti ta vrednost
		int value = NumberConst.getValue();
		// System.out.println("Stampam vrednosttt" + value);
		Struct s = new Struct(value);
		s.setElementType(Tab.intType);
		NumberConst.struct = s;
	}

	//////////////////////////// METODE
	////////////////////////////////////
	public void visit(MethodTypeAndName methodTypeAndName) {
		Struct type = methodTypeAndName.getType().struct;
		String name = methodTypeAndName.getMethodName();
		currMethod = methodTypeAndName.obj = Tab.insert(Obj.Meth, name, type);
		Tab.openScope();
		returnFound = false;
		// formParams.clear();
	}

	public void visit(MethodVoidAndName methodVoidAndName) {
		// proverit da li je pitanju metoda main()
		// ona sme da bude definisana samo jednom
		Struct type = Tab.noType;
		String name = methodVoidAndName.getMethodName();
		if (name.contentEquals("main")) {
			mainDef = true;
			// da ubelezimo ako smo definisali main metod
		}

		// fali provera da li vec postoji u TS
		if (Tab.find(name) != Tab.noObj) {
			report_error("U programu je vec definisana metoda " + name, methodVoidAndName);
		}
		currMethod = methodVoidAndName.obj = Tab.insert(Obj.Meth, name, type);
		Tab.openScope();
		returnFound = false;
		// formParams.clear();
	}

	public void visit(MethodDeclWithoutFormPars methodDeclWithoutFormPars) {
		Tab.chainLocalSymbols(methodDeclWithoutFormPars.getMethodTypeName().obj);
		// hashForm.put(methodDeclWithoutFormPars.obj.getName(), formParams);
		Tab.closeScope();
		if (!returnFound && currMethod.getType() != Tab.noType) {
			report_error("Nedostaje return naredba funkciji " + currMethod.getName(), methodDeclWithoutFormPars);
		}
	}

	public void visit(MethodDeclWithFormPars methodDeclWithFormPars) {
		Tab.chainLocalSymbols(methodDeclWithFormPars.getMethodTypeName().obj);
		// hashForm.put(methodDeclWithFormPars.obj.getName(), formParams);
		Tab.closeScope();
		if (!returnFound && currMethod.getType() != Tab.noType) {
			report_error("Nedostaje return naredba funkciji " + currMethod.getName(), methodDeclWithFormPars);
		}
	}
	////////////////////////// ENUMI //////////////////////////////////////////////

	public void visit(EnumDecl enumDecl) {
		lastEnumVal = 0;
		SymbolDataStructure str = SymbolTableFactory.instance().createSymbolTableDataStructure();
		/*
		 * for (Obj object : currEnum) { str.insertKey(object); }
		 */
		// umesto ovoga gore, Tab.chainLocalSymbols(enumDecl.getEnumDeclName().obj),
		// Tab.closeScope();
		Tab.chainLocalSymbols(enumDecl.getEnumDeclName().obj);
		Tab.closeScope();
		enumDecl.getEnumDeclName().obj.getType().setMembers(str);
		// enums.add(enumDecl.getEnumDeclName().obj);
		// currEnum.clear();// brisemo tekuci enum
	}

	public void visit(EnumDeclName EnumDeclName) {
		// System.out.println("UNOSENJE ENUMA U TS");
		Obj typeObj = Tab.find(EnumDeclName.getEnumDeclName());
		if (typeObj != Tab.noObj) {
			report_error("Vec postoji enum sa imenom" + typeObj.getName(), EnumDeclName);
		} else {
			// nije nadjen i treba ga dodati
			// tip nije int, nego new Struct(Struct.ENUM)!
			// Obj enumNode= Tab.insert(Obj.Type, typeObj.getName(), Tab.intType);
			Obj enumNode = Tab.insert(Obj.Type, EnumDeclName.getEnumDeclName(), currentEnumType = new Struct(Struct.Enum));
			EnumDeclName.obj = enumNode;
			// nakon ovoga, uradi Tab.OpenScope();
			Tab.openScope();
		}
	}

	public void visit(EnumSingleDefDefault enumSingleDefDefault) {
		// jedino ogranicenje je da vidimo da li je konkretna const vec definisana u
		// ovom enumu
		// pretazicemo ceo scope, al cemo proveriti da li je tipa trenutnog enuma
		Obj typeObj = Tab.find(enumSingleDefDefault.getEnumName());
		if (typeObj != Tab.noObj) {
			// vec postoji simbol sa ovim imenom, ali on je mozda i neka druga var/const itd
			if (typeObj.getType().getKind() == Struct.Enum) {
				report_error("Vec postoji konstanta u tekucem enumu sa imenom" + typeObj.getName(),
						enumSingleDefDefault);
			}
		} else {
			Obj obj = Tab.insert(Obj.Con, enumSingleDefDefault.getEnumName(), currentEnumType);
			enumSingleDefDefault.obj = obj;
			report_info(
					"Deklarisana je konstanta " + enumSingleDefDefault.getEnumName() + " sa vrednoscu " + lastEnumVal,
					enumSingleDefDefault);
		}
		enumSingleDefDefault.obj.setAdr(lastEnumVal++);
		// currEnum.add(enumSingleDefDefault.obj);
	}

	public void visit(EnumSingleDef enumSingleDef) {
		Obj typeObj = Tab.find(enumSingleDef.getEnumName());
		if (typeObj != Tab.noObj) {
			// vec postoji ovaj simboj u ovom opsegu
			if (typeObj.getType().getKind() == Struct.Enum) {
				report_error("Vec postoji konstanta u tekucem enumu sa imenom" + typeObj.getName(), enumSingleDef);
			}
		} else {
			Obj obj = Tab.insert(Obj.Con, enumSingleDef.getEnumName(), Tab.intType);
			enumSingleDef.obj = obj;
			report_info("Deklarisana je konstanta " + enumSingleDef.getEnumName() + " sa vrednoscu "
					+ enumSingleDef.getVal(), enumSingleDef);
		}
		enumSingleDef.obj.setAdr(enumSingleDef.getVal());
		// currEnum.add(enumSingleDef.obj);
		lastEnumVal = enumSingleDef.getVal() + 1;

	}
	/////////////////////////////// PAREMTRI FUNKCIJE
	/////////////////////////////// ///////////////////////////////////////////////////////

	public void visit(FormalParamDef formalParamDef) {
		// poukusavamo da nadjemo datu promenljivu u trenutnom scope
		// ovde takodje zelimo da imamo pokazivac na Obj
		Obj obj = Tab.currentScope.findSymbol(formalParamDef.getParamName());

		if (obj != null) {
			report_error("Postoji definisana promenljiva sa imenom " + formalParamDef.getParamName(), formalParamDef);
		} else {
			Obj node = Tab.insert(Obj.Var, formalParamDef.getParamName(), formalParamDef.getType().struct);
			// formParams.add(node);
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
			// formParams.add(node);
		}
	}

	//////////////////////////////// DESIGNATORI
	//////////////////////////////// /////////////////////////////////////////////////////
	public void visit(DesignatorStmtINC designatorStmtINC) {
		System.out.println("SAD SI U designatorStmtINC");

		// designator mora biti promenljiva ili element niza
		// elementi niza se ubacuju kao Var
		Obj obj = Tab.find(designatorStmtINC.getDesignator().obj.getName());
		if (obj == Tab.noObj) {
			// nije nadjen
			report_error(obj.getName() + "nije ni deklarican", designatorStmtINC);
		} else {
			// nadjen je ali treba proveriti da li je tipa int
			if (obj.getType().getKind() != Tab.intType.getKind() && obj.getKind() != Obj.Var) {
				report_error(obj.getName() + "nije tipa integer i ne moze da se radi INC", designatorStmtINC);
			}
			// inc.getDesignator().obj.getType() == Tab.intType)
		}
	}

	public void visit(DesignatorStmtDEC designatorStmtDEC) {
		// designator mora biti promenljiva ili element niza
		// elementi niza se ubacuju kao Var
		System.out.println("SAD SI U designatorStmtDEC");
		Obj obj = Tab.find(designatorStmtDEC.getDesignator().obj.getName());
		if (obj == Tab.noObj) {
			// nije nadjen
			report_error(obj.getName() + "nije ni deklarican", designatorStmtDEC);
		} else {
			// nadjen je ali treba proveriti da li je tipa int
			if (obj.getType().getKind() != Tab.intType.getKind() && obj.getKind() != Obj.Var) {
				report_error(obj.getName() + "nije tipa integer i ne moze da se radi DEC", designatorStmtDEC);
			}
			// inc.getDesignator().obj.getType() == Tab.intType)
		}
	}

	public void visit(DesignatorStmtWithoutParams designatorStmtWithoutParams) {
		Obj obj = Tab.find(designatorStmtWithoutParams.obj.getName());
		if (obj.getKind() != Obj.Meth) {
			report_error("Nadjeni poziv nije metoda!", designatorStmtWithoutParams);
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

			List<Obj> formalPars = hashForm.get(designatorStmtWithParams.getDesignator().obj.getName());
			int sizeFormal = formalPars.size();

			List<Struct> actParsList = designatorStmtWithParams.getActPars().list;

			if (sizeFormal != actParsList.size()) {
				report_error("Prosledjeni parametri se ne poklapaju po broju sa argumentima metode!",
						designatorStmtWithParams);
			} else {
				// sada treba proveriti svaki element zasebno
				for (int i = 0; i < sizeFormal; i++) {
					if (formalPars.get(i).getType().getKind() != actParsList.get(i).getKind())
						report_error("Prosledjeni parametri se ne poklapaju sa argumentima!", designatorStmtWithParams);
				}
			}

		}
	}

	//////////////////////// ACT PARAMETERS ////////////////////////////
	public void visit(SingleActParameter singleActParameter) {
		singleActParameter.list = new ArrayList<Struct>();
		singleActParameter.list.add(singleActParameter.getExpr().struct);
		// actParams.add(singleActParameter.getExpr().struct);
	}

	public void visit(ActParameters actParameters) {
		actParameters.list = actParameters.getActPars().list;
		actParameters.list.add(actParameters.getExpr().struct);
	}

	//////////////////////////// POMOCNE
	//////////////////////////// METODE///////////////////////////////////////////////////
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

	///////////////////////////// STATEMENTS//////////////////////////
	public void visit(ReadStatement readStatement) {
		// designator mora biti promenljiva ili element niza ili polje unutar objekta
		// designator mora biti int char ili bool
		Obj obj = Tab.find(readStatement.getDesignator().obj.getName());
		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error(obj.getName() + "nije promenljiva ni element niza!", readStatement);
		}
		if (obj.getType().getKind() != Struct.Bool && obj.getType().getKind() != Struct.Int
				&& obj.getType().getKind() != Struct.Char) {
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

	// ??? sta jos ovde treba
	public void visit(ReturnStmtWithoutExpr returnStmtWithoutExpr) {
		if (currMethod.getType() != Tab.noType) {
			report_error("Metoda " + currMethod.getName() + " nije void", returnStmtWithoutExpr);
		}
		returnStmtType = Tab.noType;
		returnFound = true;
	}

	// ??? sta jos ovde treba
	public void visit(ReturnStmtWithExpr returnStmtWithExpr) {
		if (currMethod.getType() != returnStmtWithExpr.getExpr().struct) {
			report_error("Povratna vrednost j eloseg tipa", returnStmtWithExpr);
		}
		returnStmtType = returnStmtWithExpr.getExpr().struct;
		returnFound = true;
	}

	/////////////////////// DESIGNATOR ZA ENUM I NIZ/////////////////////////////
	public void visit(DesignatorWithDOT designatorWithDOT) {
		Obj obj = Tab.noObj;
		obj = Tab.find(designatorWithDOT.getEnumIdentity());
		System.out.println("aaaaaaaaaaaaaaaaaa " + obj.getName());
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

	public void visit(DesignatorWithExpr designatorWithExpr) {
		Obj o = Tab.find(designatorWithExpr.getDesignatorName());
		if (o == Tab.noObj) {
			// nije ga nasao
			designatorWithExpr.obj = Tab.noObj;
//			Struct t = designatorWithExpr.obj.getType().getElemType();
//			designatorWithExpr.obj = new Obj(Obj.Elem, " ", t);// jel ovdenebitno sta ubacujem?
//
//			if (designatorWithExpr.obj.getType() != Tab.intType) {
//				report_error("Expression unutar zagrade kod identifikatora niza "
//						+ designatorWithExpr.getDesignatorName() + "nije integer", designatorWithExpr);
//				return;
//			}
//			report_error(designatorWithExpr.getDesignatorName()
//					+ "nije deklarisan kao niz i(ili) se ne nalazi u tabeli simbola ", designatorWithExpr);
		} else {
			// nasao ga je u TS
			if (o.getType().getKind() == Struct.Array) {
				designatorWithExpr.obj = new Obj(Obj.Elem, o.getName(), o.getType().getElemType());
				if (designatorWithExpr.getExpr().struct != Tab.intType && designatorWithExpr.getExpr().struct.getKind() != Struct.Enum) {
					report_error("Expression nije tipa integer ", designatorWithExpr);
					return;
				}
				report_info("Pronadjen je simbol niza ", designatorWithExpr);
			}
			else {
				report_error(designatorWithExpr.getDesignatorName() + " nije niz", designatorWithExpr);
			}
		}

	}

	public void visit(DesignatorStmtAssign designatorStmtAssign) {
		// ovde smo nakon sto smo posetili Designator(tu je utvrdjeno da postoji)
		// i nakon sto smo procitali Expr
		// treba da vidimo da li je designator promenljiva, element niza
		// sada samo treba da vidimo da li su kompatibillnih tipova

		System.out.println("Sad si u DesignatorStmtAssign    " + designatorStmtAssign.getDesignator().obj.getName());
		if (designatorStmtAssign.getDesignator().obj.getKind() != Obj.Var
				&& designatorStmtAssign.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Designator nije ni promenljiva ni element niza ", designatorStmtAssign);
		}
		Struct desType = designatorStmtAssign.getDesignator().obj.getType();
		Struct exprType = designatorStmtAssign.getExpr().struct;

		if (designatorStmtAssign.getDesignator().obj.getType().getKind() == Struct.Enum
				&& designatorStmtAssign.getExpr().struct.getKind() == Struct.Int) {
			report_error("Tipovi nisu kompatibilni za dodelu", designatorStmtAssign);
		} else {

		}
		if (!desType.compatibleWith(exprType)) {
			report_error("Tipovi nisu kompatibilni za dodelu", designatorStmtAssign);
		}

		// ako je Designator tipa int onda mozemo da desno imamo i ENUM i INT
		// ako je Designator tipa ENUM, onda desno mozemo da imamo samo elemente TOG
		// ENUMA

	}

	////////////////////////////////// TERM//////////////////////////////////////////
	public void visit(ExprList exprList) {
		// (ExprList)Expr Addop Term
		System.out.println("sad si u ExprList");
		if ((exprList.getExpr().struct != Tab.intType && exprList.getExpr().struct.getKind() != Struct.Enum)
				|| (exprList.getTerm().struct != Tab.intType
						&& exprList.getTerm().struct.getKind() != Struct.Enum)) {
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
		//System.out.println("Term:" + termManyFactors.getTerm().struct.getKind());
		//System.out.println("Fact:" + termManyFactors.getFactor().struct.getKind());
		
		if ((termManyFactors.getTerm().struct != Tab.intType && termManyFactors.getTerm().struct.getKind() != Struct.Enum)
				|| (termManyFactors.getFactor().struct != Tab.intType
						&& termManyFactors.getFactor().struct.getKind() != Struct.Enum))
		{
			termManyFactors.struct = Tab.noType;
			report_error("Clanovi izraza nisu integeri", termManyFactors);
			return;
		}
		termManyFactors.struct = Tab.intType;
	}

	public void visit(TermSingleFactor termSingleFactor) {
		termSingleFactor.struct = termSingleFactor.getFactor().struct;
		//System.out.println("TTTTTTTTTTermSingleFactor ima sledeci kind " + termSingleFactor.struct.getKind());
	}

	//////////////////////// FACTORS //////////////////////////////////////////////
	// ++
	public void visit(FactorDesignator factorDesignator) {
		report_info("sad si u factorDesignator ", factorDesignator);
		// ovde je bitan samo tip Designatora, zar ne?
		System.out.println("IME POSECIVANOG DESIGNATORA JE  " + factorDesignator.getDesignator().obj.getName());
		System.out
				.println("TIP POSECIVANOG DESIGNATORA JE  " + factorDesignator.getDesignator().obj.getType().getKind());

		// PITANJE: DA LI OVDE DA PRAVIM NOVI SKROZ ILI NE??
		factorDesignator.struct = factorDesignator.getDesignator().obj.getType();
	}

	public void visit(FactorRegularExpr factorRegularExpr) {
		factorRegularExpr.struct = factorRegularExpr.getExpr().struct;
	}

	// ++
	public void visit(FactorConst factorConst) {
		report_info("sad si u factorConst ", factorConst);

		factorConst.struct = factorConst.getTypeConst().struct.getElemType();
		report_info("tip strukture je sledeci " + factorConst.struct.getKind(), factorConst);
	}

	public void visit(FactorDesingatorWithoutParams factorDesingatorWithoutParams) {
		// ?? da li je ovo dobro
		// bitno je samo da upisem tip, a kind je nebitan
		Obj o = Tab.noObj;
		o = Tab.find(factorDesingatorWithoutParams.getDesignator().obj.getName());
		if (o.getKind() == Obj.Meth) {
			report_info("Pronajdena je metoda " + o.getName(), factorDesingatorWithoutParams);
		} else {
			report_error("Pronajden poziv " + o.getName() + "nije metoda!", factorDesingatorWithoutParams);

		}
		factorDesingatorWithoutParams.struct = new Struct(Struct.None,
				factorDesingatorWithoutParams.getDesignator().obj.getType().getElemType());
	}

	public void visit(FactorDesignatorWithParams factorDesignatorWithParams) {
		Obj o = Tab.noObj;
		o = Tab.find(factorDesignatorWithParams.getDesignator().obj.getName());
		if (o.getKind() != Obj.Meth) {
			report_error("Pronadjen poziv nije metoda!", factorDesignatorWithParams);
		}
		// proveriti da li se poklapaju tipovi stvarnih i prosledjenih argumenata
		if (factorDesignatorWithParams.getActPars().list.size() != o.getLocalSymbols().size()) {
			report_error("Nije isti broj formalnih parametara i argumenata poziva funkcije!",
					factorDesignatorWithParams);
		}

		Iterator<Obj> it = o.getLocalSymbols().iterator();
		ArrayList<Obj> lista = (ArrayList<Obj>) factorDesignatorWithParams.getActPars().list;
		for (int i = 0; i < o.getLocalSymbols().size(); i++) {
			if (it.next().getType().getKind() != lista.get(i).getType().getKind()) {
				report_error("Tipovi formalnih i stvarnih argumenata se ne poklapaju!", factorDesignatorWithParams);
				return;
			}
		}
		report_info("Pronadjena je metoda " + o.getName(), factorDesignatorWithParams);
		// da li ovde sme da se stavi None? da li je uopste bitno sta je?
		factorDesignatorWithParams.struct = new Struct(Struct.None,
				factorDesignatorWithParams.getDesignator().obj.getType());

	}

	public void visit(FactorNew factorNew) {
		// ne treba, jer nije podrzan za semanticku analizu u A fazi
	}

	public void visit(FactorNewArray factorNewArray) {
		// new int[1+2+x]
		if (factorNewArray.getExpr().struct != Tab.intType && factorNewArray.getExpr().struct.getKind() != Struct.Enum) {
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
