package github.zljtt.legendofthegreatlake.quests;

public class Quest {

    private String id;

    /**
     * Default constructor, required by gson
     */
    public Quest() {

    }

    public String getName() {
        return id;
    }

    public void setName(String id) {
        this.id = id;
    }
}
