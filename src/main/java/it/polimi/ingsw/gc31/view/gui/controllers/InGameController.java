package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public HBox player3Resources;
    @FXML
    public HBox player4Resources;

    private final Map<Integer, List<Label>> resourceLabels = new HashMap<>();
    @FXML
    public GridPane player1PlayAreaGrid;
    @FXML
    public GridPane player2PlayAreaGrid;
    @FXML
    public GridPane player3PlayAreaGrid;
    @FXML
    public GridPane player4PlayAreaGrid;
    @FXML
    public MFXButton chatButton;
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

    @FXML
    public ImageView handCard1;
    @FXML
    public ImageView handCard2;
    @FXML
    public ImageView handCard3;
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

    @FXML
    public Tab tab2;
    @FXML
    public Tab tab3;
    @FXML
    public Tab tab4;


    private final List<String> otherPlayers = new ArrayList<>();

    @FXML
    public Button playStarterButton;

    public VBox initialChoise;


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
     * those of the last gridPane used. For code clarity, we have decided to use a list of maps.
     * */
    private final Map<Pair<Integer, Integer>, Cell> cells1 = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Cell> cells2 = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Cell> cells3 = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Cell> cells4 = new HashMap<>();
    private final List<Map<Pair<Integer, Integer>, Cell>> cellList = new ArrayList<>(Arrays.asList(cells1, cells2, cells3, cells4));
    Rectangle2D cardViewport = new Rectangle2D(69, 79, 894, 600);

    @Override
    protected void initialize() {
    }

    @Override
    public void setUp() {

        // Set the clip of the ImageViews to a rectangle with rounded corners
        setClipToImageView(handCard1);
        setClipToImageView(handCard2);
        setClipToImageView(handCard3);
        setClipToImageView(deckGold);
        setClipToImageView(deckGoldCard1);
        setClipToImageView(deckGoldCard2);
        setClipToImageView(deckResource);
        setClipToImageView(deckResourceCard1);
        setClipToImageView(deckResourceCard2);
        setClipToImageView(secretObj1);
        setClipToImageView(secretObj2);
        setClipToImageView(commonObjCard1);
        setClipToImageView(commonObjCard2);
        setClipToImageView(starterCard);
        setClipToImageView(secretObjective);

        //Initializes the tabs titles and disable the not used ones
        List<Tab> tabs = new ArrayList<>(Arrays.asList(tab2, tab3, tab4));
        int k = 0;
        for (String player : app.getPlayerList().keySet()) {
            if (!player.equals(app.getUsername())) {
                otherPlayers.add(player);
                System.out.println("Adding other player: " + player);
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
        gridDimensions.put(player1PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        gridDimensions.put(player2PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        if (app.getNumberOfPlayers() == 2) {
            player3Resources.setVisible(false);
            player4Resources.setVisible(false);
        } else if (app.getNumberOfPlayers() == 3) {
            player4Resources.setVisible(false);
            gridDimensions.put(player3PlayAreaGrid, new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        } else if (app.getNumberOfPlayers() == 4) {
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
        handCard1.setOnDragDetected(event -> {
            Dragboard db = handCard1.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(handCard1.getImage());
            db.setContent(content);
            try {
                //System.out.println("Selecting Card 0");
                client.selectCard(0);
            } catch (RemoteException e) {
                show_ServerCrashWarning();
            }
            event.consume();
        });

        handCard2.setOnDragDetected(event -> {
            Dragboard db = handCard2.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(handCard2.getImage());
            db.setContent(content);
            try {
                //System.out.println("Selecting Card 1");
                client.selectCard(1);
            } catch (RemoteException e) {
                show_ServerCrashWarning();
            }
            event.consume();
        });

        handCard3.setOnDragDetected(event -> {
            Dragboard db = handCard3.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(handCard3.getImage());
            db.setContent(content);
            try {
                //System.out.println("Selecting Card 2");
                client.selectCard(2);
            } catch (RemoteException e) {
                show_ServerCrashWarning();
            }
            event.consume();
        });
    }


    //FROM show_Update:_________________________________________________________________________________________________

    @Override
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        setImage(firstCardDeck, deckGold);
        setImage(card1, deckGoldCard1);
        setImage(card2, deckGoldCard2);
    }

    @Override
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        setImage(firstCardDeck, deckResource);
        setImage(card1, deckResourceCard1);
        setImage(card2, deckResourceCard2);
    }

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand) {
        if (username.equals(app.getUsername())) {
            if (!hand.isEmpty()) {
                setImage(hand.getFirst(), handCard1);
                if (hand.size() >= 2) {
                    setImage(hand.get(1), handCard2);
                    if (hand.size() >= 3) {
                        setImage(hand.get(2), handCard3);
                    } else {
                        handCard3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/CardsImages/CardPlaceHolder1.png"))));
                    }
                }
            }
        }
    }

    @Override
    public void show_scorePlayer(LinkedHashMap<String, Integer> scores) {
        //TODO
    }

    @Override
    public void show_starterCard(String username, PlayableCard starterCard) {
        if(username.equals(app.getUsername())){
            setImage(starterCard, this.starterCard);
        }
    }

    @Override
    public void show_playArea(String username, Map<Point, PlayableCard> playArea, Map<Resources, Integer> achievedResources) {
        //System.out.println("I'm " + app.getUsername() + ", I'm updating playArea for " + username);
        //System.out.println("Currently otherPlayers are: " + otherPlayers);
        if (username.equals(app.getUsername())) {
            //System.out.println("Updating playArea for player1");
            //System.out.println("My gridPane is: " + player1PlayAreaGrid);
            updateGrid(player1PlayAreaGrid, cells1, playArea);
            //updateResources(1, achievedResources);
        }
        if (username.equals(otherPlayers.getFirst())) {
            //System.out.println("Updating playArea for player2");
            //System.out.println("My gridPane is: " + player2PlayAreaGrid);
            updateGrid(player2PlayAreaGrid, cells2, playArea);
            //updateResources(2, achievedResources);
        }
        if (app.getNumberOfPlayers() >= 3) {
            if (username.equals(otherPlayers.get(1))) {
                //System.out.println("Updating playArea for player3");
                //System.out.println("My gridPane is: " + player3PlayAreaGrid);
                updateGrid(player3PlayAreaGrid, cells3, playArea);
                //updateResources(3, achievedResources);
            }
            if (app.getNumberOfPlayers() == 4) {
                if (username.equals(otherPlayers.get(2))) {
                    //System.out.println("Updating playArea for player4");
                    //System.out.println("My gridPane is: " + player4PlayAreaGrid);
                    updateGrid(player4PlayAreaGrid, cells4, playArea);
                    //updateResources(4, achievedResources);
                }
            }
        }
    }

    @Override
    public void show_chooseObjectiveCard(ObjectiveCard secretObjectiveCard1, ObjectiveCard secretObjectiveCard2) {
        setImage(secretObjectiveCard1, secretObj1);
        setImage(secretObjectiveCard2, secretObj2);
    }

    @Override
    public void show_commonObjectives(ObjectiveCard commonObjectiveCard1, ObjectiveCard commonObjectiveCard2) {
        setImage(commonObjectiveCard1, commonObjCard1);
        setImage(commonObjectiveCard2, commonObjCard2);
    }

    @Override
    public void show_objectiveCard(ObjectiveCard objectiveCard) {
        setImage(objectiveCard, secretObjective);
    }

    @Override
    public void playerStateInfo(String username, String info) {
        //TODO
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
     *
     * @param event The mouse event triggering the chat toggle.
     */
    public void showHideChat(MouseEvent event) {
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
    private void setImage(Card card, ImageView target) {
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
            } else {
                //System.out.println("Cell already present on [" + x + ";" + y + "]");
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
            } else {
                //System.out.println("Cell already present on [" + x + ";" + y + "]");
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
        System.out.println("Initial Grid Rows: " + grid.getRowCount());

        System.out.println("gridDimensions.get(grid).get(2): " + gridDimensions.get(grid).get(2));
        System.out.println("gridDimensions.get(grid).get(3): " + gridDimensions.get(grid).get(3));
        System.out.println("gridDimensions.get(grid).getFirst(): " + gridDimensions.get(grid).getFirst());
        System.out.println("gridDimensions.get(grid).get(1): " + gridDimensions.get(grid).get(1));

        System.out.println("(gridDimensions.get(grid).get(2) - gridDimensions.get(grid).getFirst() + 2): " + (gridDimensions.get(grid).get(2) - gridDimensions.get(grid).getFirst() + 2));
        System.out.println("(gridDimensions.get(grid).get(3) - gridDimensions.get(grid).get(1) + 2): " + (gridDimensions.get(grid).get(3) - gridDimensions.get(grid).get(1) + 2));*/

        for (int x = 0; x < grid.getColumnCount(); x++) {
            for (int y = 0; y < grid.getRowCount(); y++) {
                cells.get(new Pair<>(x, y)).hideImage();
                System.out.println("Cell [" + x + ";" + y + "] set to invisible image");
            }
        }
        int newCoordinateX;
        int newCoordinateY;
        for (Map.Entry<Point, PlayableCard> placedCard : playArea.entrySet()) {
            newCoordinateX = placedCard.getKey().x - gridDimensions.get(grid).getFirst() + 1;
            newCoordinateY = gridDimensions.get(grid).get(3) - placedCard.getKey().y + 1;
            System.out.println("Adding card that was on Point: " + placedCard.getKey() + " on cell [" + newCoordinateX + ";" + newCoordinateY + "]");
            //System.out.println("Card Image Path: " + placedCard.getValue().getImage());
            cells.get(new Pair<>(newCoordinateX, newCoordinateY)).setCardImage(placedCard.getValue().getImage());
        }
    }

    //TODO modify after deserialization of resources
    private void updateResources(int playerNumber, Map<Resources, Integer> achievedResources) {

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
    private void setClipToImageView(ImageView target){
        Rectangle clip = new Rectangle(
                target.getFitWidth(), target.getFitHeight()
        );
        clip.setArcWidth(15);
        clip.setArcHeight(15);

        target.setClip(clip);
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
        private int positionX;
        private int positionY;
        private Image cardImage;
        private final StackPane pane;

        /**
        * Needs to be initialized with a GridPane, an x and a y coordinate.
        *
        * @param grid The GridPane to which the cell belongs.
        * @param x The x-coordinate of the cell.
        * @param y The y-coordinate of the cell.
        */
        public Cell(GridPane grid, int x, int y) {
            positionX = x;
            positionY = y;
            //Border border = new Border(new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/CardsImages/CardPlaceHolder1.png")));

            pane = new StackPane();

            pane.setMaxHeight(60);
            pane.setMaxWidth(115);
            pane.setMinHeight(60);
            pane.setMinWidth(115);
            pane.getChildren().add(this);

            //If the cell belongs to player1, it is set to accept drag and drop events
            if (grid.equals(player1PlayAreaGrid)) {
                setOnDragOver(event -> {
                    if (event.getGestureSource() != this && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                setOnDragDropped(event -> {
                    //System.out.println("Mouse Drag Dropped on cell " + positionX + " " + positionY);
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

            this.setImage(cardImage);
            this.setPreserveRatio(true);
            this.setViewport(cardViewport);
            this.setFitWidth(149); // set the card width
            this.setFitHeight(100); // Set the card height

            // Round image boarder
            setClipToImageView(this);

            grid.add(pane, x, y);
        }

        public void setCardImage(String cardImageUrl) {
            pane.toFront();
            this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cardImageUrl)));
            this.setImage(cardImage);
        }

        public void hideImage() {
            pane.toBack();
            this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/it/polimi/ingsw/gc31/Images/CardsImages/CardPlaceHolder1.png")));
            this.setImage(cardImage);
        }
    }
}

