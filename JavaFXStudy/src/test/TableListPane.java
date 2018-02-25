package test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableListPane extends TableView<Person> {
	
	private ObservableList<Person> personData = FXCollections.observableArrayList(new Person("Tsuyoshi", 38, "Nara"), new Person("Koichi", 39, "Stage"));
	
	public TableListPane(){
		this.setId("table-list-pane");
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		createColumns();
		//this.setItems(personData);
		this.setPlaceholder(new Label(""));
	}
	
	@SuppressWarnings("unchecked")
	public void createColumns(){
		TableColumn <Person,String> nameCol = new TableColumn<>("Name");
		nameCol.setId("name-col");
		nameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
		TableColumn <Person,Integer>ageCol = new TableColumn<>("Age");
		ageCol.setId("age-col");
		ageCol.setCellValueFactory(new PropertyValueFactory<Person,Integer>("age"));
		
		TableColumn <Person,String> addressCol = new TableColumn<>("Address");
		addressCol.setId("address-col");
		addressCol.setCellValueFactory(new PropertyValueFactory<Person,String>("address"));

		getColumns().addAll(nameCol, ageCol, addressCol);
	}
}
