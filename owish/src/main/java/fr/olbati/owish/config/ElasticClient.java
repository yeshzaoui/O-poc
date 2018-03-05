package fr.olbati.owish.config;


import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElasticClient {

    public Client createElasticClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "docker-cluster").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("elasticsearch"), 9300));
        return client;
    }

    public Client getClient() throws UnknownHostException {
        return createElasticClient();
    }

}
