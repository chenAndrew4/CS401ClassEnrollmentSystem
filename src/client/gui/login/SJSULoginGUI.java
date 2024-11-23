package client.gui.login;

import client.ClientConfig;
import shared.enums.Institutions;

public class SJSULoginGUI extends BaseLoginGUI {
    public SJSULoginGUI() {
        super(Institutions.SJSU);
    }

    @Override
    protected String getUniversityIconPath() {
        return ClientConfig.SJSU_LOGO_FILE_PATH;
    }
}