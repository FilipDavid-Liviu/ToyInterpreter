package view;

import controller.Controller;
import model.ProgramState;
import model.adt.IMyStack;
import model.adt.MyStack;
import model.dt.*;
import model.expressions.*;
import model.statements.*;
import model.statements.semaphore.*;
import model.types.*;
import model.values.*;
import repository.*;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.util.ArrayList;
import java.util.List;


public class Interpreter {

    public static void main(String[] args) {
        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
                new PrintStatement(new VariableExpression("v"))));

        Statement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntegerType()),
                new CompoundStatement(new AssignStatement("a", new ArithmeticExpression("+", new ValueExpression(new IntegerValue(2)), new ArithmeticExpression("*", new ValueExpression(new IntegerValue(3)), new ValueExpression(new IntegerValue(5))))),
                        new CompoundStatement(new AssignStatement("b", new ArithmeticExpression("+", new VariableExpression("a"), new ValueExpression(new IntegerValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BooleanType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(new AssignStatement("a", new ValueExpression(new BooleanValue(true))), new CompoundStatement(new IfStatement(new VariableExpression("a"),
                        new AssignStatement("v",new ValueExpression(new IntegerValue(2))), new AssignStatement("v", new ValueExpression(new IntegerValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("f", new StringType()), new CompoundStatement(new AssignStatement("f", new ValueExpression(new StringValue("test.in"))),
                new CompoundStatement(new OpenFileStatement(new VariableExpression("f")), new CompoundStatement(new VariableDeclarationStatement("c", new IntegerType()),
                        new CompoundStatement(new ReadFileStatement(new VariableExpression("f"), "c"), new CompoundStatement(new PrintStatement(new VariableExpression("c")),
                                new CompoundStatement(new ReadFileStatement(new VariableExpression("f"), "c"), new CompoundStatement(new PrintStatement(new VariableExpression("c")),
                                        new CloseFileStatement(new VariableExpression("f"))))))))));

        Statement ex5 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(4))),
                new CompoundStatement(new DoWhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntegerValue(0))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))))), new PrintStatement(new VariableExpression("v")))));

        Statement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));

        Statement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new PrintStatement(new ArithmeticExpression("+", new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntegerValue(5)))))))));

        Statement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntegerValue(30))),
                        new PrintStatement(new ArithmeticExpression("+", new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntegerValue(5))))))));

        Statement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                        new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(30))), new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));

        Statement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                        new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(30))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))))));

        Statement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(10))), new CompoundStatement(new NewStatement("a", new ValueExpression(new IntegerValue(22))),
                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntegerValue(30))), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(32))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

        //int a; a=2; switch(a) (case 1: print("false"); case 2: print("true"); default: print("falsest"))
        Statement ex12 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntegerValue(2))),
                new SwitchCaseStatement(new VariableExpression("a"), new PrintStatement(new ValueExpression(new StringValue("falsest"))),
                        new CaseStatement[] {new CaseStatement(new ValueExpression(new IntegerValue(1)), new PrintStatement(new ValueExpression(new StringValue("false")))),
                                new CaseStatement(new ValueExpression(new IntegerValue(2)), new PrintStatement(new ValueExpression(new StringValue("true")))) } )));


        //int v; for(v=2; v < 10; ++v){print(v); v=v+1}; v--; print(v)
        Statement ex13 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new ForLoopStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
                new BinaryExpression("<", new VariableExpression("v"), new ValueExpression(new IntegerValue(10))), new IncrementStatement("v"),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))))),
                new CompoundStatement(new IncrementStatement("v", true), new PrintStatement(new VariableExpression("v")))));

        //int v; sleep(3); v=10; exit; print(v)
        Statement ex14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new SleepStatement(3),
                new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntegerValue(10))), new CompoundStatement(new ExitStatement(), new PrintStatement(new VariableExpression("v"))))));

        //int v; v=4; int a; a=2; swap(v,a); print(v);
        Statement ex15 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(4))),
                new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntegerValue(2))),
                        new CompoundStatement(new SwapStatement("v", "a"), new PrintStatement(new VariableExpression("v")))))));

//        procedure sum(a,b) v=a+b;print(v)
//        procedure product(a,b) v=a*b;print(v)
//        procTable.addProcedure("sum", new ArrayList<>(List.of("a", "b")), new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
//                new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("a"), new VariableExpression("b"))),
//                        new PrintStatement(new VariableExpression("v")))));
//        procTable.addProcedure("product", new ArrayList<>(List.of("a", "b")), new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
//                new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("*", new VariableExpression("a"), new VariableExpression("b"))),
//                        new PrintStatement(new VariableExpression("v")))));


        TextMenu menu = new TextMenu();
        addCommand(menu, "1", ex1);
        addCommand(menu, "2", ex2);
        addCommand(menu, "3", ex3);
        addCommand(menu, "4", ex4);
        addCommand(menu, "5", ex5);
        addCommand(menu, "6", ex6);
        addCommand(menu, "7", ex7);
        addCommand(menu, "8", ex8);
        addCommand(menu, "9", ex9);
        addCommand(menu, "10", ex10);
        addCommand(menu, "11", ex11);
        addCommand(menu, "12", ex12);
        addCommand(menu, "13", ex13);
        addCommand(menu, "14", ex14);
        addCommand(menu, "15", ex15);


        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
//        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
//        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
//        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
//        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
//        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
//        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
//        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
//        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
//        menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
//        menu.addCommand(new RunExample("11", ex11.toString(), ctr11));
//        menu.addCommand(new RunExample("12", ex12.toString(), ctr12));
//        menu.addCommand(new RunExample("13", ex13.toString(), ctr13));
//        menu.addCommand(new RunExample("14", ex14.toString(), ctr14));
//        menu.addCommand(new RunExample("15", ex15.toString(), ctr15));
        menu.show();
    }

    public static void addCommand(TextMenu menu, String id, Statement ex){
        IExecutionStack stack = new ExecutionStack();
        IHeap heap = new Heap();
        ISymbolTable symT = new SymbolTable();
        IMyStack<ISymbolTable> stackSym = new MyStack<>();
        stackSym.push(symT);
        IOutput out = new Output();
        IFileTable fileTable = new FileTable();
        ISemaphoreTable semTable = new SemaphoreTable();
        ILockTable lockTable = new LockTable();
        IProcedureTable procTable = new ProcedureTable();
        System.out.println("Type checking...");
        System.out.println(id + ":  " + ex.toString());
        try {
            ex.typeCheck(new TypeDictionary());
            ProgramState prg = new ProgramState(stack, stackSym, heap, out, fileTable, procTable, lockTable, semTable, ex);
            IRepository repo = new Repository("logs/log" + id + ".txt");
            repo.add(prg);
            Controller ctr = new Controller(repo);
            menu.addCommand(new RunExample(id, ex.toString(), ctr));
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}