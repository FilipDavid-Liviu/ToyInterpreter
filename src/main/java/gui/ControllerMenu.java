package gui;


import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.ProgramState;
import model.adt.IMyStack;
import model.adt.MyStack;
import model.dt.*;
import model.expressions.*;
import model.statements.*;
import model.statements.lock.*;
import model.statements.procedure.CallStatement;
import model.statements.semaphore.AcquireStatement;
import model.statements.semaphore.CreateSemaphoreStatement;
import model.statements.semaphore.ReleaseStatement;
import model.types.IntegerType;
import model.types.ReferenceType;
import model.values.IntegerValue;
import repository.IRepository;
import repository.Repository;

import java.net.URL;
import java.util.*;

public class ControllerMenu implements Initializable {
    @FXML
    private ListView<String> examplesListView;
    @FXML
    private Button exitButton;
    @FXML
    private Label currentExample;

    private String currentExampleText;

    Map<Integer, Statement> examples = new HashMap<>();

    private void populateList(){
        Statement ext1 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new CompoundStatement(
                new AssignStatement("a", new ValueExpression(new IntegerValue(1))), new CompoundStatement(new VariableDeclarationStatement("b", new IntegerType()),
                new CompoundStatement(new AssignStatement("b", new ValueExpression(new IntegerValue(2))), new CompoundStatement(
                        new VariableDeclarationStatement("c", new IntegerType()), new CompoundStatement(new AssignStatement("c", new ValueExpression(new IntegerValue(5))),
                        new CompoundStatement(new SwitchStatement(new ArithmeticExpression("*", new VariableExpression("a"), new ValueExpression(new IntegerValue(10))),
                                new ArithmeticExpression("*", new VariableExpression("b"), new VariableExpression("c")),new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                                new ValueExpression(new IntegerValue(10)), new CompoundStatement(new PrintStatement(new ValueExpression(new IntegerValue(100))), new PrintStatement(new ValueExpression(new IntegerValue(200)))), new PrintStatement(new ValueExpression(new IntegerValue(300)))),
                                new PrintStatement(new ValueExpression(new IntegerValue(300))))))
                )))
        );

