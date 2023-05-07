package github.zljtt.legendofthegreatlake.npc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ClientNPCGenerator extends JFrame {

    private final JPanel mainPanel;
    private JScrollPane scrollPane;
    private Dialog root;
    private final JTextField name = new JTextField();

    public ClientNPCGenerator() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 1000));

        JButton selectButton = new JButton("Select File");
        selectButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./run/legendofthegreatlake/resourcepack/assets/legendofthegreatlake/dialogs"));
            int result = fileChooser.showOpenDialog(ClientNPCGenerator.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Do something with the selected file
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
                try {
                    String file = Files.readString(selectedFile.toPath(), StandardCharsets.UTF_8);
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(file, JsonObject.class);
                    this.name.setText(json.get("name").getAsString());
                    this.root = new Dialog(json.get("dialogs").getAsJsonObject());

                    mainPanel.add(new JLabel("Name"));
                    mainPanel.add(name);
                    mainPanel.add(this.createDialogPanel(root));
                    mainPanel.revalidate();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        // selectButton.setBounds(0, 0, 30, 20);
        mainPanel.add(selectButton);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientNPCGenerator::new);
    }

    public JPanel createDialogPanel(Dialog dialog) {
        JPanel dialogPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("对话"));
        infoPanel.add(new JTextArea(dialog.getDialog()));
        JPanel optionsPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        // Create and add subpanels to the main panel
        for (int i = 0; i < dialog.getOptions().size(); i++) {
            JPanel optionPanel = createOptionPanel(dialog.getOptions().get(i));
            optionPanel.setPreferredSize(new Dimension(200, 50));
            optionPanel.setBackground(Color.gray);
            optionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            optionsPanel.add(optionPanel);
        }
        optionsPanel.revalidate();
        JScrollPane scrollPane = new JScrollPane(optionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        dialogPanel.add(infoPanel);
        dialogPanel.add(optionsPanel);
        return dialogPanel;
    }

    public JPanel createOptionPanel(Dialog.Option option) {
        JPanel optionPanel = new JPanel();
        return optionPanel;
    }
}

