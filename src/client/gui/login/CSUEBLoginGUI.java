package client.gui.login;

import client.ClientConfig;
import shared.enums.Institutions;

public class CSUEBLoginGUI extends BaseLoginGUI {
    public CSUEBLoginGUI() {
        super(Institutions.CSUEB);
    }

    @Override
    protected String getUniversityIconPath() {
        return ClientConfig.CSUEB_LOGO_FILE_PATH; // Use path from ClientConfig
    }
}
