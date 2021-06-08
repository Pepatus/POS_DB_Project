package app;

import bll.*;
import dal.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableView tvPeople;
    TableColumn<Person, String> tcfirstName = null;
    TableColumn<Person, String> tclastName = null;

    @FXML
    private ListView<Activity> lvActivities;
    @FXML
    private ComboBox<Season> cbSeason;
    private List<Person> personList = new ArrayList<Person>();
    private List<Activity> activitieList = new ArrayList<Activity>();
    private List<Season> seasonList = new ArrayList<Season>();

    private Activity currentActivitie;
    private Season currentSeason;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getPersonsFromDb();
        getActivitiesFromDb();
        getSeasonsFromDb();
        setComboBox();


        this.cbSeason.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                currentSeason = (Season)newValue;
                showAvailableActivities();
            }
        });
        this.cbSeason.getSelectionModel().select(0);

        this.lvActivities.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.lvActivities.getSelectionModel().select(0);
        this.lvActivities.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                currentActivitie = (Activity)newValue;
                showParticipants();
            }
        });

    }

    private void showAvailableActivities() {
        lvActivities.setItems(FXCollections.observableArrayList(getAvailableActivities(activitieList)));
    }

    private List<Activity> getAvailableActivities(List<Activity> activitieList) {
        List<Activity> activities = new ArrayList<Activity>();
        activitieList.forEach(e -> {
            if(e.getIdSeason() == currentSeason.getId()){
                activities.add(e);
            }
        });
        return activities;
    }

    private void setComboBox() {
        ObservableList<Season> options =
                FXCollections.observableArrayList(seasonList);
        cbSeason.setItems(options);
    }

    private List<String> listToStringList(List<Season> seasonList) {
        List<String> s = new ArrayList<String>();
        seasonList.forEach(e -> {
            s.add(e.getIndentifier());
        });
        return s;
    }


    private void showParticipants(){
        List<Person> personsInActivitie = new ArrayList<Person>();
        personList.forEach( e -> {
            if(e.getIdActivity() == currentActivitie.getId()){
                personsInActivitie.add(e);
            }
        });

        this.tvPeople.setItems(FXCollections.observableArrayList(personsInActivitie));
        this.createColumns();
        this.configureTableView();
    }
    private void createColumns() {

        // Instanziierung der Columns und Festlegen des Datentyps
        tcfirstName = new TableColumn<Person, String>("Firstname");
        tclastName = new TableColumn<Person, String>("Lastname");

        // Festlegen des Inhaltes der Spalte (Eigenschaft des Objektes)
        // Achtung es muss dafür den  getter mit dem entsprechenden Namen geben
        tcfirstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        tclastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>> booleanCellFactory =
                new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {
                    @Override
                    public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {
                        return new BooleanCell();

                    }
                };

        // hinzufügen der Columns zur TableView
        this.tvPeople.getColumns().addAll(tcfirstName, tclastName);

    }
    class BooleanCell extends TableCell<Person, Boolean> {
        private CheckBox checkBox;
        public BooleanCell() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(isEditing())
                        commitEdit(newValue == null ? false : newValue);
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
            checkBox.setDisable(false);
            checkBox.requestFocus();
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            checkBox.setDisable(true);
        }
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            checkBox.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                checkBox.setSelected(item);
            }
        }
    }


    public void configureTableView() {
        this.tvPeople.setEditable(true);

        tcfirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        tcfirstName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFirstName(t.getNewValue());
            }
        });
        tclastName.setCellFactory(TextFieldTableCell.forTableColumn());

        tclastName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Player, String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());

            }

        });

        tcgoals.setCellFactory(TextFieldTableCell.<Player, Integer>forTableColumn(new IntegerStringConverter()));

        tcgoals.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Player, Integer> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGoals(t.getNewValue());

            }

        });

        ObservableList<String> teams = FXCollections.observableArrayList("Rapid", "Salzburg", "Bayern München",
                "Augsburg");
        tcteam.setCellFactory(ComboBoxTableCell.forTableColumn(teams));
        tcteam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Player, String> t) {
                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTeam(t.getNewValue());

            }


        });
        tcnumberOfGames.setCellFactory(TextFieldTableCell.<Player, Integer>forTableColumn(new IntegerStringConverter()));

        tcnumberOfGames.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Player, Integer> t) {

                ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGoals(t.getNewValue());

            }

        });
    }


    private void getSeasonsFromDb(){
        seasonList = DataBaseManager.getInstance().getAllSeasons();
    }

    private void getActivitiesFromDb(){
        activitieList = DataBaseManager.getInstance().getAllActivities();
    }

    private void getPersonsFromDb(){
        personList = DataBaseManager.getInstance().getAllPersons();
    }
}
