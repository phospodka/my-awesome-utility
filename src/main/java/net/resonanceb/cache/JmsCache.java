package net.resonanceb.cache;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.resonanceb.util.FileUtil;

public class JmsCache {

    private static final Object lock = new Object();

    private static ConcurrentMap<String, Deque<JSONObject>> map = new ConcurrentHashMap<>();

    /**
     * Adds a {@link org.json.JSONObject} to a cache like collection of jms messages.  It is limited by the <code>jms.cache.max.size</code>
     * property per topic it has received messages from.
     * 
     * @param json {@link org.json.JSONObject} to add to the collection
     * @throws java.io.IOException
     * @throws org.json.JSONException
     * @throws NumberFormatException
     */
    public static void add(JSONObject json) throws IOException, JSONException, NumberFormatException {
        String key = json.getString("destination");

        // synchronized on map access to prevent simultaneous access in clear / and get cache methods
        synchronized(lock) {
            Deque<JSONObject> queue = map.get(key);

            // if we didn't find the queue, make a new one
            if (queue == null) {
                queue = new ArrayDeque<>();  // maybe use LinkedList instead of ArrayDeque
            }

            // if the size is too big, shrink it
            if (queue.size() > Long.parseLong(FileUtil.getProperty("jms.cache.max.size"))) {
                queue.pollLast();
            }

            // add the message to the queue
            queue.push(json);

            // add the queue to the cache
            map.putIfAbsent(key, queue);
        }
    }

    /**
     * Clear the cache of all data
     */
    public static void clear() {
        // synchronized on map access to prevent simultaneous access in add / and get cache methods
        synchronized(lock) {
            map = new ConcurrentHashMap<>();
        }
    }

    /**
     * Converts the internal cache collection to a {@link org.json.JSONObject}
     * 
     * @return the cache as a fully constructed {@link org.json.JSONObject}
     * @throws org.json.JSONException
     */
    public static JSONObject getCacheAsJson() throws JSONException {
        JSONObject json = new JSONObject();

        // synchronized on map access to prevent simultaneous access in clear / and add methods
        synchronized(lock) {

            // iterate over each queue and convert it to json
            for (String key : map.keySet()) {
                Deque<JSONObject> queue = map.get(key);
                JSONArray array = new JSONArray(queue);
                json.put(key, array);
            }
        }

        return json;
    }
}