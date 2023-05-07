package github.zljtt.legendofthegreatlake.quests;

public class QuestManagerBackup {
    /**
     public static Path SAVE_LOCATION = FMLPaths.GAMEDIR.get().resolve(LegendOfTheGreatLake.MODID);
     private static QuestManagerBackup INSTANCE;

     public static QuestManagerBackup getInstance() {
     return INSTANCE;
     }

     public static void Init() {
     INSTANCE = new QuestManagerBackup();
     INSTANCE.load();
     }

     private final Map<String, Quest> quests;
     private volatile boolean isLoaded;
     private volatile boolean isSaving;

     private QuestManagerBackup() {
     quests = new HashMap<>();
     isLoaded = false;
     isSaving = false;
     }

     public void load() {
     isLoaded = false;
     if (isSaving) {
     LegendOfTheGreatLake.LOGGER.error("Quest loading interrupted due to file saving");
     return;
     }
     // Create and start a new thread to use the JSON object
     Thread thread = new Thread(() -> {
     LegendOfTheGreatLake.LOGGER.info("Reading quest files in " + SAVE_LOCATION.toAbsolutePath());
     try {
     if (Files.exists(SAVE_LOCATION.resolve("quests.json"))) {
     String file = new String(Files.readAllBytes(SAVE_LOCATION.resolve("quests.json")));
     Gson gson = new Gson();
     JsonObject questConfig = gson.fromJson(file, JsonObject.class);
     JsonArray jsonArray = questConfig.getAsJsonArray("quests");
     if (jsonArray == null) {
     LegendOfTheGreatLake.LOGGER.error("Error reading quest files into json");
     } else {
     for (int i = 0; i < jsonArray.size(); i++) {
     Quest questInstance = gson.fromJson(jsonArray.get(i), Quest.class);
     quests.put(questInstance.getName(), questInstance);
     }
     }
     LegendOfTheGreatLake.LOGGER.info("All quests loaded");
     } else {
     Files.createDirectories(SAVE_LOCATION);
     }
     isLoaded = true;
     } catch (IOException e) {
     LegendOfTheGreatLake.LOGGER.error("IO exception during quests loading");
     }
     }, "Quest Loading thread");
     thread.start();
     }

     public void Save() {
     if (!isLoaded) {
     LegendOfTheGreatLake.LOGGER.error("Can't save quests file when the file is not loaded");
     return;
     }
     if (isSaving) {
     LegendOfTheGreatLake.LOGGER.error("Can't save quests file when the file is already saving");
     return;
     }
     isSaving = true;
     // Create and start a new thread to use the JSON object
     Thread thread = new Thread(() -> {
     LegendOfTheGreatLake.LOGGER.info("Saving quest files to " + SAVE_LOCATION.toAbsolutePath());
     try {
     Gson gson = new Gson();
     JsonObject questConfig = new JsonObject();
     JsonArray questArray = new JsonArray();
     for (Map.Entry<String, Quest> entry : quests.entrySet()) {
     questArray.add(gson.toJson(entry.getValue()));
     }
     questConfig.add("quests", questArray);
     Files.write(SAVE_LOCATION.resolve("quests.json"), questConfig.toString().getBytes(StandardCharsets.UTF_8));
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
     **/
}
