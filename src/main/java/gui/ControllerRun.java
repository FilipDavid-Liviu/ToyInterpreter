package gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ProgramState;
import gui.helpers.KeyValuePair;
import gui.helpers.KeyValueTuple;
import model.statements.Statement;


public class ControllerRun {
    @FXML
    public TextField programStatesField;
    public Button oneStepButton;
    public Button allStepsButton;
    public ListView<String> fileListView;
    public ListView<String> outputListView;
    public ListView<String> idsListView;
    public ListView<String> executionListView;
    public Label idLabel;
    public TableColumn<KeyValuePair, String> addressColumn;
    public TableColumn<KeyValuePair, String> heapValueColumn;
    public TableColumn<KeyValuePair, String> variableColumn;
    public TableColumn<KeyValuePair, String> symbolValueColumn;
    public TableView<KeyValuePair> heapTableView;
    public TableView<KeyValuePair> symbolTableView;
    public Button exitButton;

    public TableColumn<KeyValuePair, String> lockIndexColumn;
    public TableColumn<KeyValuePair, String> lockPermitColumn;
    public TableView<KeyValuePair> lockTableView;

    public TableColumn<KeyValueTuple, String> semaphoreIndexColumn;
    public TableColumn<KeyValueTuple, String> semaphoreSizeColumn;
    public TableColumn<KeyValueTuple, String> semaphorePermitsColumn;
    public TableView<KeyValueTuple> semaphoreTableView;

    public TableColumn<KeyValueTuple, String> procedureNameColumn;
    public TableColumn<KeyValueTuple, String> procedureParametersColumn;
    public TableColumn<KeyValueTuple, String> procedureBodyColumn;
    public TableView<KeyValueTuple> procedureTableView;


    private Controller controller;
    private String currentId;


    public void setExample(Controller controller) {
        this.controller = controller;
        for (ProgramState program : controller.getRepo().getProgramList()) {
            idsListView.getItems().add(String.valueOf(program.getId()));
        }
        variableColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        symbolValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        variableColumn.setCellFactory(this::createWrappingCellFactory);
        symbolValueColumn.setCellFactory(this::createWrappingCellFactory);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        addressColumn.setCellFactory(this::createWrappingCellFactory);
        heapValueColumn.setCellFactory(this::createWrappingCellFactory);

        lockIndexColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        lockPermitColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        lockIndexColumn.setCellFactory(this::createWrappingCellFactory);
        lockPermitColumn.setCellFactory(this::createWrappingCellFactory);

        semaphoreIndexColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        semaphoreSizeColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        semaphorePermitsColumn.setCellValueFactory(new PropertyValueFactory<>("value2"));
        semaphoreIndexColumn.setCellFactory(this::createWrappingCellFactoryTuple);
        semaphoreSizeColumn.setCellFactory(this::createWrappingCellFactoryTuple);
        semaphorePermitsColumn.setCellFactory(this::createWrappingCellFactoryTuple);

        procedureNameColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        procedureParametersColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        procedureBodyColumn.setCellValueFactory(new PropertyValueFactory<>("value2"));
        procedureNameColumn.setCellFactory(this::createWrappingCellFactoryTuple);
        procedureParametersColumn.setCellFactory(this::createWrappingCellFactoryTuple);
        procedureBodyColumn.setCellFactory(this::createWrappingCellFactoryTuple);

        idsListView.getSelectionModel().selectedItemProperty().addListener(((_, _, _) -> {
            currentId = idsListView.getSelectionModel().getSelectedItem();
            if (currentId == null) {
                idLabel.setText("Current ID Selected: ");
            }
            else idLabel.setText("Current ID Selected: " + currentId);
            populateById(currentId);
        }));
        update();
    }

    public void update() {
        programStatesField.setText(String.valueOf(controller.getRepo().getProgramList().size()));
        try {
            populate();
        }
        catch (Exception _) {
        }
    }

