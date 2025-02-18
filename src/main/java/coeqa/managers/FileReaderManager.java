package coeqa.managers;

import coeqa.datareader.ConfigReader;

public class FileReaderManager {


    private FileReaderManager() {
        getConfiguration();

    }

    public static FileReaderManager fileRead() {
        return new FileReaderManager();
    }

    public ConfigReader getConfiguration() {
        return new ConfigReader();
    }


}

