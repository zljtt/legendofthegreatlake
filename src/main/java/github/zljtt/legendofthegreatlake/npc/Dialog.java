package github.zljtt.legendofthegreatlake.npc;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Dialog {
    public static String DEFAULT_TEXT = "...";
    private List<Option> options;
    private String dialog;

    public Dialog(String dialog) {
        this.options = new ArrayList<>();
        this.dialog = dialog;
    }

    public Dialog(JsonObject json) {
        this.options = new ArrayList<>();
        this.dialog = json.get("dialog") == null ? DEFAULT_TEXT : json.get("dialog").getAsString();
        int count = json.get("optionCount") == null ? 0 : json.get("optionCount").getAsInt();
        for (int i = 0; i < count; i++) {
            if (json.get("option" + (i + 1)) != null) {
                this.options.add(new Option(json.get("option" + (i + 1)).getAsJsonObject(), i));
            }
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("dialog", this.dialog);
        for (int i = 0; i < this.options.size(); i++) {
            json.add("option" + (i + 1), this.options.get(i).toJson());
        }
        return json;
    }

    public List<Option> getOptions() {
        return options;
    }

    public List<Option> getVisibleOptions() {
        return options;
    }


    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public class Option {
        private int index;
        private String text;
        private Dialog next;
        private Requirement requirement;

        public Option(int index, String text, Dialog next, Requirement requirement) {
            this.index = index;
            this.text = text;
            this.next = next;
            this.requirement = requirement;
        }

        public Option(JsonObject json, int index) {
            this.index = index;
            this.text = json.get("text") == null ? DEFAULT_TEXT : json.get("text").getAsString();
            this.next = json.get("next") == null ? null : new Dialog(json.get("next").getAsJsonObject());
            this.requirement = json.get("requirement") == null ? null : new Requirement(json.get("requirement").getAsJsonObject());
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("text", this.text);
            if (this.requirement != null) {
                json.add("requirement", this.requirement.toJson());
            }
            if (this.next != null) {
                json.add("next", this.next.toJson());
            }
            return json;
        }

        public Boolean hasNext() {
            return next != null;
        }

        public Boolean hasRequirement() {
            return requirement != null;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int choice) {
            this.index = choice;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Dialog getNext() {
            return next;
        }

        public void setNext(Dialog next) {
            this.next = next;
        }

        public Requirement getRequirement() {
            return requirement;
        }

        public void setRequirement(Requirement requirement) {
            this.requirement = requirement;
        }
    }


    public class Requirement {
        public static final String NO_QUEST_REQUIREMENT = "NONE";

        int level;
        String quest;

        public Requirement(JsonObject json) {
            this.level = json.get("level") == null ? 0 : json.get("level").getAsInt();
            this.quest = json.get("quest") == null ? NO_QUEST_REQUIREMENT : json.get("quest").getAsString();
        }

        public Requirement(int level, String quest) {
            this.level = level;
            this.quest = quest;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("level", this.level);
            json.addProperty("quest", this.quest);
            return json;
        }


        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getQuest() {
            return quest;
        }

        public void setQuest(String quest) {
            this.quest = quest;
        }
    }


}
