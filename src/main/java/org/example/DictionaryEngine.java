package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryEngine {
    private final HashValidator validator;
    private final String dictionaryPath;
    private int attempts;

    public DictionaryEngine(HashValidator validator, String dictionaryPath) {
        this.validator = validator;
        this.dictionaryPath = dictionaryPath;
    }

    public String crackPassword() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dictionaryPath));

            label35: {
                String var3;
                try {
                    String password;
                    do {
                        if ((password = reader.readLine()) == null) {
                            break label35;
                        }

                        ++this.attempts;
                        System.out.println("Attempt: [" + this.attempts + "] Trying: " + password);
                    } while(!this.validator.validate(password.trim()));

                    var3 = password.trim();
                } catch (Throwable var5) {
                    try {
                        reader.close();
                    } catch (Throwable var4) {
                        var5.addSuppressed(var4);
                    }

                    throw var5;
                }

                reader.close();
                return var3;
            }

            reader.close();
        } catch (IOException var6) {
            System.out.println("Error reading dictionary file: " + var6.getMessage());
        }

        return null;
    }
}
