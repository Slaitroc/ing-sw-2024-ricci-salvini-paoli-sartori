package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.gui.ResolutionSizes;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import javafx.util.Pair;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.*;

public class InGameController extends ViewController {

    @FXML
    public StackPane motherPane;

    //Resources Count Labels____________________________________________________________________________________________
    @FXML
    public Label mushCount1;
    @FXML
    public Label mushCount2;
    @FXML
    public Label mushCount3;
    @FXML
    public Label mushCount4;
    @FXML
    public Label animalCount1;
    @FXML
    public Label animalCount2;
    @FXML
    public Label animalCount3;
    @FXML
    public Label animalCount4;
    @FXML
    public Label insectCount1;
    @FXML
    public Label insectCount2;
    @FXML
    public Label insectCount3;
    @FXML
    public Label insectCount4;
    @FXML
    public Label plantCount1;
    @FXML
    public Label plantCount2;
    @FXML
    public Label plantCount3;
    @FXML
    public Label plantCount4;
    @FXML
    public Label inkCount1;
    @FXML
    public Label inkCount2;
    @FXML
    public Label inkCount3;
    @FXML
    public Label inkCount4;
    @FXML
    public Label quillCount1;
    @FXML
    public Label quillCount2;
    @FXML
    public Label quillCount3;
    @FXML
    public Label quillCount4;
    @FXML
    public Label scrollCount1;
    @FXML
    public Label scrollCount2;
    @FXML
    public Label scrollCount3;
    @FXML
    public Label scrollCount4;
    @FXML
    public Label player1Points;
    @FXML
    public Label player2Points;
    @FXML
    public Label player3Points;
    @FXML
    public Label player4Points;
    //Map of resources labels for each player
    private final Map<Integer, List<Label>> resourceLabels = new HashMap<>();

    //Resources VBoxes__________________________________________________________________________________________________
    @FXML
    public VBox player1Resources;
    @FXML
    public VBox player2Resources;
    @FXML
    public VBox player3Resources;
    @FXML
    public VBox player4Resources;

    //STATUS IMAGES_____________________________________________________________________________________________________
    @FXML
    public ImageView statusP2;
    @FXML
    public ImageView statusP3;
    @FXML
    public ImageView statusP4;
    private final List<ImageView> statusPanes = new ArrayList<>();

    //Player Names Labels_______________________________________________________________________________________________
    @FXML
    public Label player4Name;
    @FXML
    public Label player3Name;
    @FXML
    public Label player2Name;
    @FXML
    public Label player1Name;

    //Player turn Icons_________________________________________________________________________________________________
    @FXML
    public ImageView playingPlayer1Icon;
    @FXML
    public ImageView playingPlayer2Icon;
    @FXML
    public ImageView playingPlayer3Icon;
    @FXML
    public ImageView playingPlayer4Icon;

    //GridPanes_________________________________________________________________________________________________________
    @FXML
    public GridPane player1PlayAreaGrid;
    @FXML
    public GridPane player2PlayAreaGrid;
    @FXML
    public GridPane player3PlayAreaGrid;
    @FXML
    public GridPane player4PlayAreaGrid;

    //Chat Elements_____________________________________________________________________________________________________
    @FXML
    public MFXTextField textField;
    @FXML
    public TextFlow chatField;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox chatPopUp;
    @FXML
    public ImageView chatButtonImage;

    //Settings Elements_________________________________________________________________________________________________
    @FXML
    public StackPane menuPane;
    @FXML
    public VBox settingsVBox;
    @FXML
    public VBox controlsVBox;
    @FXML
    public ListView<String> controls;

    //ImageViews Cards__________________________________________________________________________________________________
    @FXML
    public ImageView handCard1;
    @FXML
    public ImageView handCard2;
    @FXML
    public ImageView handCard3;
    @FXML
    public ImageView player2HandCard1;
    @FXML
    public ImageView player2HandCard2;
    @FXML
    public ImageView player2HandCard3;
    @FXML
    public ImageView player3HandCard1;
    @FXML
    public ImageView player3HandCard2;
    @FXML
    public ImageView player3HandCard3;
    @FXML
    public ImageView player4HandCard1;
    @FXML
    public ImageView player4HandCard2;
    @FXML
    public ImageView player4HandCard3;
    @FXML
    public ImageView noirPion1;
    @FXML
    public ImageView noirPion2;
    @FXML
    public ImageView noirPion3;
    @FXML
    public ImageView noirPion4;
    @FXML
    public ImageView colorPion4;
    @FXML
    public ImageView colorPion3;
    @FXML
    public ImageView colorPion2;
    @FXML
    public ImageView colorPion1;
    private Image other1SecretObjectiveImage;
    private Image other2SecretObjectiveImage;
    private Image other3SecretObjectiveImage;

    //Main cards ImageViews_____________________________________________________________________________________________
    //List of hand cards for each player
    private List<ImageView> handCards;
    @FXML
    public ImageView deckGold;
    @FXML
    public ImageView deckGoldCard1;
    @FXML
    public ImageView deckGoldCard2;
    @FXML
    public ImageView deckResource;
    @FXML
    public ImageView deckResourceCard1;
    @FXML
    public ImageView deckResourceCard2;
    @FXML
    public ImageView commonObjCard1;
    @FXML
    public ImageView commonObjCard2;
    @FXML
    public ImageView starterCard;
    @FXML
    public ImageView secretObjective;
    @FXML
    public ImageView secretObj1;
    @FXML
    public ImageView secretObj2;

    //Tabs to Hide or Rename____________________________________________________________________________________________
    @FXML
    public Tab tab2;
    @FXML
    public Tab tab3;
    @FXML
    public Tab tab4;

    //EndGame Panes_____________________________________________________________________________________________________
    public StackPane youWon;
    public StackPane otherWinner;
    public Label winnerLabel;

    @FXML
    public Button playStarterButton;
    @FXML
    public VBox initialChoice;

    private final List<String> otherPlayers = new ArrayList<>();

    private ResolutionSizes size;

