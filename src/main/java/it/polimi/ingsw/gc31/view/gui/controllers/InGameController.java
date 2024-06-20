package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.gui.ResolutionSizes;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    List<VBox> resourceWindows = new ArrayList<>();

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

    @FXML
    public Button playStarterButton;

    public VBox initialChoise;

    private final List<String> otherPlayers = new ArrayList<>();

    private ResolutionSizes size;


    /* NOTE
     * The four integers represent the minimum and maximum x and y coordinates of the playAreaGrid
     * gridDimensions.get(grid).get(0) = minX
     * gridDimensions.get(grid).get(1) = minY
     * gridDimensions.get(grid).get(2) = maxX
     * gridDimensions.get(grid).get(3) = gridDimensions.get(grid).get(3)
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

        size = ResolutionSizes.HD;
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

        controls.getItems().add("Flip Card:   \tRight Click");
        controls.getItems().add("Draw Card:   \tLeft Click");
        controls.getItems().add("Place Card:  \tHold Left Click");

        //Initializes the tabs titles and disable the not used ones
        List<Tab> tabs = new ArrayList<>(Arrays.asList(tab2, tab3, tab4));
        int k = 0;
        for (String player : app.getPlayerList().keySet()) {
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
        resourceWindows.add(player1Resources);
        resourceWindows.add(player2Resources);
        player1Name.setText(app.getUsername());
        player2Name.setText(otherPlayers.getFirst());
        if (app.getNumberOfPlayers() == 2) {
            player3Resources.setVisible(false);
            player4Resources.setVisible(false);
        } else if (app.getNumberOfPlayers() == 3) {
            player4Resources.setVisible(false);
            player3Name.setText(otherPlayers.get(1));
            resourceWindows.add(player3Resources);
            gridDimensions.put(player3PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        } else if (app.getNumberOfPlayers() == 4) {
            resourceWindows.add(player3Resources);
            resourceWindows.add(player4Resources);
            player3Name.setText(otherPlayers.get(1));
            player3Name.setText(otherPlayers.get(2));
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
        // (outer stackPane is momentarily fixed inside this method to avoid a resize bug, I'm warning you)
        addHandCardDragListener(handCard1);
        addHandCardDragListener(handCard2);
        addHandCardDragListener(handCard3);

        changeResolution();
    }


    //FROM show_Update:_________________________________________________________________________________________________

    @Override
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        setCardImage(firstCardDeck, deckGold);
        setCardImage(card1, deckGoldCard1);
        setCardImage(card2, deckGoldCard2);
    }

    @Override
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        setCardImage(firstCardDeck, deckResource);
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
    public void show_scorePlayer(LinkedHashMap<String, Integer> scores) {
        for (Map.Entry<String, Integer> player : scores.entrySet()) {
            if (player.getKey().equals(app.getUsername())) {
                player1Points.setText(player.getValue().toString());
            } else if (player.getKey().equals(otherPlayers.getFirst())) {
                player2Points.setText(player.getValue().toString());
            } else if (player.getKey().equals(otherPlayers.get(1))) {
                player3Points.setText(player.getValue().toString());
            } else if (player.getKey().equals(otherPlayers.get(2))) {
                player4Points.setText(player.getValue().toString());
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
        if (username.equals(app.getUsername())) setCardImage(objectiveCard, secretObjective);
    }

    @Override
    public void playerStateInfo(String username, String info) {
        //System.out.println("Hello, I'm player " + app.getUsername() + " and I received the message that " + username + " is in state " + info);
        if (username.equals(app.getUsername())) {
            playingPlayer1Icon.setVisible(info.equals("notplaced") || info.equals("placed"));
        } else if (username.equals(otherPlayers.getFirst())) {
            playingPlayer2Icon.setVisible(info.equals("notplaced") || info.equals("placed"));
        } else if (username.equals(otherPlayers.get(1))) {
            playingPlayer3Icon.setVisible(info.equals("notplaced") || info.equals("placed"));
        } else if (username.equals(otherPlayers.get(2))) {
            playingPlayer4Icon.setVisible(info.equals("notplaced") || info.equals("placed"));
        }
    }


    @Override
    public void updateChat(String username, String message) {
        Text usernameText = new Text(username + ": ");
        if (username.equals(app.getPlayerList().keySet().stream().toList().getFirst())) {
            usernameText.setFill(javafx.scene.paint.Color.GREEN);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(1))) {
            usernameText.setFill(javafx.scene.paint.Color.VIOLET);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(2))) {
            usernameText.setFill(javafx.scene.paint.Color.RED);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(3))) {
            usernameText.setFill(javafx.scene.paint.Color.BLUE);
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
                e.printStackTrace();
                show_ServerCrashWarning();
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
                    client.changeSide();
                } else if (card == deckResourceCard1) {
                    //System.out.println("Called on deckResourceCard1");
                    client.drawResource(1);
                    client.changeSide();
                } else if (card == deckResourceCard2) {
                    //System.out.println("Called on deckResourceCard2");
                    client.drawResource(2);
                    client.changeSide();
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
                e.printStackTrace();
                show_ServerCrashWarning();
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
            playStarterButton.setVisible(true);
            playStarterButton.setMouseTransparent(false);
            secretObj1.setManaged(false);
            secretObj1.setVisible(false);
            secretObj2.setManaged(false);
            secretObj2.setVisible(false);
        } catch (RemoteException e) {
            e.printStackTrace();
            show_ServerCrashWarning();
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
        }
    }

    /**
     * Handles sending a text message when the Enter key is pressed.
     *
     * @param event The key event triggering the text send.
     */
    @FXML
    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !textField.getText().isEmpty()) {
            sendText(textField.getText());
            textField.clear();
        }
    }

    public void showHideMenu() {
        showHidePane(menuPane);
        showHidePane(settingsVBox);
    }

    public void setFullScreen() {
        app.setFullScreen();
    }

    public void showHideControls() {
        showHidePane(settingsVBox);
        showHidePane(controlsVBox);
    }

    /**
     * Change resolution to all imageViews on screen
    */
    public void changeResolution() {
        if(size == ResolutionSizes.HD) size = ResolutionSizes.SD;
        else  size = ResolutionSizes.HD;

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
        if (app.getNumberOfPlayers()>=3) changeGridResolution(player3PlayAreaGrid, cells3);
        if (app.getNumberOfPlayers()==4) changeGridResolution(player4PlayAreaGrid, cells4);

    }

    /**
     * Support method to change resolution to one single GridPane
     * */
    void changeGridResolution(GridPane grid, Map<Pair<Integer, Integer>, Cell> cells){
        for (int x = 0; x < grid.getColumnCount(); x++) {
            for (int y = 0; y < grid.getRowCount(); y++) {
                resizeCard(cells.get(new Pair<>(x, y)));
                cells.get(new Pair<>(x, y)).setPaneResolution();
            }
        }
    }

    //PRIVATE METHODS:__________________________________________________________________________________________________

    /**
     * Plays the starter card than hides the initial choice VBox and shows the player1PlayAreaGrid.
     */
    public void playStarter() {
        try {
            client.playStarter();
            initialChoise.setVisible(false);
            initialChoise.setManaged(false);
            initialChoise.setMouseTransparent(true);
            player1PlayAreaGrid.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
            show_ServerCrashWarning();
        }
    }

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
        for (Map.Entry<Point, PlayableCard> placedCard : playArea.entrySet()) {
            if (placedCard.getKey().x <= gridDimensions.get(grid).getFirst()) {
                gridDimensions.get(grid).set(0, placedCard.getKey().x);
                addColumn(grid, cells, gridDimensions.get(grid).get(2) - gridDimensions.get(grid).getFirst() + 2);
            }
            if (placedCard.getKey().y <= gridDimensions.get(grid).get(1)) {
                gridDimensions.get(grid).set(1, placedCard.getKey().y);
                addRow(grid, cells, gridDimensions.get(grid).get(3) - gridDimensions.get(grid).get(1) + 2);
            }
            if (placedCard.getKey().x >= gridDimensions.get(grid).get(2)) {
                gridDimensions.get(grid).set(2, placedCard.getKey().x);
                addColumn(grid, cells, gridDimensions.get(grid).get(2) - gridDimensions.get(grid).getFirst() + 2);
            }
            if (placedCard.getKey().y >= gridDimensions.get(grid).get(3)) {
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

        for (int x = 0; x < grid.getColumnCount(); x++) {
            for (int y = 0; y < grid.getRowCount(); y++) {
                cells.get(new Pair<>(x, y)).hideImage();
                //System.out.println("Cell [" + x + ";" + y + "] set to invisible image");
            }
        }
        int newCoordinateX;
        int newCoordinateY;
        for (Map.Entry<Point, PlayableCard> placedCard : playArea.entrySet()) {
            newCoordinateX = placedCard.getKey().x - gridDimensions.get(grid).getFirst() + 1;
            newCoordinateY = gridDimensions.get(grid).get(3) - placedCard.getKey().y + 1;
            //System.out.println("Adding card that was on Point: " + placedCard.getKey() + " on cell [" + newCoordinateX + ";" + newCoordinateY + "]")
            //System.out.println("Card Image Path: " + placedCard.getValue().getImage());
            cells.get(new Pair<>(newCoordinateX, newCoordinateY)).setCardImage(placedCard.getValue().getImage());
        }
    }

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

    private void hideHand(List<PlayableCard> hand) {
        for (PlayableCard playableCard : hand) {
            if (playableCard.getSide()) {
                playableCard.changeSide();
            }
        }
    }

    /**
     * Sends a chat message.
     *
     * @param message The message to send.
     */
    private void sendText(String message) {
        try {
            client.sendChatMessage(client.getUsername(), message);
        } catch (RemoteException e) {
            show_ServerCrashWarning();
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
                show_ServerCrashWarning();
            }
            event.consume();
        });
    }

    private void resizeCard(ImageView card) {
        //System.out.println("card.getFitWidth(): "+card.getFitWidth()+" \n card.getFitHeight(): " + card.getFitHeight());
        //System.out.println("card.getImage().getWidth(): "+card.getImage().getWidth()+" \n card.getImage().getHeight() " + card.getImage().getHeight())
        card.setFitHeight(size.getHeight());
        card.setFitWidth(size.getWidth());
        setClipToImageView(card);
    }

    private void lockMotherPaneSize() {
        motherPane.setMinSize(motherPane.getWidth(), motherPane.getHeight());
        motherPane.setMaxSize(motherPane.getWidth(), motherPane.getHeight());
    }

    private void setMotherPaneResizable() {
        motherPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        motherPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    }

    private void showHidePane(Pane pane) {
        pane.setManaged(!pane.isManaged());
        pane.setVisible(!pane.isVisible());
        pane.setMouseTransparent(!pane.isMouseTransparent());
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
            this.setFitWidth(size.getWidth()); // set the card width
            this.setFitHeight(size.getHeight()); // Set the card height
            setClipToImageView(this);

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
                            e.printStackTrace();
                            show_ServerCrashWarning();
                        }
                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });
            }


            // Round image boarder

            grid.add(pane, x, y);
        }

        public void setCardImage(String cardImageUrl) {
            pane.toFront();
            this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cardImageUrl)));
            this.setImage(cardImage);
        }

        public void setPaneResolution(){
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