    private void populate() {

        ObservableList<String> items = FXCollections.observableArrayList(
                controller.getRepo().getProgramList()
                        .stream()
                        .map(e -> String.valueOf(e.getId()))
                        .toList());
        idsListView.setItems(items);
        ProgramState commonProgram;
        try {
            commonProgram = controller.getRepo().getProgramList().getFirst();
        }
        catch (Exception e) {
            return;
        }

        items = FXCollections.observableArrayList(
                commonProgram.getFileTable().getData().getKeys()
                        .stream()
                        .map(Object::toString)
                        .toList());
        fileListView.setItems(items);

        items = FXCollections.observableArrayList(commonProgram.getOutput().getData().getAll());
        outputListView.setItems(items);

        ObservableList<KeyValuePair> heapItems = FXCollections.observableArrayList(
                commonProgram.getHeap().getData().getKeys()
                        .stream()
                        .map(e -> new KeyValuePair(String.valueOf(e), commonProgram.getHeap().getData().lookUp(e).toString()))
                        .toList());
        heapTableView.setItems(heapItems);

        ObservableList<KeyValuePair> lockItems = FXCollections.observableArrayList(
                commonProgram.getLockTable().getData().getKeys()
                        .stream()
                        .map(e -> new KeyValuePair(String.valueOf(e), (commonProgram.getLockTable().getData().lookUp(e)!=-1?commonProgram.getLockTable().getData().lookUp(e).toString():"null")))
                        .toList());
        lockTableView.setItems(lockItems);

        ObservableList<KeyValueTuple> semaphoreItems = FXCollections.observableArrayList(
                commonProgram.getSemaphoreTable().getData().getKeys()
                        .stream()
                        .map(e -> new KeyValueTuple(String.valueOf(e), commonProgram.getSemaphoreTable().getData().lookUp(e).getFirst().toString(), commonProgram.getSemaphoreTable().getData().lookUp(e).getSecond().toString()))
                        .toList());
        semaphoreTableView.setItems(semaphoreItems);

        ObservableList<KeyValueTuple> procedureItems = FXCollections.observableArrayList(
                commonProgram.getProcedureTable().getData().getKeys()
                        .stream()
                        .map(e -> new KeyValueTuple(String.valueOf(e), commonProgram.getProcedureTable().getData().lookUp(e).getFirst().toString(), commonProgram.getProcedureTable().getData().lookUp(e).getSecond().toString()))
                        .toList());
        procedureTableView.setItems(procedureItems);

        populateById(currentId);
    }

    private TableCell<KeyValuePair, String> createWrappingCellFactory(TableColumn<KeyValuePair, String> column) {
        return new TableCell<>() {
            private final Text text = new Text();

            {
                text.wrappingWidthProperty().bind(column.widthProperty());
                setGraphic(text);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    text.setText(null);
                } else {
                    text.setText(item);
                }
            }
        };
    }

    private TableCell<KeyValueTuple, String> createWrappingCellFactoryTuple(TableColumn<KeyValueTuple, String> column) {
        return new TableCell<>() {
            private final Text text = new Text();

            {
                text.wrappingWidthProperty().bind(column.widthProperty());
                setGraphic(text);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    text.setText(null);
                } else {
                    text.setText(item);
                }
            }
        };
    }

    private void populateById (String id) {
        ProgramState currentProgram = controller.getRepo().getProgramList().stream()
                .filter(e -> String.valueOf(e.getId()).equals(id))
                .findFirst()
                .orElse(null);
        if (currentProgram == null) {
            executionListView.setItems(null);
            symbolTableView.setItems(null);
            return;
        }
        ObservableList<String> items = FXCollections.observableArrayList(
                currentProgram.getExecutionStack().getAllInList()
                        .stream()
                        .map(Statement::toString)
                        .toList());
        executionListView.setItems(items);

        ObservableList<KeyValuePair> symbolItems = FXCollections.observableArrayList(
                currentProgram.getSymbolTable().getData().getKeys()
                        .stream()
                        .map(e -> new KeyValuePair(e, currentProgram.getSymbolTable().getData().lookUp(e).toString()))
                        .toList());
        symbolTableView.setItems(symbolItems);
    }


    public void runOneStep(ActionEvent actionEvent) {
        controller.oneStepForAllPrg();
        update();
        if (controller.getRepo().getProgramList().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Program finished!");
            alert.getDialogPane().setStyle("-fx-font-family: Consolas");
            alert.show();
        }
    }

    public void runAllSteps(ActionEvent actionEvent) {
        if (controller.getRepo().getProgramList().isEmpty()) {
            update();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Program finished!");
            alert.getDialogPane().setStyle("-fx-font-family: Consolas");
            alert.show();
            return;
        }
        while (!controller.getRepo().getProgramList().isEmpty()) {
            controller.oneStepForAllPrg();
            update();
        }
    }

    public void exit(ActionEvent actionEvent) {
        controller.shutdownExecutor();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
