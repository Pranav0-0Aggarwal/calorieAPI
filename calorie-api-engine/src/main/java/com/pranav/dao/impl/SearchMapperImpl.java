package com.pranav.dao.impl;


import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;
import com.pranav.dao.SearchMapper;

public class SearchMapperImpl implements SearchMapper {
    private final AerospikeClient client;
    private final String namespace = "calorieDB";
    private final String set = "searchMappings";

    public SearchMapperImpl(AerospikeClient client) {
        this.client = client;
    }

    @Override
    public boolean isPresent(String foodName) {
        Key key = new Key(namespace, set, foodName);
        return client.exists(null, key);
    }

    @Override
    public void addMapping(String foodName, String foodId) {
        WritePolicy writePolicy = new WritePolicy();
        Key key = new Key(namespace, set, foodName);
        Bin bin = new Bin("foodId", foodId);

        client.put(writePolicy, key, bin);
    }

    @Override
    public String getMapping(String foodName) {
        Key key = new Key(namespace, set, foodName);
        Record record = client.get(null, key);
        return (record != null) ? record.getString("foodId") : null;
    }
}
