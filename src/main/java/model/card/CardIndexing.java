package model.card;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The CardIndexing Class gets all Important information out of the Official Json files of the Game. It searches for
 * them in the Resources folder. From the Json Files it creates Card Objects. It can also search through the Json files
 * and extract the distinct values of a given Field.
 * A lot of these Methods are only used the first Time I imported all the Info from the original Json files.
 * I used this class to find out how many Regions there are exactly for example.
 */
public class CardIndexing {


    public static void main(String[] args) {

        getAllArrayFieldValues("subtypes").forEach(System.out::println);

    }

    public static Card[] getCardsArrayFromResourcesFile(String fileName){

        try(InputStream inputStream = CardIndexing.class.getClassLoader().getResourceAsStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Card[].class);
        }
        catch (IOException e) {
            System.out.println("Parsing didn't work");
            throw new IllegalStateException("Parsing cards didn't work");
        }
    }

    public static Card[] getCardsArrayFromFileAbsolute(String fileName){

        String filePath = "absolutepath";

        try(FileReader reader = new FileReader(filePath+fileName);
        BufferedReader bufferedReader = new BufferedReader(reader)) {
            Gson gson = new Gson();
            return gson.fromJson(bufferedReader, Card[].class);
        }
        catch (IOException e) {
            throw new IllegalStateException("Parsing cards didn't work");
        }
    }

    public static Card[] getCardsArrayFromURL(String url){

        try(InputStream is = new URL(url).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            Gson gson = new Gson();
            return gson.fromJson(bufferedReader, Card[].class);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Parsing cards didn't work");
        }
    }

    public static ArrayList<Card> getCardArrayFromArrays(){
        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set1-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set2-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set3-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set4-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set5-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromResourcesFile("set6-en_us.json")));
        return cardArray;
    }

    public static ArrayList<Card> getCardArrayFromArraysAbsolute(){
        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set1-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set2-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set3-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set4-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set5-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromFileAbsolute("set6-en_us.json")));
        return cardArray;
    }

    public static ArrayList<Card> getCardArrayFromURLs(){
        ArrayList<Card> cardArray = new ArrayList<>();
        cardArray.addAll(List.of(getCardsArrayFromURL("https://dd.b.pvp.net/latest/set1/en_us/data/set1-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromURL("https://dd.b.pvp.net/latest/set2/en_us/data/set2-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromURL("https://dd.b.pvp.net/latest/set3/en_us/data/set3-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromURL("https://dd.b.pvp.net/latest/set4/en_us/data/set4-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromURL("https://dd.b.pvp.net/latest/set5/en_us/data/set5-en_us.json")));
        cardArray.addAll(List.of(getCardsArrayFromURL("https://dd.b.pvp.net/latest/set6/en_us/data/set6-en_us.json")));
        return cardArray;
    }

    public static Set<String> getArrayFieldValues(String fileName, String fieldName){

        Set<String> outputSet = new HashSet<>();
        try(InputStream inputStream = CardIndexing.class.getClassLoader().getResourceAsStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)) {

            Gson gson = new Gson();
            JsonObject[] jsonObjects = gson.fromJson(reader, JsonObject[].class);

            for (JsonObject object : jsonObjects){

                for(JsonElement jsonElement : object.getAsJsonArray(fieldName)){
                    outputSet.add(jsonElement.getAsString());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Parsing didn't work");
        }
        catch (NullPointerException e) {
            System.out.println("Field [" + fieldName + "] doesnt exist ");
        }
        return outputSet;
    }

    public static Set<String> getFieldValues(String fileName, String fieldName){

        Set<String> outputSet = new HashSet<>();
        try(InputStream inputStream = CardIndexing.class.getClassLoader().getResourceAsStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)) {

            Gson gson = new Gson();
            JsonObject[] jsonObjects = gson.fromJson(reader, JsonObject[].class);

            for (JsonObject object : jsonObjects){
                outputSet.add(object.get(fieldName).getAsString());
            }
        }
        catch (IOException e) {
            System.out.println("Parsing didn't work");
        }
        catch (NullPointerException e) {
            System.out.println("Field [" + fieldName + "] doesnt exist ");
        }
        return outputSet;
    }

    public static List<String> getAllArrayFieldValues(String fieldName){

        Set<String> output = new HashSet<>();
        output.addAll(getArrayFieldValues("set1-en_us.json", fieldName));
        output.addAll(getArrayFieldValues("set2-en_us.json", fieldName));
        output.addAll(getArrayFieldValues("set3-en_us.json", fieldName));
        output.addAll(getArrayFieldValues("set4-en_us.json", fieldName));
        output.addAll(getArrayFieldValues("set5-en_us.json", fieldName));
        output.addAll(getArrayFieldValues("set6-en_us.json", fieldName));
        return output.stream().sorted().collect(Collectors.toList());
    }

    public static List<String> getAllFieldValues(String fieldName){

        Set<String> output = new HashSet<>();
        output.addAll(getFieldValues("set1-en_us.json", fieldName));
        output.addAll(getFieldValues("set2-en_us.json", fieldName));
        output.addAll(getFieldValues("set3-en_us.json", fieldName));
        output.addAll(getFieldValues("set4-en_us.json", fieldName));
        output.addAll(getFieldValues("set5-en_us.json", fieldName));
        output.addAll(getFieldValues("set6-en_us.json", fieldName));
        return output.stream().sorted().collect(Collectors.toList());
    }
}