        Statement ext2 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntegerType())), new CompoundStatement(
                new VariableDeclarationStatement("cnt", new IntegerType()), new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntegerValue(1))),
                new CompoundStatement(new CreateSemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1"))), new CompoundStatement(
                        new ForkStatement(new CompoundStatement(new AcquireStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression("*",
                                new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(10)))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseStatement("cnt"))))), new CompoundStatement(
                                        new ForkStatement(new CompoundStatement(new AcquireStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression("*",
                                                new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(10)))), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression("*",
                                                new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(2)))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseStatement("cnt")))))),
                        new CompoundStatement(new AcquireStatement("cnt"), new CompoundStatement(new PrintStatement(new ArithmeticExpression("-", new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(1)))), new ReleaseStatement("cnt")))
                ))
                )))
        );

        //v=10;
        //(fork(v=v-1;v=v-1;print(v)); sleep(10);print(v*10)
        Statement ext3 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(10))),
                new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))),
                        new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))),
                                new PrintStatement(new VariableExpression("v"))))), new CompoundStatement(new SleepStatement(10),
                                        new PrintStatement(new ArithmeticExpression("*", new VariableExpression("v"), new ValueExpression(new IntegerValue(10))))))));

        //v=2;w=5;call sum(v*10,w);print(v);
        //fork(call product(v,w);
        //fork(call sum(v,w)))
        Statement ext4 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
                new CompoundStatement(new VariableDeclarationStatement("w", new IntegerType()), new CompoundStatement(new AssignStatement("w", new ValueExpression(new IntegerValue(5))),
                        new CompoundStatement(new CallStatement("sum", new ArrayList<>(List.of(new ArithmeticExpression("*", new VariableExpression("v"), new ValueExpression(new IntegerValue(10))), new VariableExpression("w")))),
                                new CompoundStatement( new PrintStatement(new VariableExpression("v")), new CompoundStatement(new ForkStatement(new CallStatement("product", new ArrayList<>(List.of(new VariableExpression("v"), new VariableExpression("w"))))),
                                        new ForkStatement(new CallStatement("sum", new ArrayList<>(List.of(new VariableExpression("v"), new VariableExpression("w"))))))))))));

        //Ref int a; new(a,20);
        //(for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
        //print(rh(a))
        Statement ext5 = new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("a", new ValueExpression(new IntegerValue(20))),
                new CompoundStatement(new ForStatement("v", new ValueExpression(new IntegerValue(0)), new ValueExpression(new IntegerValue(3)), new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(1))),
                        new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression("*", new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a"))))))),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));

        //Ref int v1; Ref int v2; int x; int q;
        //new(v1,20);new(v2,30);newLock(x);
        //fork(
        //fork(
        //lock(x);wh(v1,rh(v1)-1);unlock(x)
        //);
        //lock(x);wh(v1,rh(v1)*10);unlock(x)
        //);newLock(q);
        //fork(
        //fork(lock(q);wh(v2,rh(v2)+5);unlock(q));
        //lock(q);wh(v2,rh(v2)*10);unlock(q)
        //);
        //nop;nop;nop;nop;
        //lock(x); print(rh(v1)); unlock(x);
        //lock(q); print(rh(v2)); unlock(q);

        Statement ext6 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntegerType())), new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntegerType())),
                new CompoundStatement(new VariableDeclarationStatement("x", new IntegerType()), new CompoundStatement(new VariableDeclarationStatement("q", new IntegerType()),
                        new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntegerValue(20))), new CompoundStatement(new NewStatement("v2", new ValueExpression(new IntegerValue(30))),
                                new CompoundStatement(new CreateLockStatement("x"), new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("x"),
                                        new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression("-", new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(1)))),
                                                new UnlockStatement("x")))), new CompoundStatement(new LockStatement("x"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression("*", new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(10)))),
                                                        new UnlockStatement("x"))))), new CompoundStatement(new CreateLockStatement("q"), new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("q"),
                                                        new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression("+", new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntegerValue(5)))),
                                                                new UnlockStatement("q")))), new CompoundStatement(new LockStatement("q"), new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression("*", new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntegerValue(10)))),
                                                                        new UnlockStatement("q"))))), new CompoundStatement(new NOpStatement(), new CompoundStatement(new NOpStatement(), new CompoundStatement(new NOpStatement(), new CompoundStatement(new NOpStatement(),
                                                                                new CompoundStatement(new LockStatement("x"), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new CompoundStatement(new UnlockStatement("x"), new CompoundStatement(new LockStatement("q"),
                                                                                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))), new UnlockStatement("q"))))))))))))))))))));

