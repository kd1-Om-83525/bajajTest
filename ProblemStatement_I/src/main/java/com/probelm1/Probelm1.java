package com.probelm1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;

public class Probelm1 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar DestinationHashGenerator.jar <PRN> <JSON file path>");
            return;
        }

        String prn = args[0].toLowerCase();
        String jsonFilePath = args[1];

        try {
            String destinationValue = findDestinationValue(jsonFilePath);
            if (destinationValue == null) {
                System.out.println("Destination key not found in the JSON file.");
                return;
            }

            String randomString = generateRandomString(8);
            String concatenatedString = prn + destinationValue + randomString;
            String hash = generateHash(concatenatedString);

            System.out.println(hash + ";" + randomString);
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String findDestinationValue(String jsonFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));

        return traverseJson(rootNode);
    }

    private static String traverseJson(JsonNode node) {
        if (node.isObject()) {
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode childNode = node.get(fieldName);
                if (fieldName.equals("destination")) {
                    return childNode.asText();
                }
                String result = traverseJson(childNode);
                if (result != null) return result;
            }
        } else if (node.isArray()) {
            for (JsonNode arrayElement : node) {
                String result = traverseJson(arrayElement);
                if (result != null) return result;
            }
        }
        return null;
    }

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private static String generateHash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
