package github.zljtt.legendofthegreatlake.npc;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import github.zljtt.legendofthegreatlake.LegendOfTheGreatLake;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class ClientNPCManager {

    private static ClientNPCManager INSTANCE;

    public static ClientNPCManager getInstance() {
        return INSTANCE;
    }

    public static void Init() {
        INSTANCE = new ClientNPCManager();
    }

    private final Map<String, ClientNPC> npcs;

    public ClientNPCManager() {
        npcs = new HashMap<>();
    }

    public void addNPC(String name, ClientNPC dialog) {
        npcs.put(name, dialog);
    }

    public Dialog getDialog(String id) {
        return npcs.get(id) == null ? new Dialog("NO SUCH DIALOG") : npcs.get(id).getDialog();
    }

    public String getName(String id) {
        return npcs.get(id) == null ? "NO SUCH NPC" : npcs.get(id).getName();
    }

    public static class DialogLoader extends SimpleJsonResourceReloadListener {
        public DialogLoader() {
            super(new Gson(), "dialogs");
        }

        @Override
        protected void apply(Map<ResourceLocation, JsonElement> jsonElementMap, ResourceManager resourceManager, ProfilerFiller profiler) {
            ClientNPCManager.Init();
            for (Map.Entry<ResourceLocation, JsonElement> entry : jsonElementMap.entrySet()) {
                LegendOfTheGreatLake.LOGGER.info("Loading dialog " + entry.getKey().getPath());
                try {
                    String name = entry.getValue().getAsJsonObject().get("name").getAsString();
                    Dialog dialog = new Dialog(entry.getValue().getAsJsonObject().get("dialogs").getAsJsonObject());
                    ClientNPCManager.getInstance().addNPC(entry.getKey().getPath(), new ClientNPC(name, dialog));
                } catch (IllegalStateException e) {
                    LegendOfTheGreatLake.LOGGER.error("Error loading dialog for " + entry.getKey() + " due to incorrect json format");
                } catch (NullPointerException e) {
                    LegendOfTheGreatLake.LOGGER.error("Error loading dialog for " + entry.getKey() + " due to incorrect variable name");

                }
            }

        }
    }


}
