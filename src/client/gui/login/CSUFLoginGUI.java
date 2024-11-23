package client.gui.login;


import client.ClientConfig;
import shared.enums.Institutions;

public class CSUFLoginGUI extends BaseLoginGUI {
    public CSUFLoginGUI() {
        super(Institutions.CSUF);
    }

    @Override
    protected String getUniversityIconPath() {
        return ClientConfig.CSUF_LOGO_FILE_PATH;
    }
}