//        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
//                new PrintStatement(new VariableExpression("v"))));
//
//        Statement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntegerType()),
//                new CompoundStatement(new AssignStatement("a", new ArithmeticExpression("+", new ValueExpression(new IntegerValue(2)), new ArithmeticExpression("*", new ValueExpression(new IntegerValue(3)), new ValueExpression(new IntegerValue(5))))),
//                        new CompoundStatement(new AssignStatement("b", new ArithmeticExpression("+", new VariableExpression("a"), new ValueExpression(new IntegerValue(1)))), new PrintStatement(new VariableExpression("b"))))));
//
//        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BooleanType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
//                new CompoundStatement(new AssignStatement("a", new ValueExpression(new BooleanValue(true))), new CompoundStatement(new IfStatement(new VariableExpression("a"),
//                        new AssignStatement("v",new ValueExpression(new IntegerValue(2))), new AssignStatement("v", new ValueExpression(new IntegerValue(3)))), new PrintStatement(new VariableExpression("v"))))));
//
//        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("f", new StringType()), new CompoundStatement(new AssignStatement("f", new ValueExpression(new StringValue("test.in"))),
//                new CompoundStatement(new OpenFileStatement(new VariableExpression("f")), new CompoundStatement(new VariableDeclarationStatement("c", new IntegerType()),
//                        new CompoundStatement(new ReadFileStatement(new VariableExpression("f"), "c"), new CompoundStatement(new PrintStatement(new VariableExpression("c")),
//                                new CompoundStatement(new ReadFileStatement(new VariableExpression("f"), "c"), new CompoundStatement(new PrintStatement(new VariableExpression("c")),
//                                        new CloseFileStatement(new VariableExpression("f"))))))))));
//
//        Statement ex5 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(4))),
//                new CompoundStatement(new DoWhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntegerValue(0))),
//                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))))), new PrintStatement(new VariableExpression("v")))));
//
//        Statement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
//                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
//                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));
//
//        Statement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
//                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
//                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new PrintStatement(new ArithmeticExpression("+", new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntegerValue(5)))))))));
//
//        Statement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
//                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntegerValue(30))),
//                        new PrintStatement(new ArithmeticExpression("+", new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntegerValue(5))))))));
//
//        Statement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
//                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
//                        new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(30))), new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));
//
//        Statement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(20))),
//                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))), new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
//                        new CompoundStatement(new NewStatement("v", new ValueExpression(new IntegerValue(30))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))),
//                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))))));
//
//        Statement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
//                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(10))), new CompoundStatement(new NewStatement("a", new ValueExpression(new IntegerValue(22))),
//                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntegerValue(30))), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(32))),
//                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
//                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));
//
//        Statement ex12 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntegerValue(2))),
//                new SwitchCaseStatement(new VariableExpression("a"), new PrintStatement(new ValueExpression(new StringValue("falsest"))),
//                        new CaseStatement[] {new CaseStatement(new ValueExpression(new IntegerValue(1)), new PrintStatement(new ValueExpression(new StringValue("false")))),
//                                new CaseStatement(new ValueExpression(new IntegerValue(2)), new PrintStatement(new ValueExpression(new StringValue("true")))) } )));
//
//        Statement ex13 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new ForLoopStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
//                new BinaryExpression("<", new VariableExpression("v"), new ValueExpression(new IntegerValue(10))), new IncrementStatement("v"),
//                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))))),
//                                new CompoundStatement(new IncrementStatement("v", true), new PrintStatement(new VariableExpression("v")))));
//
//        Statement ex14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new SleepStatement(3),
//                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(10))), new CompoundStatement(new ExitStatement(), new PrintStatement(new VariableExpression("v"))))));
//
//        Statement ex15 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(4))),
//                new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntegerValue(2))),
//                        new CompoundStatement(new SwapStatement("v", "a"), new PrintStatement(new VariableExpression("v")))))));
//
//        Statement ex16 =  new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new VariableDeclarationStatement("s", new IntegerType())), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
//                new CompoundStatement(new CreateSemaphoreStatement("s", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntegerValue(0)))),
//                        new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(3)))),
//                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new CompoundStatement(new AcquireStatement("s"),
//                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new ReleaseStatement("s")))))),
//                                new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(4)))),
//                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new CompoundStatement(new AcquireStatement("s"),
//                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new ReleaseStatement("s")))))), new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))),
//                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new CompoundStatement(new AcquireStatement("s"),
//                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new ReleaseStatement("s")))))), new CompoundStatement(new AcquireStatement("s"), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new ReleaseStatement("s")))))))));
//        Statement ex20 =  new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new VariableDeclarationStatement("s", new IntegerType())), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(2))),
//                new CompoundStatement(new CreateLockStatement("s"), new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(3)))),
//                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new CompoundStatement(new LockStatement("s"),
//                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new UnlockStatement("s")))))),
//                                new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(4)))),
//                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new CompoundStatement(new LockStatement("s"),
//                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new UnlockStatement("s")))))), new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))),
//                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new CompoundStatement(new LockStatement("s"),
//                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new UnlockStatement("s")))))), new CompoundStatement(new LockStatement("s"), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new UnlockStatement("s")))))))));
//        Statement ex17 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new ConditionalAssignmentStatement("v", new BinaryExpression("==", new VariableExpression("v"), new ValueExpression(new IntegerValue(1))), new ValueExpression(new IntegerValue(99)), new ValueExpression(new IntegerValue(77))),
//                new PrintStatement(new VariableExpression("v"))));
//
//        Statement ex18 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new ForStatement("a", new ValueExpression(new IntegerValue(0)), new ValueExpression(new IntegerValue(3)), new BinaryExpression("+", new VariableExpression("a"), new ValueExpression(new IntegerValue(1))), new PrintStatement(new VariableExpression("v"))), new PrintStatement(new VariableExpression("a"))));

