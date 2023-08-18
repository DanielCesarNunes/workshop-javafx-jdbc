package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.utils.Constrains;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{
	
	private Department entity;
	
	@FXML
	private TextField txtId; 
	
	@FXML
	private TextField txtName; 
	
	@FXML
	private Label labelErrorName; 

	@FXML
	private Button btSave; 
	
	@FXML
	private Button btCancel; 
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("save");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("cancel");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	public void initializeNodes() {
		Constrains.setTextFieldInteger(txtId);
		Constrains.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		else {
			if(String.valueOf(entity.getId()) == "null") {
				txtId.setText("");
			}
			else {
				txtId.setText(String.valueOf(entity.getId()));
			}
			txtName.setText(entity.getName());
		}
	}

}
