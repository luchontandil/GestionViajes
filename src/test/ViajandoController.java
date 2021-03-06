/*
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
*/
package test;

import java.io.IOException;
import model.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
FXML Controller class

@author Luciano
*/
public class ViajandoController implements Initializable{
    
    private Object[] data;
    private static int segundos=0,horas=0,minutos=0;
    @FXML
    private Label cronometro;
    private static Timer timer;
    private static TimerTask timerTask;
    private static ArrayList<Object[]> gastosCombustible = new ArrayList<Object[]>();
    private static ArrayList<Object[]> peajes = new ArrayList<Object[]>();
    
    /**
    *Initializes the controller class.
    * Se inicia el cronometro.
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //empieza el cronometro
        timer = new Timer();
        
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        segundos++;
                        
                        String seg="",min="",hr="";
                        if(minutos>=60){
                            horas++;
                            minutos=0;
                        }
                        if(segundos>=60){
                            minutos++;
                            segundos=0;
                        }
                        if(segundos<=9) {seg = "0";}
                        if(minutos<=9) {min = "0";}
                        if(horas<=9) {hr = "0";}

                        cronometro.setText(hr+horas+":"+min+minutos+":"+seg+segundos);
                    }
                });
            }
        };
        
        timer.schedule(timerTask, 0, 1000);
        
    }

    /**
     * Carga los datos desde un array de objetos
     * Es llamado desde la clase de nuevo viaje
     * @param data Object[]
     */
    public void loadData(Object[] data){
        this.data = new Object[data.length+4];
        
        this.data[0] = data[0];            // tipo de viaje
        this.data[1] = null;               // duracion
        this.data[2] = null;               // duracion Total
        this.data[3] = (Integer)data[1];   // ID partida
        this.data[4] = (Integer)data[2];   // ID llegada
        this.data[5] = data[3];            // kilometros iniciales
        this.data[6] = null;               // fecha de llegada
        this.data[7] = Database.consulta("SELECT NOW() as fechasalida").get(0).get("fechasalida"); //fecha-hora de salida
        
        
        //// Muestra los datos por consola /////
        for (Object obj : this.data){
            if(obj!=null)System.out.println(obj.toString());
        }
        //////
    }
    /**
    *Muestra la ventana de carga de combustible
    */
    @FXML
    private void mostrarCargaCombustible(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cargarCombustible.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            stage.setTitle("Cargar Combustible");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            
            stage.show();
        }
        catch (Exception ex){
            ex.getMessage();
        }
    }
    /**
    * Muestra la ventana de pago de peaje.
    */
    @FXML
    private void mostrarPagarPeaje(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pagarPeaje.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            stage.setTitle("Pagar Peaje");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            
            stage.show();
        }
        catch (Exception ex){
            ex.getMessage();
        }
    }

    /**
     *  Añade al array cargasCombustibles un objeto de tipo Combustible.
     * @param array Array con los datos del combustible
     */
    public static void cargarDataCombustible(Object[] array){
        gastosCombustible.add(array);
        // {LITROS, KM, PRECIO, DATETIME};
    }

    /**
     *  Añade al array Peajes un objeto de tipo Peaje.
     * @param array Array con los datos de los peajes
     */
    public static void cargarDataPeaje(Object[] array){
        peajes.add(array);
        // {PRECIO, DATETIME}
    }

    /**
     *  Resetea el tiempo a 0.
     *  Cancela el task del timer.
     *  Setea el label del timer a 00:00:00
     */
    public void cancelTimer(){
        ViajandoController.timerTask.cancel();
        ViajandoController.timer.cancel();
        segundos=0;
        minutos=0;
        horas=0;
        cronometro.setText("00:00:00");
    }

    /**
     *  
     * @return los segundos que lleva contados el cronometro
     */
    public static int getSegundos(){
        return segundos+(minutos*60)+(horas*3600);
    }
    /**
    * Abre la ventana de Ingreso de kilometros finales
    * Si fue valido y se cierra hace la consulta, cancela el timer y cierra esta ventana
    *
    *
    */
    @FXML
    private void finalizar(ActionEvent event){
        
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("finalizandoViaje.fxml"));
                
                Parent scene = (Parent) loader.load();
                Stage st = new Stage();
               
                FinalizandoViajeController controller = loader.<FinalizandoViajeController>getController();
                controller.loadData(
                    data,               //{tipo de viaje, Duracion(ESTA NULL), Duracion total(ESTA NULL), idSalida, idLlegada, Km Iniciales, fecha de LLegada(ESTA NULL, LO SETEA CUANDO SE FINALIZA, DATETIME de la salida)}
                    gastosCombustible,  //{IdViaje, Litros, Km, Precio, DATETIME}
                    peajes              //{IdViaje, Costo, DATETIME}
                );
                st.initModality(Modality.APPLICATION_MODAL);
                st.setOnCloseRequest(WindowEvent -> {
                    System.out.println("esta finalizado? "+controller.getFinalizado());
                    if(FinalizandoViajeController.getFinalizado()){
                        Stage stage = (Stage) cronometro.getScene().getWindow();
                        cancelTimer();
                        stage.close();
                    }
                    
                });
                st.setTitle("Finalizando Viaje");
                st.setScene(new Scene(scene));
                st.setResizable(false);
                st.show();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
    }
}
