package it.polimi.ingsw.gc31.model.card;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.util.*;

/**
 * This class represents the front side of card;
 *
 * @author Christian Salvini
 */
public class CardFront {
    /**
     * The score obtained from placing the card.
     */
    private final int score;
    /**
     * List of Resources that represents the corners of the card's Front. The
     * corners are identified according
     * to the following convention:
     * 0-> Up Dx
     * 1-> Down Dx
     * 2-> Down Sx
     * 3-> Up Sx
     * It can have a maximum of 4 elements.
     */
    private final List<Resources> resources;
    /**
     * The resources the Player must have in their playArea to be able to place the
     * card.
     */
    private final Map<Resources, Integer> requirements;
    /**
     * link to the image of the front side
     */
    private final String dirImg;
    /**
     * Objective of the card to be verified in order to obtain the score points.
     */
    private final Objective objective;

    /**
     * Constructor of the class
     */
    public CardFront(int score, List<Resources> resources, Map<Resources, Integer> requirements, String dirImg,
            Objective ob)
    // throws WrongNumberOfCornerException
    // , DirImgValueMissingException
    {
        this.score = score;

        // if (resources.size() != 4) throw new WrongNumberOfCornerException();
        this.resources = listDeepCopy(resources);
        this.requirements = mapDeepCopy(requirements);

        // if (dirImg == null) throw new DirImgValueMissingException();
        this.dirImg = dirImg;
        // TODO implementare depp copy per ob
        this.objective = ob;
    }

    /**
     * Check if it's possible to place a card on the corner indicated by the
     * parameter Corner
     *
     * @param corner corner to be checked
     * @return true if it is possible to place a card on that corner, false
     *         otherwise
     */
    public boolean checkCorner(int corner) {
        return resources.get(corner) != Resources.HIDDEN;
    }

    /**
     * @param corner is the index (0 to 3) of the 4 corners of the card (central
     *               resources included from number 4 to 7)
     * @return The type of Resource that has been covered
     */
    public Resources coverCorner(int corner) {
        Resources ret = resources.get(corner);
        resources.set(corner, Resources.HIDDEN);
        return ret;
    }

    // TODO provare con programmazione funzionale e usando listDeepCopy
    public List<Resources> getResources() {
        List<Resources> res = new ArrayList<>();
        for (Resources val : resources) {
            if (val != Resources.HIDDEN && val != Resources.EMPTY) {
                res.add(val);
            }
        }
        return res;
    }

    public Map<Resources, Integer> getRequirements() {
        return mapDeepCopy(requirements);
    }

    public Objective getObjective() {
        return objective;
    }

    public String getImage() {
        return this.dirImg;
    }

    public int getScore() {
        return this.score;
    }

    private List<Resources> listDeepCopy(List<Resources> listToCopy) {
        List<Resources> newList = new ArrayList<>();
        for (Resources val : listToCopy) {
            newList.add(val);
        }
        return newList;
    }

    private Map<Resources, Integer> mapDeepCopy(Map<Resources, Integer> mapToCopy) {
        Map<Resources, Integer> newMap = new HashMap<>();
        for (Map.Entry<Resources, Integer> val : mapToCopy.entrySet()) {
            newMap.put(val.getKey(), Integer.valueOf(val.getValue()));
        }
        return newMap;
    }

    public JsonObject serializeToJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("score", this.score);

        JsonArray resourcesArray = new JsonArray();
        for (Resources res : this.resources) {
            resourcesArray.add(res.toString());
        }
        jsonObject.add("resources", resourcesArray);

        if (requirements.equals(Collections.emptyMap())) {
            jsonObject.add("requirements", null);
        } else {
            JsonObject requirementsObjet = new JsonObject();
            for (Map.Entry<Resources, Integer> res : this.requirements.entrySet()) {
                requirementsObjet.addProperty(res.getKey().toString(), res.getValue());
            }
            jsonObject.add("requirements", requirementsObjet);
        }
        jsonObject.addProperty("dirImg", dirImg);

        jsonObject.add("objective", null);
        return jsonObject;
    }

    public List<Resources> getCorners() {
        return resources;
    }
}
