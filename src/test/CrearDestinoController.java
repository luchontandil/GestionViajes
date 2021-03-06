/*
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
*/
package test;

import funciones.fn;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Database;

/**
 * FXML Controller class
 * 
 * @author Luciano
 */
public class CrearDestinoController implements Initializable{
    @FXML
    private TextField direccion;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField nDireccion;
    @FXML
    private Button btnAceptar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        // TODO
    }
    
    /**
    * Crea el destino en la bd si los datos son correctos.
    */
    @FXML
    private void crearDestino(ActionEvent event){
        
        if(fn.checkINT(nDireccion.getText())){
            if(Database.consulta("SELECT ciudad,direccion,nDireccion FROM lugar WHERE ciudad=? AND direccion=? AND nDireccion=?", new Object[]{ciudad.getText(),direccion.getText(),nDireccion.getText()}).get(0)==null){
                Database.insert("INSERT INTO lugar (ciudad,direccion,nDireccion) VALUES(?,?,?)", new Object[]{ciudad.getText(),direccion.getText(),nDireccion.getText()});
                
                Stage stage = (Stage) btnAceptar.getScene().getWindow();
                btnAceptar.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ya existe un lugar con esos datos");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "El numero de direccion solo acepta enteros");
            alert.showAndWait();
        }
    }
    
}
