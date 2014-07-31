package com.gmail.lifeofreilly.longtail;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HAR {
    private final static Logger log = Logger.getLogger(HAR.class);
    private JSONObject jsonObject;
    private final File file;

    public HAR(final File file) {
        this.file = file;
        parseJSON();
    }

    private void parseJSON() {
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader(file));
        } catch (FileNotFoundException ex) {
            log.error("An error occurred while attempting read .har file: " + ex);
            throw new IllegalArgumentException("The path supplied must point to an existing .har file.");
        } catch (ParseException ex) {
            log.error("An error occurred while attempting to parse the .har file: " + ex);
        } catch (IOException ex) {
            log.error("An error occurred while attempting to process the .har file: " + ex);
        }
    }

    public String getFileName() {
        return file.getName();
    }

    public long getStatus() {
        JSONObject log = (JSONObject) jsonObject.get("log");
        JSONArray entries = (JSONArray) log.get("entries");
        JSONObject entriesObject = (JSONObject) entries.get(0);
        JSONObject response = (JSONObject) entriesObject.get("response");
        return (Long) response.get("status");
    }

    public String getURL() {
        JSONObject log = (JSONObject) jsonObject.get("log");
        JSONArray entries = (JSONArray) log.get("entries");
        JSONObject entriesObject = (JSONObject) entries.get(0);
        JSONObject response = (JSONObject) entriesObject.get("request");
        return (String) response.get("url");
    }

    public String getTitle() {
        JSONObject log = (JSONObject) jsonObject.get("log");
        JSONArray pages = (JSONArray) log.get("pages");
        JSONObject pagesObject = (JSONObject) pages.get(0);
        return (String) pagesObject.get("title");
    }

    public String getStartedDateTime() {
        JSONObject log = (JSONObject) jsonObject.get("log");
        JSONArray pages = (JSONArray) log.get("pages");
        JSONObject pagesObject = (JSONObject) pages.get(0);
        return (String) pagesObject.get("startedDateTime");
    }

    public long getOnContentLoad() {
        JSONObject log = (JSONObject) jsonObject.get("log");
        JSONArray pages = (JSONArray) log.get("pages");
        JSONObject pagesObject = (JSONObject) pages.get(0);
        JSONObject pageTimings = (JSONObject) pagesObject.get("pageTimings");
        return (Long) pageTimings.get("onContentLoad");
    }

    public long getOnLoad() {
        JSONObject log = (JSONObject) jsonObject.get("log");
        JSONArray pages = (JSONArray) log.get("pages");
        JSONObject pagesObject = (JSONObject) pages.get(0);
        JSONObject pageTimings = (JSONObject) pagesObject.get("pageTimings");
        return (Long) pageTimings.get("onLoad");
    }

    @SuppressWarnings("unchecked")
    public JSONObject getMetaData() {
        JSONObject harMetaData = new JSONObject();
        harMetaData.put("file",getFileName());
        harMetaData.put("url",getURL());
        harMetaData.put("status",getStatus());
        harMetaData.put("title", getTitle());
        harMetaData.put("time",getStartedDateTime());
        harMetaData.put("onContentLoad", getOnContentLoad());
        harMetaData.put("onLoad", getOnLoad());
        return harMetaData;
    }
}
