package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableView tbPeople;
    @FXML
    private ListView lvActivities;
    @FXML
    private ComboBox cbSeason;
    private List<Person> personList = new ArrayList<Person>();
    private List<Activitie> activitieList = new ArrayList<Activitie>;
    private List<Season> seasonList = new ArrayList<Season>();

    private Activitie currentActivitie;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getPersonsFromDb();
        getActivitiesFromDb();
        getSeasonsFromDb();
        setComboBox();
        this.lvActivities.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.lvActivities.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                currentActivitie = (Activitie)newValue;
                showParticipants();
            }
        });
    }

    private void setComboBox() {
        ObservableList<Season> options =
                FXCollections.observableArrayList(seasonList);
        ComboBox comboBox = new ComboBox(options);
    }

    private void showParticipants(){
        private List<Person> personsInActivitie = new ArrayList<Person>();
        personList.forEach( e -> {
            if(e.idActivitie == currentActivitie.id){
                personsInActivitie.add(e);
            }
        });
    }

    private void getSeasonsFromDb(){

    }

    private void getActivitiesFromDb(){

    }

    private void getPersonsFromDb(){

    }


}
