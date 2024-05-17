package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RuleBookController extends ViewController{

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    @FXML
    private ImageView imageView5;

    @FXML
    private ImageView imageView6;

    @FXML
    private MFXButton nextButton;

    @FXML
    private MFXButton prevButton;

    @FXML
    private MFXButton backButton;

    private int currentPage = 1;

    @Override
    @FXML
    public void initialize() {
    }

    @FXML
    public void nextImage() {
        switch (currentPage) {
            case 1:
                imageView1.setVisible(false);
                imageView2.setVisible(true);
                currentPage = 2;
                break;
            case 2:
                imageView2.setVisible(false);
                imageView3.setVisible(true);
                currentPage = 3;
                break;
            case 3:
                imageView3.setVisible(false);
                imageView4.setVisible(true);
                currentPage = 4;
                break;
            case 4:
                imageView4.setVisible(false);
                imageView5.setVisible(true);
                currentPage = 5;
                break;
            case 5:
                imageView5.setVisible(false);
                imageView6.setVisible(true);
                currentPage = 6;
                break;
            case 6:
                break;
        }
    }

    @FXML
    public void prevImage() {
        switch (currentPage) {
            case 1:
                break;
            case 2:
                imageView2.setVisible(false);
                imageView1.setVisible(true);
                currentPage = 1;
                break;
            case 3:
                imageView3.setVisible(false);
                imageView2.setVisible(true);
                currentPage = 2;
                break;
            case 4:
                imageView4.setVisible(false);
                imageView3.setVisible(true);
                currentPage = 3;
                break;
            case 5:
                imageView5.setVisible(false);
                imageView4.setVisible(true);
                currentPage = 4;
                break;
            case 6:
                imageView6.setVisible(false);
                imageView5.setVisible(true);
                currentPage = 5;
                break;
        }
    }

    @FXML
    private void loadMainMenuScene() {
        app.setDefaultSize();
        app.loadScene(SceneTag.MAINMENU);
    }

}
