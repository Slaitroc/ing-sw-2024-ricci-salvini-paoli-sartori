package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;
import org.junit.jupiter.api.*;

import java.awt.Point;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayAreaTest {

        private PlayArea playArea;

        private Board board;

        @BeforeEach
        public void setUp() throws EmptyDeckException {
                playArea = new PlayArea();
                board = new Board();
                // starterCard.changeSide();
                playArea.placeStarter(board.getDeckStarter().draw());
        }

        /**
         * Generic test that place random cards (placed on the back) in specified
         * location
         * To add new card in new space copy/paste the pattern and update the location
         * value
         * 
         * @author Matteo Paoli
         */
        @Test
        @DisplayName("Placing overlapping Cards Test")
        public void testPlaceOnRight() throws EmptyDeckException{

                System.out.println("testPlace (1,1)):");
                PlayableCard playableCard = board.getDeckResource().draw();
                playArea.place(playableCard, new Point(1, 1));
                assertEquals(playableCard, playArea.getPlacedCards().get(new Point(1, 1)));
                System.out.println("Correct");

                System.out.println("testPlace (-1,1):");
                playableCard = board.getDeckResource().draw();
                playableCard = board.getDeckResource().draw();
                playArea.place(playableCard, new Point(-1, 1));
                assertEquals(playableCard, playArea.getPlacedCards().get(new Point(-1, 1)));
                System.out.println("Correct");

                System.out.println("testPlace (-1,-1):");
                playableCard = board.getDeckResource().draw();
                playableCard = board.getDeckResource().draw();
                playArea.place(playableCard, new Point(-1, -1));
                assertEquals(playableCard, playArea.getPlacedCards().get(new Point(-1, -1)));
                System.out.println("Correct");

                System.out.println("testPlace (1,-1):");
                playableCard = board.getDeckResource().draw();
                playableCard = board.getDeckResource().draw();
                playArea.place(playableCard, new Point(1, -1));
                assertEquals(playableCard, playArea.getPlacedCards().get(new Point(1, -1)));
                System.out.println("Correct");

                System.out.println("testPlace (1,-1):");
                PlayableCard playableCard2 = board.getDeckResource().draw();
                playArea.place(playableCard2, new Point(1, -1));
                assertEquals(playableCard, playArea.getPlacedCards().get(new Point(1, -1)));
                System.out.println("Correct");

                System.out.println("testPlace (2, 0):");
                playableCard = board.getDeckResource().draw();
                playableCard = board.getDeckResource().draw();
                playArea.place(playableCard, new Point(2, 0));
                assertEquals(playableCard, playArea.getPlacedCards().get(new Point(2, 0)));
                System.out.println("Correct");

                System.out.println("testPlace (2, 0):");
                playableCard2 = board.getDeckResource().draw();
                playableCard2 = board.getDeckResource().draw();
                playArea.place(playableCard2, new Point(2, 0));
                assertEquals(playableCard, playArea.getPlacedCards().get(new Point(2, 0)));
                System.out.println("Correct");

                System.out.println("testPlace (2, -2):");
                playableCard = board.getDeckResource().draw();
                playableCard = board.getDeckResource().draw();
                playArea.place(playableCard, new Point(2, -2));
                assertEquals(playableCard, playArea.getPlacedCards().get(new Point(2, -2)));
                System.out.println("Correct");

                System.out.println("testPlace (100, -200):");
                playableCard = board.getDeckResource().draw();
                playableCard = board.getDeckResource().draw();
                playArea.place(playableCard, new Point(100, -200));
                assertNull(playArea.getPlacedCards().get(new Point(100, -200)));
                System.out.println("Correct");
        }

        /**
         * Verify that gold card on front is tested before placement
         * WARNING:
         * This test SHOULD result positive in the vast majority of the cases
         * There is little to non chances to fail caused to the randomness of the
         * resources cards
         * 
         * @author Matteo Paoli
         */
        @Test
        public void testCheckRequirements() throws EmptyDeckException {

                PlayableCard resourceCard = board.getDeckResource().draw();
                playArea.place(resourceCard, new Point(1, 1));
                assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(1, 1)));

                resourceCard = board.getDeckResource().draw();
                resourceCard = board.getDeckResource().draw();
                playArea.place(resourceCard, new Point(-1, -1));
                assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(-1, -1)));

                resourceCard = board.getDeckResource().draw();
                resourceCard = board.getDeckResource().draw();
                playArea.place(resourceCard, new Point(1, -1));
                assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(1, -1)));

                resourceCard = board.getDeckResource().draw();
                resourceCard = board.getDeckResource().draw();
                playArea.place(resourceCard, new Point(-1, 1));
                assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(-1, 1)));

                PlayableCard goldCard = board.getDeckGold().draw();
                goldCard.changeSide();
                // Map<Resources, Integer> achievedResources = new HashMap<Resources,
                // Integer>(playArea.getAchievedResources());
                playArea.place(goldCard, new Point(-2, 2));
                assertNull(playArea.getPlacedCards().get(new Point(-2, 2)));
        }

        /**
         * General Test that allow me to place specific cards (resource and gold)
         * in specific locations
         */
        @Test
        @DisplayName("Placing goldCards Test")
        public void testGoldCards() {

                System.out.println("resourceCard in (1,1))");
                PlayableCard resourceCard = createResourceCard(
                                Resources.ANIMAL,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY);
                resourceCard.changeSide();
                playArea.place(resourceCard, new Point(1, 1));
                assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(1, 1)));

                System.out.println("resourceCard in (1,-1))");
                resourceCard = createResourceCard(
                                Resources.ANIMAL,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY);
                resourceCard.changeSide();
                playArea.place(resourceCard, new Point(1, -1));
                assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(1, -1)));

                System.out.println("resourceCard in (-1,-1))");
                resourceCard = createResourceCard(
                                Resources.ANIMAL,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY);
                resourceCard.changeSide();
                playArea.place(resourceCard, new Point(-1, -1));
                assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(-1, -1)));

                System.out.println("resourceCard in (-1,1))");
                resourceCard = createResourceCard(
                                Resources.ANIMAL,
                                Resources.EMPTY,
                                Resources.MUSHROOM,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY);
                resourceCard.changeSide();
                playArea.place(resourceCard, new Point(-1, 1));
                assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(-1, 1)));

                System.out.println("goldCard in (2,0)):");
                PlayableCard goldCard = createGoldCard(
                                Resources.ANIMAL,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.ANIMAL, 3,
                                Resources.MUSHROOM, 1);
                goldCard.changeSide();
                playArea.place(goldCard, new Point(2, 0));
                assertEquals(goldCard, playArea.getPlacedCards().get(new Point(2, 0)));

                System.out.println("goldCard in (3,1)):");
                goldCard = createGoldCard(
                                Resources.ANIMAL,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.EMPTY,
                                Resources.ANIMAL, 5,
                                Resources.MUSHROOM, 1);
                goldCard.changeSide();
                playArea.place(goldCard, new Point(3, 1));
                assertNull(playArea.getPlacedCards().get(new Point(3, 1)));

        }

        /**
         * Custom method to build a gold card
         * 
         * @param f0,f1,f2,f3:   Resources on front (0=NorthWest → than clockwise)
         * @param b0,b1,b2,b3,b4 Resources on Back (b4 is the permanent resource in
         *                       center)
         * @param r1,r2          Resources required
         * @param n1,n2          number of required resources
         * @return obj GoldCard
         */
        private PlayableCard createGoldCard(Resources f0, Resources f1, Resources f2, Resources f3,
                        Resources b0, Resources b1, Resources b2, Resources b3, Resources b4,
                        Resources r1, int n1, Resources r2, int n2) {
                CardColor cardColor = CardColor.RED;

                int score = 0;

                // resources deve avere 4 elementi
                List<Resources> resourcesFront = new ArrayList<>();
                resourcesFront.add(f0);
                resourcesFront.add(f1);
                resourcesFront.add(f2);
                resourcesFront.add(f3);

                Map<Resources, Integer> requirements = new HashMap<>();
                requirements.put(r1, n1);
                requirements.put(r2, n2);

                Objective ob = null;

                CardFront front = new CardFront(
                                score,
                                resourcesFront,
                                requirements,
                                null,
                                ob);

                // resourceBack può avere dai 4 ai 7 elementi
                List<Resources> resourceBack = new ArrayList<>();
                resourceBack.add(b0);
                resourceBack.add(b1);
                resourceBack.add(b2);
                resourceBack.add(b3);
                resourceBack.add(b4);

                CardBack back = new CardBack(
                                resourceBack,
                                null);

                return new GoldCard(cardColor, front, back);
        }

        /**
         * Custom method to build a resource card
         * 
         * @param f0,f1,f2,f3:   Resources on front (0=NorthWest → than clockwise)
         * @param b0,b1,b2,b3,b4 Resources on Back (b4 is the permanent resource in
         *                       center)
         * @return obj ResourceCard
         */
        private PlayableCard createResourceCard(Resources f0, Resources f1, Resources f2, Resources f3,
                        Resources b0, Resources b1, Resources b2, Resources b3, Resources b4) {
                CardColor cardColor = CardColor.RED;

                int score = 0;

                // resources deve avere 4 elementi
                List<Resources> resourcesFront = new ArrayList<>();
                resourcesFront.add(f0);
                resourcesFront.add(f1);
                resourcesFront.add(f2);
                resourcesFront.add(f3);

                Map<Resources, Integer> requirements = Collections.emptyMap();

                CardFront front = new CardFront(
                                score,
                                resourcesFront,
                                requirements,
                                null,
                                null);

                // resourceBack può avere dai 4 ai 7 elementi
                List<Resources> resourceBack = new ArrayList<>();
                resourceBack.add(b0);
                resourceBack.add(b1);
                resourceBack.add(b2);
                resourceBack.add(b3);
                resourceBack.add(b4);

                CardBack back = new CardBack(
                                resourceBack,
                                null);

                return new ResourceCard(cardColor, front, back);
        }
}