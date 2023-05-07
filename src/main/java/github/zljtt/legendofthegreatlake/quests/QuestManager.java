package github.zljtt.legendofthegreatlake.quests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QuestManager {
    public static Path QUEST_SAVE_LOCATION = LegendOfTheGreatLake.PACK_REPO.resolve(LegendOfTheGreatLake.PACK_NAME).resolve("data").resolve(LegendOfTheGreatLake.MODID).resolve("quests");
    private static QuestManager INSTANCE;

    public static QuestManager getInstance() {
        return INSTANCE;
    }

    public static void Init() {
        INSTANCE = new QuestManager();
    }

    private final Map<String, Quest> quests;
    private volatile boolean isSaving;
    private String saveLocation;

    private QuestManager() {
        quests = new HashMap<>();
        isSaving = false;
    }

    public void addQuest(Quest quest) {
        this.quests.put(quest.getName(), quest);
    }


    public void Save() {
        if (isSaving) {
            LegendOfTheGreatLake.LOGGER.error("Can't save quests file when the file is already saving");
            return;
        }
        isSaving = true;
        // Create and start a new thread to use the JSON object
        Thread thread = new Thread(() -> {
            LegendOfTheGreatLake.LOGGER.info("Saving quest files to " + QUEST_SAVE_LOCATION.toAbsolutePath());
            try {
                Gson gson = new Gson();
                JsonObject questConfig = new JsonObject();
                JsonArray questArray = new JsonArray();
                for (Map.Entry<String, Quest> entry : quests.entrySet()) {
                    questArray.add(gson.toJson(entry.getValue()));
                }
                questConfig.add("quests", questArray);
                Files.write(QUEST_SAVE_LOCATION.resolve("quests.json"), questConfig.toString().getBytes(StandardCharsets.UTF_8));
                LegendOfTheGreatLake.LOGGER.info("All quests saved");
            } catch (IOException e) {
                LegendOfTheGreatLake.LOGGER.error("IO exception during quests saving");
            } catch (UnsupportedOperationException e) {
                LegendOfTheGreatLake.LOGGER.error("Unsupported encoding");
            }

            isSaving = false;
        }, "Quest Saving thread");
        thread.start();
    }

    public static class QuestLoader extends SimpleJsonResourceReloadListener {
        public QuestLoader() {
            super(new Gson(), "quests");
        }

        @Override
        protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager resourceManager, ProfilerFiller profiler) {
            QuestManager.Init();
            Gson gson = new Gson();
            LegendOfTheGreatLake.LOGGER.info("Start Loading Server Data Pack ");

            for (Map.Entry<ResourceLocation, JsonElement> entry : jsonElementMap.entrySet()) {
                if (entry.getValue() instanceof JsonObject json) {
                    LegendOfTheGreatLake.LOGGER.info("Loading quest from resource location " + entry.getKey());
                    for (JsonElement questJson : json.getAsJsonArray("quests")) {
                        QuestManager.getInstance().addQuest(gson.fromJson(questJson, Quest.class));
                    }
                }
            }

        }
    }
}