//        Statement ex19 = new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntegerValue(1))),
//                new SwitchStatement(new VariableExpression("v"), new ValueExpression(new IntegerValue(0)), new PrintStatement(new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(5)))),
//                        new ValueExpression(new IntegerValue(1)), new PrintStatement(new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(10)))), new PrintStatement(new VariableExpression("v")))));

//        examples.add(ex1);
//        examples.add(ex2);
//        examples.add(ex3);
//        examples.add(ex4);
//        examples.add(ex5);
//        examples.add(ex6);
//        examples.add(ex7);
//        examples.add(ex8);
//        examples.add(ex9);
//        examples.add(ex10);
//        examples.add(ex11);
//        examples.add(ex12);
//        examples.add(ex13);
//        examples.add(ex14);
//        examples.add(ex15);
//        examples.add(ex16);
//        examples.add(ex17);
//        examples.add(ex18);
//        examples.add(ex19);

        examples.put(0, ext5);
        examples.put(1, ext6);
        examples.put(2, ext6);
        examples.put(3, ext6);
        examples.put(4, ext6);

    }

    private static Controller initController(String id, Statement ex) {
        return initController(id, ex, new ProcedureTable());
    }

    private static Controller initController(String id, Statement ex, ProcedureTable procedureTable){
        IExecutionStack stack = new ExecutionStack();
        IHeap heap = new Heap();
        ISymbolTable symT = new SymbolTable();
        IMyStack<ISymbolTable> stackSym = new MyStack<>();
        stackSym.push(symT);
        IOutput out = new Output();
        IFileTable fileTable = new FileTable();
        ILockTable lockTable = new LockTable();
        ISemaphoreTable semaphoreTable = new SemaphoreTable();
//        procedure sum(a,b) v=a+b;print(v)
//        procedure product(a,b) v=a*b;print(v)
//        procedureTable.addProcedure("sum", new ArrayList<>(List.of("a", "b")), new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
//                new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("+", new VariableExpression("a"), new VariableExpression("b"))),
//                        new PrintStatement(new VariableExpression("v")))));
//        procedureTable.addProcedure("product", new ArrayList<>(List.of("a", "b")), new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
//                new CompoundStatement(new AssignStatement("v", new ArithmeticExpression("*", new VariableExpression("a"), new VariableExpression("b"))),
//                        new PrintStatement(new VariableExpression("v")))));
        System.out.println("Type checking...");
        System.out.println(ex.toString());
        try {
            ex.typeCheck(new TypeDictionary());
        }
        catch (RuntimeException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TypeChecker Error");
            alert.setHeaderText("Error while compiling the program");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw e;
        }
        ProgramState prg = new ProgramState(stack, stackSym, heap, out, fileTable, procedureTable, lockTable, semaphoreTable, ex);
        IRepository repo = new Repository("logs/log" + id + ".txt");
        repo.add(prg);
        return new Controller(repo);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateList();
        for (Statement example : examples.values()) {
            examplesListView.getItems().add(example.toString());
        }

        examplesListView.getSelectionModel().selectedItemProperty().addListener(((_, _, _) -> {
            currentExampleText = examplesListView.getSelectionModel().getSelectedItem();
            currentExample.setText(currentExampleText);
        }));
    }

    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private Integer searchExample(String exampleText){
//        for (int i = 0; i < examples.size(); i++) {
//            if (examples.get(i).toString().equals(exampleText)) {
//                return i;
//            }
//        }
        for (Map.Entry<Integer, Statement> entry : examples.entrySet()) {
            if (entry.getValue().toString().equals(exampleText)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void removeExample(Integer id){
        examples.remove((int) id);
        examplesListView.getItems().clear();
        for (Statement example : examples.values()) {
            examplesListView.getItems().add(example.toString());
        }
    }

    public void run(ActionEvent actionEvent) {
        try {
            Integer id = searchExample(currentExampleText);
            if (id != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(ToyGUI.class.getResource("run_all.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add("file:src/main/styles.css");
                ControllerRun controllerRun = fxmlLoader.getController();
                Controller controller = initController(id.toString(), examples.get(id));
                controllerRun.setExample(controller);

                Image icon = new Image("file:src/main/icon.png");
                Stage stage = new Stage();
                stage.getIcons().add(icon);
                stage.setTitle("Run");
                stage.setScene(scene);
                stage.show();
                removeExample(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
