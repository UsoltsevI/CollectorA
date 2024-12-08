package org.example.collectora.pinterest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Hashtable;
import java.util.Set;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;

import org.example.collectora.database.HBase;
import org.example.collectora.database.Row;
import org.example.collectora.database.ColumnFamily;

public class PinDatabase {
    private static final String configFileName = "config/hbase-connection.json";
    private static final Logger LOGGER = LoggerFactory.getLogger(PinDatabase.class);
    private static final byte[] TABLE_NAME = "pins".getBytes();
    private static final byte[] META_CF = "meta".getBytes();
    private static final byte[] PINNER_CF = "pinner".getBytes();
    private final HBase database = new HBase();

    public void connect() throws IOException {
        database.connect(loadProperties());
    }

    private Map<String,String> loadProperties() throws IOException {
        Map<String,String> properties = new Hashtable<>();
        try (Reader reader = new FileReader(configFileName)) {
            JsonObject json = new Gson().fromJson(reader, JsonObject.class);
            Set<Map.Entry<String,JsonElement>> entries = json.entrySet();
            for (Map.Entry<String,JsonElement> entry : entries) {
                properties.put(entry.getKey(), entry.getValue().getAsString());
            }
        } catch (JsonSyntaxException e) {
            LOGGER.info(e.getMessage());
        }
        return properties;
    }

    public void savePin(Pin pin) {
        Row row = new Row(pin.getId().getBytes());
        Map<byte[],ColumnFamily> columnFamilies = row.getColumnFamilies();
        columnFamilies.put(META_CF, pin.getMeta().toColumnFamily(META_CF));
        database.put(TABLE_NAME, row);
    }
}
