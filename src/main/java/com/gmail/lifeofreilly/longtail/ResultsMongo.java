package com.gmail.lifeofreilly.longtail;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.mongodb.DBCollection;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.net.UnknownHostException;

public class ResultsMongo implements Results {
    private final static Logger log = Logger.getLogger(ResultsMongo.class);
    private final String address;
    private final String name;
    private final String collection;
    private final int port;

    private DBCollection dbCollection;

    public ResultsMongo(final String address, final int port, final String name, final String collection) {
        this.address = address;
        this.port = port;
        this.name = name;
        this.collection = collection;
        createMongoClient();
    }

    private void createMongoClient() {
        try {
            MongoClient mongo = new MongoClient(address, port);
            DB db = mongo.getDB(name);
            dbCollection = db.getCollection(collection);
        } catch (UnknownHostException ex) {
            log.error("Error  creating mongo client:  " + ex);
        }
    }

    @Override
    public void addResult(JSONObject result) {
        DBObject dbObject = (DBObject) JSON.parse(result.toJSONString());
        dbCollection.insert(dbObject);
    }
}
