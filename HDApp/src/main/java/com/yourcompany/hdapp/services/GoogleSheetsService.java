package com.yourcompany.hdapp.services;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GoogleSheetsService {
    private static final String APPLICATION_NAME = "HDApp";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private Sheets sheetsService;

    public GoogleSheetsService() throws IOException {
        GoogleCredentials credentials;
        try (FileInputStream serviceAccountStream = new FileInputStream("path/to/serviceAccountKey.json")) {
            credentials = GoogleCredentials.fromStream(serviceAccountStream)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        }

        sheetsService = new Sheets.Builder(new NetHttpTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<List<Object>> getSheetData(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response.getValues();
    }
}
