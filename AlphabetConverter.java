import java.util.Scanner;
import java.util.HashMap;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.security.InvalidKeyException;

public class AlphabetConverter {

    public static void main(String[] args) {
        HashMap<Character, String> symbols = new HashMap<>();

        symbols.put('A', "@");
        symbols.put('B', "@~");
        symbols.put('C', "@~~");
        symbols.put('D', "@~~~");
        symbols.put('E', "~");
        symbols.put('F', "~@");
        symbols.put('G', "~@@");
        symbols.put('H', "~@@@");
        symbols.put('I', "#");
        symbols.put('J', "#!");
        symbols.put('K', "#!!");
        symbols.put('L', "#!!!");
        symbols.put('M', "!");
        symbols.put('N', "!#");
        symbols.put('O', "!##");
        symbols.put('P', "!###");
        symbols.put('Q', "$");
        symbols.put('R', "$%");
        symbols.put('S', "$%%");
        symbols.put('T', "$%%%");
        symbols.put('U', "%");
        symbols.put('V', "%$");
        symbols.put('W', "%$$");
        symbols.put('X', "%$$$");
        symbols.put('Y', "^");
        symbols.put('Z', "^&");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter English String: ");
        String plaintext = scanner.nextLine();

        String symbolText = convertToSymbols(plaintext.toUpperCase(), symbols);
        System.out.println("Space Person String: " + symbolText);

        System.out.print("Enter Shift Value (Integer): ");
        int shiftValue = scanner.nextInt();

        String encryptedText = encrypt(plaintext.toUpperCase(), shiftValue);
        System.out.println("Encrypted Text: " + encryptedText);

        try {
            String secretKey = "Salem";
            String spaceLangHash = calculateHMAC(symbolText, secretKey);
            System.out.println("Hash Value of Space Person String: " + spaceLangHash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    public static String encrypt(String plaintext, int shift) {
        StringBuilder encryptedText = new StringBuilder();

        for (char character : plaintext.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int originalAlphabetPosition = character - base;
                int newAlphabetPosition = (originalAlphabetPosition + shift) % 26;
                char newCharacter = (char) (base + newAlphabetPosition);

                encryptedText.append(newCharacter);
            } else {
                encryptedText.append(character);
            }
        }

        return encryptedText.toString();
    }

    public static String convertToSymbols(String plaintext, HashMap<Character, String> symbols) {
        StringBuilder spaceLanguage = new StringBuilder();

        for (char character : plaintext.toCharArray()) {
            if (symbols.containsKey(character)) {
                spaceLanguage.append(symbols.get(character));
            } else {
                spaceLanguage.append(character);
            }
        }
        return spaceLanguage.toString();
    }

    public static String calculateHMAC(String symbolText, String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), algorithm);
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(symbolText.getBytes());
        return Base64.getEncoder().encodeToString(hmacBytes);
    }
}