    //temporary fix to scores been updated in weird places
    private final List<Integer> tmpScores = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
    private boolean shortcutDetected = false;
    private boolean firstPlayer = true;
    private boolean timerAlreadyStarted = false;
    private int seconds = 0;
    private boolean ping = true;
    @FXML
    public Label pingText;
    @FXML
    public TextField infoText;
    private Timeline timeline;


    /* NOTE
     * The four integers represent the minimum and maximum x and y coordinates of the playAreaGrid
     * gridDimensions.get(grid).get(0) = minX
     * gridDimensions.get(grid).get(1) = minY
     * gridDimensions.get(grid).get(2) = maxX
     * gridDimensions.get(grid).get(3) = maxY
     */
    private final LinkedHashMap<GridPane, ArrayList<Integer>> gridDimensions = new LinkedHashMap<>();

    /*
     * NOTE
     * We need a map of cells for each gridPane, otherwise all the cells will be overwritten with
     * those of the last gridPane used. For code clarity, it is used a list of maps, one fore each player.
     * */
    private final Map<Pair<Integer, Integer>, Cell> cells1 = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Cell> cells2 = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Cell> cells3 = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Cell> cells4 = new HashMap<>();
    private final List<Map<Pair<Integer, Integer>, Cell>> cellList = new ArrayList<>(Arrays.asList(cells1, cells2, cells3, cells4));

    Rectangle2D cardViewportSD = new Rectangle2D(69, 79, 894, 600);

    @Override
    protected void initialize() {
    }

