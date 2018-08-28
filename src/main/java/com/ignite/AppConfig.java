package com.ignite;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import com.ignite.model.Student;

/**
 * https://apacheignite.readme.io/docs/java-thin-client-quick-start
 * 
 * @author jcamargos
 *
 */
public class AppConfig {

	public static void main(String[] args) {
		ClientConfiguration cfg = new ClientConfiguration().setAddresses("127.0.0.1:10800");

		try (IgniteClient igniteClient = Ignition.startClient(cfg)) {
			System.out.println();
			System.out.println(">>> Thin client put-get example started.");

			final String CACHE_NAME = "StudentCache";

			ClientCache<Integer, Student> cache = igniteClient.getOrCreateCache(CACHE_NAME);

			System.out.format(">>> Created cache [%s].\n", CACHE_NAME);

			Integer key = 1;
			Student val = new Student();
			val.setId(key.longValue());
			val.setName("Mime");
			val.setAvg(4.8);

			cache.put(key, val);

			System.out.format(">>> Saved [%s] in the cache.\n", val);

			Student cachedVal = cache.get(key);

			System.out.format(">>> Loaded [%s] from the cache.\n", cachedVal);
			System.out.println("\n\n Values on cache [Name]: " + cache.getName());
		} catch (ClientException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.format("Unexpected failure: %s\n", e);
		}
	}
}