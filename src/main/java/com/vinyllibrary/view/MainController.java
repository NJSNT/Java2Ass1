package com.vinyllibrary.view;

import com.vinyllibrary.model.Vinyl;
import com.vinyllibrary.viewmodel.MainViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Vinyl> vinylTableView;

    @FXML
    private TableColumn<Vinyl, String> idColumn;

    @FXML
    private TableColumn<Vinyl, String> titleColumn;

    @FXML
    private TableColumn<Vinyl, String> artistColumn;

    @FXML
    private TableColumn<Vinyl, Number> releaseYearColumn;

    @FXML
    private TableColumn<Vinyl, String> stateColumn;

    @FXML
    private TableColumn<Vinyl, String> borrowedByColumn;

    @FXML
    private TableColumn<Vinyl, String> reservedByColumn;

    @FXML
    private TableColumn<Vinyl, String> markedForRemovalColumn;

    @FXML
    private TextField userIdField;

    @FXML
    private Button reserveButton;

    @FXML
    private Button borrowButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button markForRemovalButton;

    @FXML
    private Button addVinylButton;

    private MainViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel = new MainViewModel();

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        releaseYearColumn.setCellValueFactory(cellData -> cellData.getValue().releaseYearProperty());
        stateColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStateName()));
        borrowedByColumn.setCellValueFactory(cellData -> cellData.getValue().borrowedByProperty());
        reservedByColumn.setCellValueFactory(cellData -> cellData.getValue().reservedByProperty());
        markedForRemovalColumn.setCellValueFactory(cellData -> {
            boolean marked = cellData.getValue().isMarkedForRemoval();
            return new javafx.beans.property.SimpleStringProperty(marked ? "Yes" : "No");
        });

        vinylTableView.setItems(viewModel.getVinylList());

        userIdField.textProperty().bindBidirectional(viewModel.currentUserIdProperty());

        vinylTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                viewModel.setSelectedVinylId(newVal.getId());
            }
        });

        updateButtonStates();
        vinylTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateButtonStates();
        });
    }

    private void updateButtonStates() {
        Vinyl selected = vinylTableView.getSelectionModel().getSelectedItem();
        boolean hasSelection = selected != null;
        reserveButton.setDisable(!hasSelection);
        borrowButton.setDisable(!hasSelection);
        returnButton.setDisable(!hasSelection);
        markForRemovalButton.setDisable(!hasSelection);
    }

    @FXML
    private void handleReserve() {
        viewModel.reserveVinyl();
    }

    @FXML
    private void handleBorrow() {
        viewModel.borrowVinyl();
    }

    @FXML
    private void handleReturn() {
        viewModel.returnVinyl();
    }

    @FXML
    private void handleMarkForRemoval() {
        viewModel.markForRemoval();
    }

    @FXML
    private void handleAddVinyl() {
        showAddVinylDialog();
    }

    private void showAddVinylDialog() {
        Dialog<Vinyl> dialog = new Dialog<>();
        dialog.setTitle("Add New Vinyl");
        dialog.setHeaderText("Enter vinyl details");

        ButtonType confirmButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField artistField = new TextField();
        artistField.setPromptText("Artist");
        TextField yearField = new TextField();
        yearField.setPromptText("Release Year");

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Artist:"), 0, 1);
        grid.add(artistField, 1, 1);
        grid.add(new Label("Release Year:"), 0, 2);
        grid.add(yearField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        javafx.util.Callback<ButtonType, Vinyl> resultConverter = buttonType -> {
            if (buttonType == confirmButtonType) {
                try {
                    String title = titleField.getText();
                    String artist = artistField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    String id = "V" + String.format("%03d", viewModel.getVinylList().size() + 1);
                    Vinyl vinyl = new Vinyl(id, title, artist, year);
                    viewModel.getLibrary().addVinyl(vinyl);
                    return vinyl;
                } catch (NumberFormatException e) {
                    showAlert("Invalid year format");
                }
            }
            return null;
        };

        dialog.setResultConverter(resultConverter);

        dialog.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public MainViewModel getViewModel() {
        return viewModel;
    }
}