    @Override
    public void setUp() {

        size = ResolutionSizes.HD; //needed to be set at the start or cards will not know what size to be initialized
        secretObjective.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/CardsImages/ObjectiveBack/1709658535735-b4f02509-be6b-4cd6-acbe-7a6b989ab079_102.jpg"))));
        handCards = new ArrayList<>(Arrays.asList(
                handCard1, handCard2, handCard3,
                player2HandCard1, player2HandCard2, player2HandCard3,
                player3HandCard1, player3HandCard2, player3HandCard3,
                player4HandCard1, player4HandCard2, player4HandCard3
        ));

        // Set the clip of the ImageViews to a rectangle with rounded corners
        for (ImageView handCard : handCards) {
            setClipToImageView(handCard);
        }

        controls.getItems().add("Flip Card:     \t\tRight Click");
        controls.getItems().add("Draw Card:     \t\tLeft Click");
        controls.getItems().add("Place Card:    \t\tHold Left Click");
        controls.getItems().add("Autofill chat: \t\tTab in chat");
        controls.getItems().add("Show Menu:     \tctrl + m / ctrl + s");
        controls.getItems().add("Show Controls: \tctrl + c");
        controls.getItems().add("Quit:          \t\tctrl + q");
        controls.getItems().add("Fullscreen:    \t\tctrl + f");

        //Initializes the tabs titles and disable the not used ones
        List<Tab> tabs = new ArrayList<>(Arrays.asList(tab2, tab3, tab4));
        int k = 0;
        for (String player : app.getPlayerList().keySet()) {
            System.out.println("player " + player);
            if (!player.equals(app.getUsername())) {
                otherPlayers.add(player);
                //System.out.println("Adding other player: " + player);
                tabs.get(k).setText(player);
                k++;
            }
        }
        for (; k <= 2; k++) {
            //System.out.println("Disabling tab " + k);
            tabs.get(k).setDisable(true);
            tabs.get(k).setText("Locked");
        }

        //Initializes the gridDimensions maps for all the playAreaGrids that need to be initialized to minX=0, minY=0, maxX=0, maxY=0
        //Also hides the resources of the players not in game
        gridDimensions.put(player1PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        gridDimensions.put(player2PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        statusPanes.add(statusP2);
        player1Name.setText(app.getUsername());
        player2Name.setText(otherPlayers.getFirst());
        if (app.getNumberOfPlayers() == 2) {
            player3Resources.setVisible(false);
            player4Resources.setVisible(false);
        } else if (app.getNumberOfPlayers() == 3) {
            player4Resources.setVisible(false);
            player3Name.setText(otherPlayers.get(1));
            statusPanes.add(statusP3);
            gridDimensions.put(player3PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        } else if (app.getNumberOfPlayers() == 4) {
            player3Name.setText(otherPlayers.get(1));
            player4Name.setText(otherPlayers.get(2));
            statusPanes.add(statusP3);
            statusPanes.add(statusP4);
            gridDimensions.put(player3PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
            gridDimensions.put(player4PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        }

        //Initializes the first 3 columns and rows for all the playAreaGrids
        k = 0;
        for (GridPane grid : gridDimensions.keySet()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Cell cell = new Cell(grid, i, j);
                    cellList.get(k).put(new Pair<>(i, j), cell);
                }
            }
            k++;
        }
        player1PlayAreaGrid.setVisible(false);

        //Initializes the resourceLabels map
        resourceLabels.put(1, Arrays.asList(mushCount1, animalCount1, insectCount1, plantCount1, inkCount1, quillCount1, scrollCount1));
        resourceLabels.put(2, Arrays.asList(mushCount2, animalCount2, insectCount2, plantCount2, inkCount2, quillCount2, scrollCount2));
        resourceLabels.put(3, Arrays.asList(mushCount3, animalCount3, insectCount3, plantCount3, inkCount3, quillCount3, scrollCount3));
        resourceLabels.put(4, Arrays.asList(mushCount4, animalCount4, insectCount4, plantCount4, inkCount4, quillCount4, scrollCount4));

        //Set 3 listeners for drag detection on the hand cards
        // (outer stackPane is momentarily fixed inside this method to avoid a resize bug, I am warning you)
        addHandCardDragListener(handCard1);
        addHandCardDragListener(handCard2);
        addHandCardDragListener(handCard3);

        assignPion();
        textField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleChatKeyPressed);
        motherPane.addEventFilter(KeyEvent.KEY_PRESSED, this::handleShortcuts);
        changeResolution();
    }

    //FROM show_Update:_________________________________________________________________________________________________

    @Override
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        if(firstCardDeck != null){
            setCardImage(firstCardDeck, deckGold);
        }else {
            deckGold.setImage(new Image("/src/main/resources/it/polimi/ingsw/gc31/Images/CardsImages/deckEmpty.jpg"));
        }
        setCardImage(card1, deckGoldCard1);
        setCardImage(card2, deckGoldCard2);
    }

    @Override
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        if(firstCardDeck != null){
            setCardImage(firstCardDeck, deckResource);
        }else {
            deckResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/CardsImages/deckEmpty.jpg"))));
        }
        setCardImage(card1, deckResourceCard1);
        setCardImage(card2, deckResourceCard2);
    }

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand) {
        if (username.equals(app.getUsername())) {
            updateHand(0, hand);
        } else if (username.equals(otherPlayers.getFirst())) {
            hideHand(hand);
            updateHand(1, hand);
        } else if (username.equals(otherPlayers.get(1))) {
            hideHand(hand);
            updateHand(2, hand);
        } else if (username.equals(otherPlayers.get(2))) {
            hideHand(hand);
            updateHand(3, hand);
        }
    }

    @Override
    public void show_scorePlayer(LinkedHashMap<String, Pair<Integer, Boolean>> scores) {
        for (Map.Entry<String, Pair<Integer, Boolean>> player : scores.entrySet()) {
            if (player.getKey().equals(app.getUsername())) {
                tmpScores.set(0, Integer.parseInt(player1Points.getText()));
                player1Points.setText(player.getValue().getKey().toString());
                //setVisible with the boolean coming from scores
                playingPlayer1Icon.setVisible(player.getValue().getValue());
            } else if (player.getKey().equals(otherPlayers.getFirst())) {
                tmpScores.set(1, Integer.parseInt(player2Points.getText()));
                player2Points.setText(player.getValue().getKey().toString());
                playingPlayer2Icon.setVisible(player.getValue().getValue());
            } else if (player.getKey().equals(otherPlayers.get(1))) {
                tmpScores.set(2, Integer.parseInt(player3Points.getText()));
                player3Points.setText(player.getValue().getKey().toString());
                playingPlayer3Icon.setVisible(player.getValue().getValue());
            } else if (player.getKey().equals(otherPlayers.get(2))) {
                tmpScores.set(3, Integer.parseInt(player4Points.getText()));
                player4Points.setText(player.getValue().getKey().toString());
                playingPlayer4Icon.setVisible(player.getValue().getValue());
            }
        }
    }

    @Override
    public void show_starterCard(String username, PlayableCard starterCard) {
        if (username.equals(app.getUsername())) {
            setCardImage(starterCard, this.starterCard);
        }
    }

    @Override
    public void show_playArea(String username, Map<Point, PlayableCard> playArea, Map<Resources, Integer> achievedResources) {
        //System.out.println("Currently otherPlayers are: " + otherPlayers);
        if (username.equals(app.getUsername())) {
            //System.out.println("Updating playArea for player1")
            //System.out.println("My gridPane is: " + player1PlayAreaGrid);
            updateGrid(player1PlayAreaGrid, cells1, playArea);
            updateResources(1, achievedResources);
        }
        if (username.equals(otherPlayers.getFirst())) {
            //System.out.println("Updating playArea for player2")
            //System.out.println("My gridPane is: " + player2PlayAreaGrid);
            updateGrid(player2PlayAreaGrid, cells2, playArea);
            updateResources(2, achievedResources);
        }
        if (app.getNumberOfPlayers() >= 3) {
            if (username.equals(otherPlayers.get(1))) {
                //System.out.println("Updating playArea for player3")
                //System.out.println("My gridPane is: " + player3PlayAreaGrid);
                updateGrid(player3PlayAreaGrid, cells3, playArea);
                updateResources(3, achievedResources);
            }
            if (app.getNumberOfPlayers() == 4) {
                if (username.equals(otherPlayers.get(2))) {
                    //System.out.println("Updating playArea for player4")
                    //System.out.println("My gridPane is: " + player4PlayAreaGrid);
                    updateGrid(player4PlayAreaGrid, cells4, playArea);
                    updateResources(4, achievedResources);
                }
            }
        }
    }

    @Override
    public void show_chooseObjectiveCard(String username, ObjectiveCard secretObjectiveCard1, ObjectiveCard secretObjectiveCard2) {
        if (username.equals(app.getUsername())) {
            setCardImage(secretObjectiveCard1, secretObj1);
            setCardImage(secretObjectiveCard2, secretObj2);
        }
    }

    @Override
    public void show_commonObjectives(ObjectiveCard commonObjectiveCard1, ObjectiveCard commonObjectiveCard2) {
        setCardImage(commonObjectiveCard1, commonObjCard1);
        setCardImage(commonObjectiveCard2, commonObjCard2);
    }

    @Override
    public void show_objectiveCard(String username, ObjectiveCard objectiveCard) {
        if (username.equals(app.getUsername())) {
            playStarterButton.setVisible(true);
            playStarterButton.setMouseTransparent(false);
            secretObj1.setManaged(false);
            secretObj1.setVisible(false);
            secretObj2.setManaged(false);
            secretObj2.setVisible(false);
            infoText.setText("Choose starter card side");
            setCardImage(objectiveCard, secretObjective);
        }
        else if (username.equals(otherPlayers.getFirst()))
            other1SecretObjectiveImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(objectiveCard.getImage())));
        else if (username.equals(otherPlayers.get(1)))
            other2SecretObjectiveImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(objectiveCard.getImage())));
        else if (username.equals(otherPlayers.get(2)))
            other3SecretObjectiveImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(objectiveCard.getImage())));

    }

    @Override
    public void playerStateInfo(String username, String info) {
        //System.out.println("Hello, I am player " + app.getUsername() + " and I received the message that " + username + " is in state " + info);
        if (username.equals(app.getUsername())) {
            if (timeline != null){
                timeline.stop();
                seconds = 0;
            }
            infoText.setText(info);
            if (firstPlayer) {
                noirPion1.setVisible(true);
                firstPlayer = false;
            }
        } else if (username.equals(otherPlayers.getFirst())) {
            if (firstPlayer) {
                noirPion2.setVisible(true);
                firstPlayer = false;
            }
        } else if (username.equals(otherPlayers.get(1))) {
            //playingPlayer3Icon.setVisible(info.equals("notplaced") || info.equals("placed"));
            if (firstPlayer) {
                noirPion3.setVisible(true);
                firstPlayer = false;
            }
        } else if (username.equals(otherPlayers.get(2))) {
            //playingPlayer4Icon.setVisible(info.equals("notplaced") || info.equals("placed"));
            if (firstPlayer) {
                noirPion4.setVisible(true);
                firstPlayer = false;
            }
        }
    }

    @Override
    public void updateChat(String username, String message) {
        Text usernameText = new Text(username + ": ");
        populateChat(username, message, usernameText);
    }

    @Override
    public void updateChat(String fromUsername, String toUsername, String message) {
        Text usernameText;
        String colorUsername;
        //If my username is the toUsername, → fromUsername is trying to send me a message then I will print [From: X]: message
        if (toUsername.equals(app.getUsername())) {
            colorUsername = toUsername;
            usernameText = new Text("[From: " + fromUsername + "]: ");
        }
        //If my username is the fromUsername, → I am trying to send me a message to toUsername then I will print [To: Y]: message
        else if (fromUsername.equals(app.getUsername())) {
            colorUsername = fromUsername;
            usernameText = new Text("[To: " + toUsername + "]: ");
        } else return;

        populateChat(colorUsername, message, usernameText);
    }

    @Override
    public void handleInGamePlayers(LinkedHashMap<String, Boolean> players) {
        int i = 0;
        for (String player : otherPlayers) {
            if (players.containsKey(player)) statusPanes.get(i).setVisible(false);
            else {
                statusPanes.get(i).setVisible(true);
                statusPanes.get(i).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/Misc/quitPlayer.jpg"))));
            }
            i++;
        }
    }

    @Override
    public void showWinner(String username, Map<String, Integer> playersScore) {
        if (username.equals(app.getUsername())) {
            showPane(youWon);
            youWon.setMouseTransparent(true);
        } else {
            winnerLabel.setText(username + "won");
            showPane(otherWinner);
            otherWinner.setMouseTransparent(true);
        }
        for (Map.Entry<String, Integer> player : playersScore.entrySet()) {
            if (player.getKey().equals(app.getUsername())) {
                player1Points.setText(tmpScores.getFirst() + " + " + (player.getValue() - tmpScores.getFirst()) + " = " + player.getValue());
            }
            else if (player.getKey().equals(otherPlayers.getFirst())){
                player2Points.setText(tmpScores.get(1) + " + " + (player.getValue() - tmpScores.get(1)) + " = " + player.getValue());
            }
            else if (player.getKey().equals(otherPlayers.get(1))){
                player3Points.setText(tmpScores.get(2) + " + " + (player.getValue() - tmpScores.get(2)) + " = " + player.getValue());
            }
            else if (player.getKey().equals(otherPlayers.get(2))){
                player4Points.setText(tmpScores.get(3) + " + " + (player.getValue() - tmpScores.get(3)) + " = " + player.getValue());
            }
        }
        handCards.get(5).setImage(other1SecretObjectiveImage);
        handCards.get(8).setImage(other2SecretObjectiveImage);
        handCards.get(11).setImage(other3SecretObjectiveImage);
    }

    @Override
    public void showCountDown(Integer secondsLeft) {
        if (!timerAlreadyStarted) {
            timerAlreadyStarted = true;
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                seconds++;
                infoText.setText("Game Paused: " + formatTime(secondsLeft - seconds));
                if (secondsLeft == seconds) {
                    timeline.stop();
                    seconds = 0;
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    @Override
    public void playerRejoined() {
        timerAlreadyStarted = false;
        seconds = 0;
        initialChoice.setVisible(false);
        initialChoice.setManaged(false);
        initialChoice.setMouseTransparent(true);
        player1PlayAreaGrid.setVisible(true);
    }

    @Override
    public void showPing() {
        if(ping){
            pingText.setText("❤");
            ping=false;
        } else {
            pingText.setText("💓");
            ping=true;
        }
    }

    @Override
    public void setMessage(String message) {
        infoText.setText(message);
    }

    //MOUSE COMMANDS:___________________________________________________________________________________________________

    /**
     * Handles changing the side of a card when a secondary mouse button click is detected.
     *
     * @param mouseEvent The mouse event triggering the side change.
     */
    public void changeCardSide(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            //System.out.println("changeSide called");
            ImageView card = (ImageView) mouseEvent.getSource();

            try {
                if (card == handCard1) {
                    //System.out.println("Called on Card1");
                    client.selectCard(0);
                    client.changeSide();
                } else if (card == handCard2) {
                    //System.out.println("Called on Card2");
                    client.selectCard(1);
                    client.changeSide();
                } else if (card == handCard3) {
                    //System.out.println("Called on Card3");
                    client.selectCard(2);
                    client.changeSide();
                } else if (card == starterCard) {
                    //System.out.println("Called on Starter Card");
                    client.changeStarterSide();
                }
            } catch (RemoteException e) {
                show_ServerCrashWarning(e.toString());
                e.getStackTrace();
            }
        }
    }

    /**
     * Handles drawing a card when a primary mouse button click is detected.
     *
     * @param mouseEvent The mouse event triggering the card draw.
     */
    public void drawCard(MouseEvent mouseEvent) {
        //System.out.println("drawCard called");
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            //System.out.println("Primary Button Down");
            ImageView card = (ImageView) mouseEvent.getSource();
            try {
                if (card == deckResource) {
                    //System.out.println("Called on deckResource");
                    client.drawResource(0);
                } else if (card == deckResourceCard1) {
                    //System.out.println("Called on deckResourceCard1");
                    client.drawResource(1);
                } else if (card == deckResourceCard2) {
                    //System.out.println("Called on deckResourceCard2");
                    client.drawResource(2);
                } else if (card == deckGold) {
                    //System.out.println("Called on deckGold");
                    client.drawGold(0);
                } else if (card == deckGoldCard1) {
                    //System.out.println("Called on deckGoldCard1");
                    client.drawGold(1);
                } else if (card == deckGoldCard2) {
                    //System.out.println("Called on deckGoldCard2");
                    client.drawGold(2);
                }
            } catch (RemoteException e) {
                show_ServerCrashWarning(e.toString());
                e.getStackTrace();
            }
        }
    }

    /**
     * Handles choosing an objective when a mouse click is detected.
     *
     * @param mouseEvent The mouse event triggering the objective choice.
     */
    public void chooseObjective(MouseEvent mouseEvent) {
        ImageView obj = (ImageView) mouseEvent.getSource();

        try {
            if (obj == secretObj1) {
                client.chooseSecretObjective1();
            } else {
                client.chooseSecretObjective2();
            }
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }

    /**
     * Plays the starter card than hides the initial choice VBox and shows the player1PlayAreaGrid.
     */
    public void playStarter() {
        try {
            client.playStarter();
            initialChoice.setVisible(false);
            initialChoice.setManaged(false);
            initialChoice.setMouseTransparent(true);
            player1PlayAreaGrid.setVisible(true);
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }

    /**
     * Toggles the visibility of the chat popup VBox by setting it to visible or invisible.
     * Also resets the chat button image if there are unread messages.
     */
    public void showHideChat() {
        //System.out.println("ChatPopUp is visible: " + chatPopUp.isVisible());
        if (chatPopUp.isVisible()) {
            //System.out.println("Trying to hide chat");
            chatPopUp.setVisible(false);
            chatPopUp.setMouseTransparent(true);
        } else {
            //System.out.println("Trying to show chat");
            chatPopUp.setVisible(true);
            chatPopUp.setMouseTransparent(false);
            chatButtonImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/AppIcons/iconMessage.png"))));
            chatField.requestFocus();
        }
    }

    /**
     * Handles sending a text message when the Enter key is pressed.
     *
     * @param event The key event triggering the text send.
     */
    @FXML
    private void handleChatKeyPressed(KeyEvent event) {
        System.out.println("handleEnterKeyPressed called with value: " + event.getCode());
        sendMessage(event, textField);
    }

    private void handleShortcuts(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) {
            shortcutDetected = !shortcutDetected;
            event.consume();
        } else if (shortcutDetected && event.getCode() == KeyCode.C) {
            showHideControls();
            shortcutDetected = false;
            event.consume();
        } else if (shortcutDetected && event.getCode() == KeyCode.M || event.getCode() == KeyCode.S) {
            showHideMenu();
            shortcutDetected = false;
            event.consume();
        } else if (shortcutDetected && event.getCode() == KeyCode.R) {
            changeResolution();
            shortcutDetected = false;
            event.consume();
        } else if (shortcutDetected && event.getCode() == KeyCode.Q) {
            quit();
            shortcutDetected = false;
            event.consume();
        } else if (shortcutDetected && event.getCode() == KeyCode.F) {
            app.setFullScreen();
            shortcutDetected = false;
            event.consume();
        }

    }

    public void showHideMenu() {
        if (controlsVBox.isVisible()) {
            hidePane(controlsVBox);
            showPane(settingsVBox);
        } else {
            showHidePane(menuPane);
            showHidePane(settingsVBox);
        }
        hidePane(controlsVBox);
    }

    public void setFullScreen() {
        app.setFullScreen();
    }

    public void showHideControls() {
        if (menuPane.isVisible()) {
            showHidePane(settingsVBox);
            showHidePane(controlsVBox);
        } else {
            showPane(menuPane);
            showPane(controlsVBox);
        }

    }

    /**
     * Change resolution to all imageViews on screen
     */
    public void changeResolution() {
        if (size == ResolutionSizes.HD) size = ResolutionSizes.SD;
        else size = ResolutionSizes.HD;

        resizeCard(handCard1);
        resizeCard(handCard2);
        resizeCard(handCard3);
        resizeCard(deckGold);
        resizeCard(deckGoldCard1);
        resizeCard(deckGoldCard2);
        resizeCard(deckResource);
        resizeCard(deckResourceCard1);
        resizeCard(deckResourceCard2);
        resizeCard(secretObj1);
        resizeCard(secretObj2);
        resizeCard(commonObjCard1);
        resizeCard(commonObjCard2);
        resizeCard(starterCard);
        resizeCard(secretObjective);
        changeGridResolution(player1PlayAreaGrid, cells1);
        changeGridResolution(player2PlayAreaGrid, cells2);
        if (app.getNumberOfPlayers() >= 3) changeGridResolution(player3PlayAreaGrid, cells3);
        if (app.getNumberOfPlayers() == 4) changeGridResolution(player4PlayAreaGrid, cells4);

    }

    /**
     * Handles the quitting process for a player, displaying a confirmation dialog and performing necessary actions if confirmed.
     * Displays a confirmation alert to the player asking if they are sure they want to quit the game.
     * If the player confirms, attempts to quit the game and navigates back to the main menu.
     * Handles potential remote exceptions by showing a server crash warning.
     */
    public void quit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitting game");
        alert.setHeaderText("Are you sure to quit the game?");
        alert.setContentText("You will not be able to rejoin the game.\nAre you sure you want to quit?");
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();

        ButtonType result = alert.getResult();
        if (result == ButtonType.OK) {
            try {
                client.quitGame();
                app.loadScene(SceneTag.MAINMENU);
                app.setDefaultWindowSize();
            } catch (RemoteException e) {
                show_ServerCrashWarning(e.toString());
                e.getStackTrace();
            }
        }
    }

    //PRIVATE METHODS:__________________________________________________________________________________________________

    /**
     * Sets the image of a card to the specified target ImageView.
     *
     * @param card   The card whose image is to be set.
     * @param target The target ImageView.
     */
    private void setCardImage(Card card, ImageView target) {
        if (card != null) {
            target.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getImage()))));
        }
    }

    /**
     * Adds a column to the specified GridPane at the specified x-coordinate and fills it with cells.
     * Notice that it need to specify what cells map to use.
     *
     * @param grid The GridPane to which the column is to be added.
     * @param x    The x-coordinate of the new column.
     */
    private void addColumn(GridPane grid, Map<Pair<Integer, Integer>, Cell> cells, int x) {
        //System.out.println("Adding column " + x + " to grid " + grid);
        for (int y = 0; y < grid.getRowCount(); y++) {
            if (cells.get(new Pair<>(x, y)) == null) {
                Cell cell = new Cell(grid, x, y);
                cells.put(new Pair<>(x, y), cell);
                //System.out.println("Adding cell on [" + x + ";" + y + "] because of addColumn(" + x + ")");
            }
        }
    }

    /**
     * Adds a row to the specified GridPane at the specified y-coordinate and fills it with cells.
     * Notice that it need to specify what cells map to use.
     *
     * @param grid The GridPane to which the row is to be added.
     * @param y    The y-coordinate of the new row.
     */
    private void addRow(GridPane grid, Map<Pair<Integer, Integer>, Cell> cells, int y) {
        //System.out.println("Adding row " + y + " to grid " + grid);
        for (int x = 0; x < grid.getColumnCount(); x++) {
            if (cells.get(new Pair<>(x, y)) == null) {
                Cell cell = new Cell(grid, x, y);
                cells.put(new Pair<>(x, y), cell);
                //System.out.println("Adding cell on [" + x + ";" + y + "] because of addRow(" + y + ")");
            }
        }
    }

    /**
     * Start with checking if the specified grid had to be resized, then set all the cells of the relative grid
     * to the invisible image and finally set the image of the cards in the playArea to the cells in the grid.
     *
     * @param grid     The GridPane to be updated.
     * @param playArea The map representing the play area.
     */
    private void updateGrid(GridPane grid, Map<Pair<Integer, Integer>, Cell> cells, Map<Point, PlayableCard> playArea) {
        //System.out.println("UPDATE_GRID CALL");
        for (Map.Entry<Point, PlayableCard> placedCard : playArea.entrySet()) {
            if (placedCard.getKey().x < gridDimensions.get(grid).getFirst()) {
                //System.out.println("Adding Column");
                gridDimensions.get(grid).set(0, placedCard.getKey().x);
                addColumn(grid, cells, gridDimensions.get(grid).get(2) - gridDimensions.get(grid).getFirst() + 2);
            }
            if (placedCard.getKey().y < gridDimensions.get(grid).get(1)) {
                //System.out.println("Adding Row");
                gridDimensions.get(grid).set(1, placedCard.getKey().y);
                addRow(grid, cells, gridDimensions.get(grid).get(3) - gridDimensions.get(grid).get(1) + 2);
            }
            if (placedCard.getKey().x > gridDimensions.get(grid).get(2)) {
                //System.out.println("Adding Column");
                gridDimensions.get(grid).set(2, placedCard.getKey().x);
                addColumn(grid, cells, gridDimensions.get(grid).get(2) - gridDimensions.get(grid).getFirst() + 2);
            }
            if (placedCard.getKey().y > gridDimensions.get(grid).get(3)) {
                //System.out.println("Adding Row");
                gridDimensions.get(grid).set(3, placedCard.getKey().y);
                addRow(grid, cells, gridDimensions.get(grid).get(3) - gridDimensions.get(grid).get(1) + 2);
            }
        }

/*        System.out.println("Initial Grid Columns: " + grid.getColumnCount());
        System.out.println("Initial Grid Rows: " + grid.getRowCount())

        System.out.println("gridDimensions.get(grid).get(2): " + gridDimensions.get(grid).get(2));
        System.out.println("gridDimensions.get(grid).get(3): " + gridDimensions.get(grid).get(3));
        System.out.println("gridDimensions.get(grid).getFirst(): " + gridDimensions.get(grid).getFirst());
        System.out.println("gridDimensions.get(grid).get(1): " + gridDimensions.get(grid).get(1));

        System.out.println("(gridDimensions.get(grid).get(2) - gridDimensions.get(grid).getFirst() + 2): " + (gridDimensions.get(grid).get(2) - gridDimensions.get(grid).getFirst() + 2));
        System.out.println("(gridDimensions.get(grid).get(3) - gridDimensions.get(grid).get(1) + 2): " + (gridDimensions.get(grid).get(3) - gridDimensions.get(grid).get(1) + 2));*/

        //int debug = 0;
        for (int x = 0; x < grid.getColumnCount(); x++) {
            for (int y = 0; y < grid.getRowCount(); y++) {
                //debug++;
                cells.get(new Pair<>(x, y)).hideImage();
                //System.out.println("Cell [" + x + ";" + y + "] set to invisible image");
            }
        }
        //System.out.println("UPDATED GRID with " + debug + " iterations");
        int newCoordinateX;
        int newCoordinateY;
        for (Map.Entry<Point, PlayableCard> placedCard : playArea.entrySet()) {
            newCoordinateX = placedCard.getKey().x - gridDimensions.get(grid).getFirst() + 1;
            newCoordinateY = gridDimensions.get(grid).get(3) - placedCard.getKey().y + 1;
            //System.out.println("Adding card that was on Point: " + placedCard.getKey() + " on cell [" + newCoordinateX + ";" + newCoordinateY + "]");
            cells.get(new Pair<>(newCoordinateX, newCoordinateY)).setCardImage(placedCard.getValue().getImage());
        }
    }

    /**
     * Updates the visual representation of a player's resources by setting the resource labels for the specified player.
     * <li>Iterates through the map of achieved resources and updates the corresponding resource label based on the resource type.</li>
     * <li>Sets the text of each resource label to reflect the current amount of the resource.</li>
     *
     * @param playerNumber      The number of the player whose resources are being updated.
     * @param achievedResources A map containing the resources and their corresponding amounts that the player has achieved.
     */
    private void updateResources(int playerNumber, Map<Resources, Integer> achievedResources) {
        for (Map.Entry<Resources, Integer> resource : achievedResources.entrySet()) {
            switch (resource.getKey()) {
                case Resources.MUSHROOM:
                    //System.out.println("Updating MUSHROOM for player " + playerNumber + " to " + resource.getValue());
                    resourceLabels.get(playerNumber).getFirst().setText(String.valueOf(resource.getValue()));
                    break;
                case Resources.ANIMAL:
                    //System.out.println("Updating ANIMAL for player " + playerNumber + " to " + resource.getValue());
                    resourceLabels.get(playerNumber).get(1).setText(String.valueOf(resource.getValue()));
                    break;
                case Resources.INSECT:
                    //System.out.println("Updating INSECT for player " + playerNumber + " to " + resource.getValue());
                    resourceLabels.get(playerNumber).get(2).setText(String.valueOf(resource.getValue()));
                    break;
                case Resources.PLANT:
                    //System.out.println("Updating PLANT for player " + playerNumber + " to " + resource.getValue());
                    resourceLabels.get(playerNumber).get(3).setText(String.valueOf(resource.getValue()));
                    break;
                case Resources.INK:
                    //System.out.println("Updating INK for player " + playerNumber + " to " + resource.getValue());
                    resourceLabels.get(playerNumber).get(4).setText(String.valueOf(resource.getValue()));
                    break;
                case Resources.FEATHER:
                    //System.out.println("Updating FEATHER for player " + playerNumber + " to " + resource.getValue());
                    resourceLabels.get(playerNumber).get(5).setText(String.valueOf(resource.getValue()));
                    break;
                case Resources.SCROLL:
                    //System.out.println("Updating SCROLL for player " + playerNumber + " to " + resource.getValue());
                    resourceLabels.get(playerNumber).get(6).setText(String.valueOf(resource.getValue()));
                    break;
            }
        }
    }

    /**
     * Updates the visual representation of a player's hand by setting the card images for the specified player
     * <li>Iterates through the list of playable cards and updates the corresponding image in the player's hand.</li>
     * <li>If the player's hand contains only two cards, it sets a placeholder image for the Third card slot.</li>
     *
     * @param playerNumber The number of the player whose hand is being updated.
     * @param hand         A list of playable cards representing the player's current hand.
     */
    private void updateHand(int playerNumber, List<PlayableCard> hand) {
        int i = 0;
        for (PlayableCard card : hand) {
            //System.out.println("Updating hand card " + i + " for player " + playerNumber + " resulting in: " + (i + (playerNumber * 3)));
            setCardImage(card, handCards.get(i + (playerNumber * 3)));
            i++;
        }
        if (hand.size() == 2) {
            //System.out.println("Hiding card " + 2 + " for player " + playerNumber + " resulting in: " + (2 + (playerNumber * 3)));
            handCards.get(2 + (playerNumber) * 3).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/CardsImages/CardPlaceHolder1.png"))));
        }
    }

    /**
     * Support method to change resolution to one single GridPane
     */
    private void changeGridResolution(GridPane grid, Map<Pair<Integer, Integer>, Cell> cells) {
        for (int x = 0; x < grid.getColumnCount(); x++) {
            for (int y = 0; y < grid.getRowCount(); y++) {
                resizeCard(cells.get(new Pair<>(x, y)));
                cells.get(new Pair<>(x, y)).setPaneResolution();
            }
        }
    }

    //private method to hide the hand of other players
    private void hideHand(List<PlayableCard> hand) {
        for (PlayableCard playableCard : hand) {
            if (playableCard.getSide()) {
                playableCard.changeSide();
            }
        }
    }

    /**
     * Set the clip of the target ImageView to a rectangle with rounded corners.
     * Basically used to round the corners of the cards.
     * Change here to modify the shape of the cards.
     */
    private void setClipToImageView(ImageView target) {
        Rectangle clip = new Rectangle(
                target.getFitWidth(), target.getFitHeight()
        );
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        target.setClip(clip);
    }

    /**
     * Adds a drag listener to the specified (ImageView) handCard.
     * When a drag is detected, the motherPane is locked to the current size and the card is dragged.
     * Then a snapshot of the card is taken and added to the dragBoard.
     * This is done because of a bug that resize the outer pane when a snapshot is taken.
     * The motherPane is then set to resizable again.
     *
     * @param card The ImageView to which the drag listener is to be added.
     */
    private void addHandCardDragListener(ImageView card) {
        card.setOnDragDetected(event -> {
            lockMotherPaneSize();
            WritableImage snapshot = card.snapshot(new SnapshotParameters(), null);
            Dragboard db = card.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(snapshot);
            db.setContent(content);
            setMotherPaneResizable();
            try {
                if (card.equals(handCard1)) client.selectCard(0);
                else if (card.equals(handCard2)) client.selectCard(1);
                else if (card.equals(handCard3)) client.selectCard(2);
            } catch (RemoteException e) {
                show_ServerCrashWarning(e.toString());
                e.getStackTrace();
            }
            event.consume();
        });
    }

    /**
     * Resizes a card's image view to fit specified dimensions and sets a clipping region to ensure the image is displayed correctly.
     * <li>Sets the fit height and width of the card image view based on predefined size values.</li>
     * <li>Applies a clipping region to the card image view to ensure the image is properly displayed within the set dimensions.</li>
     *
     * @param card The ImageView representing the card to be resized.
     */
    private void resizeCard(ImageView card) {
        //System.out.println("card.getFitWidth(): "+card.getFitWidth()+" \n card.getFitHeight(): " + card.getFitHeight());
        //System.out.println("card.getImage().getWidth(): "+card.getImage().getWidth()+" \n card.getImage().getHeight() " + card.getImage().getHeight())
        card.setFitHeight(size.getCardsHeight());
        card.setFitWidth(size.getCardWidth());
        setClipToImageView(card);
    }

    /**
     * Locks the size of the mother pane to its current dimensions, preventing it from being resized.
     * Used to prevent a little visual bug during drag and drop.
     */
    private void lockMotherPaneSize() {
        motherPane.setMinSize(motherPane.getWidth(), motherPane.getHeight());
        motherPane.setMaxSize(motherPane.getWidth(), motherPane.getHeight());
    }

    /**
     * Unlocks the size of the mother pane to its current dimensions, allowing it to be resizable.
     * Used to prevent a little visual bug during drag and drop.
     */
    private void setMotherPaneResizable() {
        motherPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        motherPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    }

    /**
     * Supportive method used to hide or show a Pane
     *
     * @param pane generic Pane to hid or show
     */
    private void showHidePane(Pane pane) {
        pane.setManaged(!pane.isManaged());
        pane.setVisible(!pane.isVisible());
        pane.setMouseTransparent(!pane.isMouseTransparent());
    }

    private void showPane(Pane pane) {
        pane.setManaged(true);
        pane.setVisible(true);
        pane.setMouseTransparent(false);
    }

    private void hidePane(Pane pane) {
        pane.setManaged(false);
        pane.setVisible(false);
        pane.setMouseTransparent(true);
    }

    /**
     * Assigns a pion image to each player based on their name and updates the corresponding image view.
     *
     * <p>This method performs the following steps:</p>
     * <ol>
     *   <li>Creates a list of pion image paths.</li>
     *   <li>Iterates through the list of players and assigns the corresponding pion image to each player's image view based on their name.</li>
     * </ol>
     */
    private void assignPion() {
        List<String> pionImages = new ArrayList<>();
        pionImages.add("/it/polimi/ingsw/gc31/Images/Board/CODEX_pion_vert.png");
        pionImages.add("/it/polimi/ingsw/gc31/Images/Board/CODEX_pion_jaune.png");
        pionImages.add("/it/polimi/ingsw/gc31/Images/Board/CODEX_pion_rouge.png");
        pionImages.add("/it/polimi/ingsw/gc31/Images/Board/CODEX_pion_bleu.png");
        int i = 0;
        for (String player : app.getPlayerList().keySet()) {
            if (player.equals(player1Name.getText()))
                colorPion1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(pionImages.get(i)))));
            else if (player.equals(player2Name.getText()))
                colorPion2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(pionImages.get(i)))));
            else if (player.equals(player3Name.getText()))
                colorPion3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(pionImages.get(i)))));
            else if (player.equals(player4Name.getText()))
                colorPion4.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(pionImages.get(i)))));

            i++;
        }
    }

    /**
     * Populates the TextFlow, adding the prefix colored in a uniquely color and the message (suffix + \n)
     * Then proceed to layout the new message among the others using the scrollPane
     * Also shows the notification icon is the chat is closed at the moment
     *
     * @param username     The username of the player used to assign the color to the message prefix
     * @param message      The message content to be added to the chat.
     * @param usernameText The prefix of the message already modified
     */
    private void populateChat(String username, String message, Text usernameText) {
        if (username.equals(app.getPlayerList().keySet().stream().toList().getFirst())) {
            usernameText.setFill(Color.GREEN);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(1))) {
            usernameText.setFill(Color.VIOLET);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(2))) {
            usernameText.setFill(Color.RED);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(3))) {
            usernameText.setFill(Color.BLUE);
        }
        Text messageText = new Text(message + "\n");
        messageText.setFill(Color.BLACK);

        chatField.getChildren().add(usernameText);
        chatField.getChildren().add(messageText);
        scrollPane.layout();
        scrollPane.setVvalue(1.0);
        if (!chatPopUp.isVisible()) {
            chatButtonImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/AppIcons/iconMessagePending.png"))));
        }
    }

    private String formatTime(int seconds) {
        int hrs = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hrs, minutes, secs);
    }

    //INNER CLASSES:____________________________________________________________________________________________________
    /*
     * Cell class that extends ImageView and represents a cell in the playAreaGrid.
     * It has a positionX and positionY that represent the x and y coordinates of the cell in the grid.
     * It has a cardImage that represents the image of the card in the cell.
     * If the cell is empty, the image is set to an ALMOST invisible image.
     * Using a 100% transparent image does not trigger his function (TO VERIFY)
     *
     */
    class Cell extends ImageView {
        private final int positionX;
        private final int positionY;
        private final StackPane pane;
        private Image cardImage;

        /**
         * Needs to be initialized with a GridPane, an x and a y coordinate.
         *
         * @param grid The GridPane to which the cell belongs.
         * @param x    The x-coordinate of the cell.
         * @param y    The y-coordinate of the cell.
         */
        public Cell(GridPane grid, int x, int y) {
            positionX = x;
            positionY = y;
            //Border border = new Border(new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

            //Create a New StackPane of the dimensions of the cell (Smaller than the card image)
            pane = new StackPane();
            pane.setMaxHeight(size.getPaneHeight());
            pane.setMaxWidth(size.getPaneWidth());
            pane.setMinHeight(size.getPaneHeight());
            pane.setMinWidth(size.getPaneWidth());
            pane.getChildren().add(this);

            //Insert invisible image to the stackPane (Set the image dimensions bigger than the stackPane, set his viewPort, and his clip (for round corners))
            cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/CardsImages/CardPlaceHolder1.png")));
            this.setImage(cardImage);
            this.setPreserveRatio(true);
            this.setViewport(cardViewportSD);
            this.setFitWidth(size.getCardWidth()); // set the card width
            this.setFitHeight(size.getCardsHeight()); // Set the card height
            setClipToImageView(this); // Round image boarder

            //If the cell belongs to player1, it is set to accept drag and drop events
            if (grid.equals(player1PlayAreaGrid)) {
                setOnDragOver(event -> {
                    if (event.getGestureSource() != this && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                setOnDragDropped(event -> {
                    //System.out.println("Mouse Drag Dropped on cell " + positionX + " " + positionY)
                    //System.out.println("Playing card in " + (positionX + gridDimensions.get(grid).getFirst() - 1) + " " + (gridDimensions.get(grid).get(3) - positionY + 1));
                    if (event.getDragboard().hasImage()) {
                        try {
                            client.play(new Point(positionX + gridDimensions.get(grid).getFirst() - 1, gridDimensions.get(grid).get(3) - positionY + 1));
                        } catch (RemoteException e) {
                            show_ServerCrashWarning(e.toString());
                            e.getStackTrace();
                        }
                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });
            }

            grid.add(pane, x, y);
        }

        public void setCardImage(String cardImageUrl) {
            pane.toFront();
            this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cardImageUrl)));
            this.setImage(cardImage);
        }

        public void setPaneResolution() {
            pane.setMaxHeight(size.getPaneHeight());
            pane.setMaxWidth(size.getPaneWidth());
            pane.setMinHeight(size.getPaneHeight());
            pane.setMinWidth(size.getPaneWidth());
        }

        public void hideImage() {
            pane.toBack();
            this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/CardsImages/CardPlaceHolder1.png")));
            this.setImage(cardImage);
        }
    }
}

