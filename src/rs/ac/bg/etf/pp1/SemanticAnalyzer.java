package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	private boolean errorDetected = false;
	
	Logger log = Logger.getLogger(getClass());

	private Obj currProg;
	private int constant;
	private Struct constantType;
	private Struct currentType = Tab.noType;
	private Struct boolType = Tab.find("bool").getType();
	
	private boolean brackets = false;
	
	private Struct currEnumType = Tab.noType;
	private int enumCurVal = 0;
	private int enumAssign;
	private boolean enumAssigned;
	private int[] enumValues = new int[100];
	private int enumSize = 0;
	private Obj currEnum = Tab.noObj;
	
	private Obj currMeth = Tab.noObj;

	int var_count;

	
	/* LOG */
	
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
		log.error(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	@Override
	public void visit(ProgramName programName) {
		currProg = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		var_count = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(currProg);
		Tab.closeScope();
	}
	
	@Override
	public void visit(Type type) {
		Obj currType = Tab.find(type.getI1());
		
		if (currType == Tab.noObj) {
			report_error("Undefined type: " + type.getI1(), type);
			type.struct = Tab.noType;
			currentType = Tab.noType;
			return;
		}
		
		if (currType.getKind() != Obj.Type) {
			report_error("Invalid type: " + type.getI1(), type);
			type.struct = Tab.noType;
			currentType = Tab.noType;
			return;
		}
		
		type.struct = currType.getType();
		currentType = type.struct;
	}
	
	@Override
	public void visit(ConDecl conDecl) {
		Obj existingConst = Tab.find(conDecl.getI1());
		if(existingConst != Tab.noObj) {
			report_error("Constant already defined : " + conDecl.getI1(), conDecl);
		}
		else {
			if(constantType.assignableTo(currentType)) {
				Obj newConst = Tab.insert(Obj.Con, conDecl.getI1(), currentType);
				newConst.setAdr(constant);
			}
			else {
				report_error("Constant not assignable to declared type for constant : " + conDecl.getI1(), conDecl);
			}

		}
	}
	
	@Override
	public void visit(VarDecl varDecl) {
		if(Tab.currentScope.findSymbol(varDecl.getI1()) != null) {
			report_error("Var already defined : " + varDecl.getI1(), varDecl);
		}
		else {
			if(!brackets) {
				Tab.insert(Obj.Var, varDecl.getI1(), currentType);
			} 
			else {
				Struct currArr = new Struct(Struct.Array, currentType);
				Tab.insert(Obj.Var, varDecl.getI1(), currArr);
			}
		}
	}
		
	@Override
	public void visit(Brackets_true brackets_true) {
		brackets = true;
	}
	
	@Override
	public void visit(Brackets_e brackets_e) {
		brackets = false;
	}
	
	@Override
	public void visit(EnumName enumName) {
		currEnum = Tab.noObj;
		currEnumType = Tab.noType;
		
		Obj existingVar = Tab.find(enumName.getI1());
		if (existingVar != Tab.noObj) {
			report_error("Var already defined : " + enumName.getI1(), enumName);
			return;
		}
		else {
			currEnumType = new Struct(Struct.Enum);
			
			currEnum = Tab.insert(Obj.Type, enumName.getI1(), currEnumType);
			
			Tab.openScope();
			
			enumCurVal = 0;
			enumAssign = 0;
			enumAssigned = false;
			enumSize = 0;
			enumValues = new int[100];
		}
	}
	
	@Override
	public void visit(EnumDeclList enumDeclList) {
		if(currEnum != null && currEnum != Tab.noObj) {
			Tab.chainLocalSymbols(currEnum);
			Tab.closeScope();
		}
		
		currEnum = Tab.noObj;
		currEnumType = Tab.noType;
	}
	
	@Override
	public void visit(EnumDecl enumDecl) {
		
		if(currEnum == Tab.noObj || currEnumType == Tab.noType) return;

		if(Tab.currentScope.findSymbol(enumDecl.getI1()) != null) {
			report_error("Enum already defined : " + enumDecl.getI1(), enumDecl);
		}
		
		else { 
			//does enum have predefined value?
			
			int val;
			if(enumAssigned) val = enumAssign;
			else val = enumCurVal;
			
			for (int i = 0; i < enumSize; i++) {
				if(enumValues[i] == val) {
					report_error("Consant already defined in enum: " + enumDecl.getI1() + " , " + enumValues[i], enumDecl);
					return;
				}
			}
			
			Obj member = Tab.insert(Obj.Con, enumDecl.getI1(), currEnumType);
			member.setAdr(val);
			
			enumValues[enumSize++] = val;
			enumCurVal = val + 1;
		}
	}
	
	@Override
	public void visit(NumAssign_num numAssign_num) {
		enumAssign = numAssign_num.getN1();
		enumAssigned = true;
	}
	
	@Override
	public void visit(NumAssign_e NumAssign_e) {
		enumAssigned = false;
	}
	
	@Override
	public void visit(Constant_num constant_num) {
		constant = constant_num.getN1();
		constantType = Tab.intType;
	}
	
	@Override
	public void visit(Constant_char constant_char) {
		constant = constant_char.getC1();
		constantType = Tab.charType;
	}
	
	@Override
	public void visit(Constant_bool constant_bool) {
		constant = constant_bool.getB1();
		constantType = boolType;
	}
	
	@Override
	public void visit(Method method) {
		if(Tab.find(method.getI1()) != Tab.noObj) {
			report_error("Method name is already declared : " + method.getI1() , method);
		}
		else {
			currMeth = Tab.insert(Obj.Meth, method.getI1(), Tab.noType);
			method.obj = currMeth;
			
			Tab.openScope();			
		}
	}
	
	@Override
	public void visit(MethodDecl MethodDecl) {
		Tab.chainLocalSymbols(currMeth);
		Tab.closeScope();
		
		currMeth = null;
	}
	
	@Override
	public void visit (Statement_return statement_return) {
		if (currMeth == Tab.noObj) {
			report_error("Return statement must be inside a method. ", statement_return);
		}
	}
	
	@Override 
	public void visit(Statement_read statement_read) {
		Obj cur = statement_read.getDesignator().obj;
		
		if (cur.getKind() != Obj.Var && cur.getKind() != Obj.Elem) {
			report_error("Read argument must be a variable or array element. ", statement_read);
			return;
		}
		
		if (cur.getType() != Tab.intType && cur.getType() != Tab.charType && cur.getType() != Tab.find("bool").getType()) {
			report_error("Read argument type must be a int, char or boolean. ", statement_read);
			return;
		}
	}
	
	@Override
	public void visit (Statement_print statement_print) {
		Struct exprType = statement_print.getExpr().struct;
		
		if (exprType == null || exprType == Tab.noType) {
			return;
		}
		
		if (exprType != Tab.intType && exprType != Tab.charType && exprType != Tab.find("bool").getType()) {
			report_error("Print argument type must be a int, char or boolean. ", statement_print);
			return;
		}
	}
	
	@Override
	public void visit (Statement_findAny statement_findAny) {
		Obj des1 = statement_findAny.getDesignatorDot().getDesignator().obj;
		Obj des2 = statement_findAny.getDesignatorDot().getDesignator1().obj;
		
		if (des1 == Tab.noObj || des2 == Tab.noObj) {
			return;
		}
		
		if(des2.getKind() != Obj.Var && des2.getKind() != Obj.Elem) {
			report_error("findAny designator must be var or elem kind. ", statement_findAny);
			return;
		}
		
		if (des2.getType().getKind() != Struct.Array) {
			report_error("findAny function must be called for arrays only. ", statement_findAny);
			return;
		}
		
		if (des2.getType().getElemType().getKind() == Struct.Array) {
			report_error("findAny function supports one dimensional arrays only.", statement_findAny);
			return;
		}
		
		if (des1.getType().getKind() != Struct.Bool) {
			report_error("Left side of assignment must be boolean type. ", statement_findAny);
			return;
		}
		
		Struct exprType = statement_findAny.getExpr().struct;
		
		if (!(exprType.assignableTo(des2.getType().getElemType()))){
			report_error("findAny parameter and array elem type aren't compatible. ", statement_findAny);
			return;
		}
	}
	
	@Override
	public void visit(Statement_max statement_max) {
		Obj des1 = statement_max.getDesignatorDot().getDesignator().obj;
		Obj des2 = statement_max.getDesignatorDot().getDesignator1().obj;
		
		if (des1 == Tab.noObj || des2 == Tab.noObj) {
			return;
		}
		
		if(des2.getKind() != Obj.Var && des2.getKind() != Obj.Elem) {
			report_error("max designator must be var or elem kind. ", statement_max);
			return;
		}
		
		if (des2.getType().getKind() != Struct.Array) {
			report_error("max function must be called for arrays only. ", statement_max);
			return;
		}
		
		if (des2.getType().getElemType().getKind() == Struct.Array) {
			report_error("max function supports one dimensional arrays only.", statement_max);
			return;
		}
		
		if (des1.getType().getKind() != des2.getType().getElemType().getKind()) {
			report_error("Left side of assignment must be boolean type. ", statement_max);
			return;
		}
		
	}
	
	@Override
	public void visit(Statement_count statement_count) {
		Obj des1 = statement_count.getDesignatorDot().getDesignator().obj;
		Obj des2 = statement_count.getDesignatorDot().getDesignator1().obj;
		
		if (des1 == Tab.noObj || des2 == Tab.noObj) {
			return;
		}
		
		if(des2.getKind() != Obj.Var && des2.getKind() != Obj.Elem) {
			report_error("count designator must be var or elem kind. ", statement_count);
			return;
		}
		
		if (des2.getType().getKind() != Struct.Array) {
			report_error("count function must be called for arrays only. ", statement_count);
			return;
		}
		
		if (des2.getType().getElemType().getKind() == Struct.Array) {
			report_error("findAny function supports one dimensional arrays only.", statement_count);
			return;
		}
		
		if (des1.getType().getKind() != Struct.Int) {
			report_error("Left side of assignment must be int type. ", statement_count);
			return;
		}
		
		Struct exprType = statement_count.getExpr().struct;
		
		if(!exprType.assignableTo(des2.getType().getElemType())) {
			report_error("Expr type must match array's element type.", statement_count);
		}
	}
	
	
	
	//context - designators, factors, terms, expressions,
	
	
	
	@Override
	public void visit (DesignatorStatement designatorStatement) {
		Obj des = designatorStatement.getDesignator().obj;
		
		if(des == null || des == Tab.noObj) {
			return;
		}
		
		/*if(designatorStatement.getDesignatorList() instanceof DesignatorList_count) {
			DesignatorList_count assignment = (DesignatorList_count) designatorStatement.getDesignatorList();
			
			Obj des2 = assignment.getDesignator().obj;
			
			if (des == Tab.noObj || des2 == Tab.noObj) {
				return;
			}
			
			if(des2.getKind() != Obj.Var && des2.getKind() != Obj.Elem) {
				report_error("count designator must be var or elem kind. ", designatorStatement);
				return;
			}
			
			if (des2.getType().getKind() != Struct.Array) {
				report_error("count function must be called for arrays only. ", designatorStatement);
				return;
			}
			
			if (des2.getType().getElemType().getKind() == Struct.Array) {
				report_error("count function supports one dimensional arrays only.", designatorStatement);
				return;
			}
			
			Struct exprType = assignment.getExpr().struct;
			
			if(!exprType.assignableTo(des2.getType().getElemType())) {
				report_error("Expr type must match array's element type.", designatorStatement);
			}
			
		}*/
		
		if (designatorStatement.getDesignatorList() instanceof DesignatorList_assign) {
			DesignatorList_assign assignment = (DesignatorList_assign) designatorStatement.getDesignatorList();
			
			if(des.getKind() != Obj.Var && des.getKind() != Obj.Elem) {
				report_error("Left side of assignment must be var or elem. ", designatorStatement);
				return;
			}
			
			Struct srcType = assignment.getExpr().struct;
			Struct dstType = des.getType();
			
			if(srcType == null || srcType == Tab.noType) {
				return;
			}
			
			if(!srcType.assignableTo(dstType)) {
				report_error("Assignment types aren't compatible. ", designatorStatement);
			}
			
			return;
		}
		
		if (designatorStatement.getDesignatorList() instanceof DesignatorList_inc
				|| designatorStatement.getDesignatorList() instanceof DesignatorList_dec) {
			if (des.getKind() != Obj.Var && des.getKind() != Obj.Elem) {
				report_error("Operand must be var or elem. ", designatorStatement);
				return;
			}
			
			if(des.getType() != Tab.intType) {
				report_error("Can't increment or decrement non-int operand. ", designatorStatement);
			}
			
			return;
		}
		
		if (designatorStatement.getDesignatorList() instanceof DesignatorList_noActPars
				|| designatorStatement.getDesignatorList() instanceof DesignatorList_actpars) {
			report_error("Method calls are not supported in main. ", designatorStatement);
			return;
		}
	}
	
	
	@Override
	public void visit (Expr_arith expr_arith) {
		expr_arith.struct = expr_arith.getCondFact().struct;
	}
	
	@Override
	public void visit (Expr_cond expr_cond) {
		Struct condType = expr_cond.getCondFact().struct;
		Struct leftExpr = expr_cond.getExpr().struct;
		Struct rightExpr = expr_cond.getExpr1().struct;
		
		if(condType == Tab.noType || leftExpr == Tab.noType || rightExpr == Tab.noType) {
			expr_cond.struct = Tab.noType;
			return;
		}
		
		if (condType != Tab.find("bool").getType()) {
			report_error("Condition must be type boolean. ", expr_cond);
			expr_cond.struct = Tab.noType;
			return;
		}
	
		if (!leftExpr.equals(rightExpr)) {
			report_error("Ternary operator outcomes must have same type. ", expr_cond);
			expr_cond.struct = Tab.noType;
			return;
		}
		
		expr_cond.struct = leftExpr;
		
	}
	
	@Override
	public void visit (CondFact_relopExpr condFact_relopExpr) {
		Struct leftExpr = condFact_relopExpr.getExprTern().struct;
		Struct rightExpr = condFact_relopExpr.getExprTern1().struct;
		
		if (leftExpr == Tab.noType || rightExpr == Tab.noType) {
			condFact_relopExpr.struct = Tab.noType;
			return;
		}
		
		if (!leftExpr.compatibleWith(rightExpr)) {
			report_error("Relational operator operands must have compatible type. ", condFact_relopExpr);
			condFact_relopExpr.struct = Tab.noType;
			return;
		}
		
		boolean operator = condFact_relopExpr.getRelop() instanceof Relop_eq || 
				condFact_relopExpr.getRelop() instanceof Relop_neq ||
				condFact_relopExpr.getRelop() instanceof Relop_neq;
		
		if(leftExpr.getKind() == Struct.Array && !operator) {
			report_error("Arrays conditions must use only == or !=. ", condFact_relopExpr);
			condFact_relopExpr.struct = Tab.noType;
			return;
		}
		
		condFact_relopExpr.struct = Tab.find("bool").getType();
	}
	
	@Override
	public void visit (CondFact_expr condFact_expr) {
		condFact_expr.struct = condFact_expr.getExprTern().struct;
	}
	
	@Override
	public void visit (ExprTern_minus exprTern_minus) {
		Struct termType = exprTern_minus.getTerm().struct;
		Struct addTermType = exprTern_minus.getAddTerm().struct;
		
		if (termType != Tab.intType) {
			report_error("Unary minus operand must be type int.", exprTern_minus);
			exprTern_minus.struct = Tab.noType;
			return;
		}
		
		if (exprTern_minus.getAddTerm() instanceof AddTerm_e) {
			exprTern_minus.struct = termType;
			return;
		}
		
		if (addTermType != Tab.intType) {
			exprTern_minus.struct = Tab.noType;
			return;
		}
		
		exprTern_minus.struct = Tab.intType;
	}
	
	@Override
	public void visit (ExprTern_nonMinus exprTern_nonMinus) {
		Struct termType = exprTern_nonMinus.getTerm().struct;
		Struct addTermType = exprTern_nonMinus.getAddTerm().struct;
		
		if (exprTern_nonMinus.getAddTerm() instanceof AddTerm_e) {
			exprTern_nonMinus.struct = termType;
			return;
		}
		
		if (termType != Tab.intType) {
			report_error("Add operands must be type int.", exprTern_nonMinus);
			exprTern_nonMinus.struct = Tab.noType;
			return;
		}
		
		if (addTermType != Tab.intType) {
			exprTern_nonMinus.struct = Tab.noType;
			return;
		}
		
		exprTern_nonMinus.struct = Tab.intType;
	}
	
	@Override
	public void visit (AddTerm_add addTerm_add) {
		Struct termType = addTerm_add.getTerm().struct;
		Struct prevType = addTerm_add.getAddTerm().struct;
		
		if (termType != Tab.intType) {
			report_error("Add operands must be type int.", addTerm_add);
			addTerm_add.struct = Tab.noType;
			return;
		}
		
		if (!(addTerm_add.getAddTerm() instanceof AddTerm_e) && prevType != Tab.intType) {
			addTerm_add.struct = Tab.noType;
			return;
		}
		
		addTerm_add.struct = Tab.intType;
	}
	
	@Override
	public void visit (Term term) {
		Struct factorType = term.getFactor().struct;
		Struct mulFactorType = term.getMulFactor().struct;
		
		if(term.getMulFactor() instanceof MulFactor_e) {
			term.struct = factorType;
			return;
		}
		
		if (factorType != Tab.intType) {
			report_error("Mul operands must be type int.", term);
			term.struct = Tab.noType;
			return;
		}
		
		if (mulFactorType != Tab.intType) {
			term.struct = Tab.noType;
			return;
		}
		
		term.struct = Tab.intType;
	}
	
	@Override
	public void visit (MulFactor_mul mulFactor_mul) {
		Struct factorType = mulFactor_mul.getFactor().struct;
		Struct prevType = mulFactor_mul.getMulFactor().struct;
		
		if (factorType != Tab.intType) {
			report_error("Mul operand must be type int.", mulFactor_mul);
			mulFactor_mul.struct = Tab.noType;
			return;
		}
		
		if (!(mulFactor_mul.getMulFactor() instanceof MulFactor_e) && prevType != Tab.intType) {
			mulFactor_mul.struct = Tab.noType;
			return;
		}
		
		mulFactor_mul.struct = Tab.intType;
	}
	
	@Override
	public void visit (MulFactor_e mulFactor_e) {
		mulFactor_e.struct = Tab.noType;
	}
	
	@Override
	public void visit (Factor_num factor_num) {
		factor_num.struct = Tab.intType;
	}
	
	@Override
	public void visit (Factor_char factor_char) {
		factor_char.struct = Tab.charType;
	}
	
	@Override
	public void visit (Factor_bool factor_bool) {
		factor_bool.struct = boolType;
	}
	
	@Override
	public void visit (Factor_expr factor_expr) {
		factor_expr.struct = factor_expr.getExpr().struct;
	}
	
	@Override 
	public void visit (Factor_meth factor_meth) {
		Obj des = factor_meth.getDesignator().obj;
		
		if (des == Tab.noObj) {
			factor_meth.struct = Tab.noType;
			return;
		}
		
		if (des.getKind() != Obj.Meth) {
			report_error(factor_meth.getDesignator().obj.getName() + " is not a method.", factor_meth);
			factor_meth.struct = Tab.noType;
			return;
		}
		
		if(!(factor_meth.getActParsSingle() instanceof ActParsSingle_true)) {
			report_error(des.getName() + " requires 1 argument. " , factor_meth);
			factor_meth.struct = Tab.noType;
			return;
		}
		
		ActParsSingle_true actParsSingle = (ActParsSingle_true) factor_meth.getActParsSingle();
		
		ActPars actPars = actParsSingle.getActPars();
		
		if (!(actPars.getExprList() instanceof ExprList_e)) {
			report_error(des.getName() + " requires 1 argument. " , factor_meth);
			factor_meth.struct = Tab.noType;
			return;
		}
		
		Struct actParsType = actPars.getExpr().struct;
		
		switch (des.getName()) {
			case "ord" : 
				if (actParsType != Tab.charType) {
					report_error(des.getName() + " requires argument of type char. " , factor_meth);
					factor_meth.struct = Tab.noType;
					return;
				}
				
				factor_meth.struct = des.getType();
				break;
				
			case "len" :
				if (actParsType.getKind() != Struct.Array) {
					report_error(des.getName() + " requires argument of type Array. " , factor_meth);
					factor_meth.struct = Tab.noType;
					return;
				}
				
				factor_meth.struct = des.getType();
				break;
				
			case "chr" :
				if (actParsType != Tab.intType) {
					report_error(des.getName() + " requires argument of type int. " , factor_meth);
					factor_meth.struct = Tab.noType;
					return;
				}
				
				factor_meth.struct = des.getType();
				break;
				
			default :
				report_error("Unsupported method call" , factor_meth);
				factor_meth.struct = Tab.noType;
				return;
		}
	}
	
	@Override
	public void visit (Factor_des factor_des) {
		Obj cur = factor_des.getDesignator().obj;
		
		if (cur == Tab.noObj) {
			factor_des.struct = Tab.noType;
			return;
		}
		
		factor_des.struct = cur.getType();
	}
	
	@Override 
	public void visit (Factor_exprBrackets factor_exprBrackets) {
		Struct arrType = factor_exprBrackets.getType().struct;
		Struct sizeType = factor_exprBrackets.getExpr().struct;
		
		if (arrType == Tab.noType || sizeType == Tab.noType) {
			factor_exprBrackets.struct = Tab.noType;
			return;
		}
		
		if (sizeType != Tab.intType) {
			report_error("Size of an array must be int. ", factor_exprBrackets);
			return;
		}
		
		factor_exprBrackets.struct = new Struct(Struct.Array, arrType);
	}
	
	@Override
	public void visit (Designator_ident designator_ident) {
		Obj cur = Tab.find(designator_ident.getI1());
		if(cur == Tab.noObj) {
			report_error("Variable:  " + designator_ident.getI1() + " is not declared.", designator_ident);
			designator_ident.obj = Tab.noObj;
			return;
		}
		
		designator_ident.obj = cur;
	}
	
	@Override
	public void visit (Designator_dotIdent designator_dotIdent) {
		
		Obj base = designator_dotIdent.getDesignator().obj;
		
		if(base == Tab.noObj) {
			designator_dotIdent.obj = Tab.noObj;
			return;
		}
		
		if (base.getKind() != Obj.Type) {
			report_error("Base type is not a type, " , designator_dotIdent);
			designator_dotIdent.obj = Tab.noObj;
			return;
		}
		
		if (base.getType().getKind() != Struct.Enum) {
			report_error(base.getName() +  " is not an enum type." , designator_dotIdent);
			designator_dotIdent.obj = Tab.noObj;
			return;
		}
		
		Obj member = Tab.noObj;
		
		for (Obj cur : base.getLocalSymbols()) {
			if(cur.getName().equals(designator_dotIdent.getI2())) {
				member = cur;
				break;
			}
		}
		
		if (member == Tab.noObj) {
			report_error("Enum " + base.getName() + " has no member: " + designator_dotIdent.getI2(), designator_dotIdent);
			designator_dotIdent.obj = Tab.noObj;
			return;
		}
		
		designator_dotIdent.obj = member;
	}
	
	@Override
	public void visit (Designator_index designator_index) {
		Obj base = designator_index.getDesignator().obj;
		
		if (base == Tab.noObj) {
			report_error("Error getting base obj.", designator_index);
			designator_index.obj = Tab.noObj;
			return;
		}
		
		if (base.getKind() != Obj.Var) {
			report_error("Base: " + base.getName() + " is not variable.", designator_index);
			designator_index.obj = Tab.noObj;
			return;
		}
		
		if (base.getType().getKind() != Struct.Array) {
			report_error("Base: " + base.getName() + " is not an array.", designator_index);
			designator_index.obj = Tab.noObj;
			return;
		}
		
		if(designator_index.getExpr().struct != Tab.intType) {
			report_error("Array index must be int type.", designator_index);
			designator_index.obj = Tab.noObj;
			return;
		}
		
		designator_index.obj = new Obj(Obj.Elem, base.getName(), base.getType().getElemType());
	}
	
	@Override
	public void visit (Designator_dotLength designator_dotLength) {
		Obj base = designator_dotLength.getDesignator().obj;
		
		if (base == Tab.noObj) {
			report_error("Error getting base obj", designator_dotLength);
			designator_dotLength.obj = Tab.noObj;
			return;
		}
		
		if (base.getType().getKind() != Struct.Array) {
			report_error("Base: " +  base.getName() + " is not an array.", designator_dotLength);
			designator_dotLength.obj = Tab.noObj;
			return;
		}
		
		designator_dotLength.obj = new Obj(Obj.Con, base.getName(), Tab.intType);
	}
	
}
