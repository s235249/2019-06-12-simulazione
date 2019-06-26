/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.model.CondimentCalFood;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="boxIngrediente"
    private ComboBox<Condiment> boxIngrediente; // Value injected by FXMLLoader

    @FXML // fx:id="btnDietaEquilibrata"
    private Button btnDietaEquilibrata; // Value injected by FXMLLoader
    
    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaDieta(ActionEvent event) {
    	Condiment ingredientePartenza = boxIngrediente.getValue();
    	if(ingredientePartenza == null) {
    		txtResult.appendText("Seleziona un ingrediente\n");
    		return;
    	}
    	List<Condiment> dieta = model.calcolaDieta(ingredientePartenza);
    	for(Condiment c : dieta) {
    		txtResult.appendText(c.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	try {
			double maxCalorie = Double.parseDouble(txtCalorie.getText());
			model.createGraph(maxCalorie);
			txtResult.appendText("Grafo creato\n");
			txtResult.appendText("Vertici "+model.getNumeroVertici()+"\n");
			txtResult.appendText("Archi "+model.getNumeroArchi()+"\n");
			txtResult.appendText("\n\n\n");
			for(CondimentCalFood ccf : model.getCalFood()) {
				txtResult.appendText(ccf.toString());
			}
			boxIngrediente.getItems().addAll(model.listCondimentUnderCalories(maxCalorie));
		} catch (NumberFormatException e) {
			txtResult.appendText("Inserire un numero\n");
			// e.printStackTrace();
			return;
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
