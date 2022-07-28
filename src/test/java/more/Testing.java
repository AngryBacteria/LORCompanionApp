// FIXME (due1) Package eingeführt
package more;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.card.CardService;

/**
 * Test Class for small things I want to test from time to time.
 */
public class Testing {

	// FIXME (due1) Keine Testklasse, sollte also nicht im Bereich
	// 'src/test/java' sein...
	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(4);
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		CardService cardService = new CardService(true);
		cardService.writeCardsToJson();


		String fileName = "C:\\Users\\nicog\\Downloads\\lines.txt";

		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			List<String> list = stream.collect(Collectors.toList());
			System.out.println(list);
			list.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}

		executor.shutdown();
	}

}
