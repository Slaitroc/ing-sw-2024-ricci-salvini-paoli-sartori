package it.polimi.ingsw.gc31.Model.Card;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.*;

/**
 * This class represents the front side of card;
 * @author Christian Salvini
 */
public class CardFront {
    private final int score;
    private final List<Resources> resources;
    private final Map<Resources, Integer> requirements;
    private final String dirImg;
    private final Objective ob;

    public CardFront(int score, List<Resources> resources, Map<Resources, Integer> requirements, String dirImg, Objective ob) {
        this.score = score;

        this.resources = listDeepCopy(resources);
        this.requirements = mapDeepCopy(requirements);

        this.dirImg = dirImg;
        //TODO implementare depp copy per ob
        this.ob = ob;
    }

    public boolean checkCorner(int corner) {
        return resources.get(corner) != Resources.HIDDEN;
    }
    public void coverCorner(int corner) {
        resources.set(corner, Resources.HIDDEN);
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
    public Map<Resources, Integer> getRequirements(){
        return mapDeepCopy(requirements);
    }

    public Objective getObjective() {
        return ob;
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

    /**
     * utility per la serializzazione
     */
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
            JsonArray requirementsArray = new JsonArray();
            for (Map.Entry<Resources, Integer> res : this.requirements.entrySet()) {
                requirementsArray.add(res.toString());
            }
            jsonObject.add("requirements", requirementsArray);
        }
        jsonObject.addProperty("dirImg", dirImg);

        jsonObject.add("objective", null);
        return jsonObject;
    }
}
