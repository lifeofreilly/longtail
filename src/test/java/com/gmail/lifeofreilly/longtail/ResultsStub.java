package com.gmail.lifeofreilly.longtail;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

public class ResultsStub implements Results {

    private final static Logger log = Logger.getLogger(ResultsMongo.class);

    @Override
    public void addResult(JSONObject result) {
        log.info("Test Stub addResult() method called.");
    }
}
