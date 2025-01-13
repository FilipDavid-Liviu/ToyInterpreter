package com.example.toylanguage;

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
import model.adt.KeyValuePair;
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
        idsListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
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
