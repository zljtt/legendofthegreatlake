package github.zljtt.legendofthegreatlake.npc;

public class ClientNPC {

    private String name;
    private Dialog dialog;

    public ClientNPC(String name, Dialog dialog) {
        this.name = name;
        this.dialog = dialog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
