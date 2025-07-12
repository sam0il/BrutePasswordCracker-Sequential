//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.IOException;
import java.util.Scanner;

public class BruteForceEngine {
    private final HashValidator validator;
    private final String charSet;
    private final int maxLength;
    private int attempts;
    private final int[] maskIndices;
    private final String mask;
    private boolean stopRequested = false;
    private final Scanner scanner;

    public BruteForceEngine(HashValidator validator, String charSet, int maxLength, String mask, String maskSpot) {
        this.validator = validator;
        this.charSet = charSet;
        this.maxLength = maxLength;
        this.mask = mask;
        this.scanner = new Scanner(System.in);
        if (maskSpot != null && !maskSpot.trim().isEmpty()) {
            String[] indices = maskSpot.split(",");
            this.maskIndices = new int[indices.length];

            for(int i = 0; i < indices.length; ++i) {
                this.maskIndices[i] = Integer.parseInt(indices[i].trim());
            }
        } else {
            this.maskIndices = new int[0];
        }

    }

    public String crackPassword() {
        for(int length = 1; length <= this.maxLength; ++length) {
            if (this.stopRequested) {
                System.out.println("Brute force stopped by user.");
                break;
            }

            if (length >= this.mask.length()) {
                String result = this.generateAndValidate(new char[length], 0, length);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    private String generateAndValidate(char[] currentPassword, int position, int remainingLength) {
        if (this.stopRequested) {
            return null;
        } else {
            if (this.attempts % 1000 == 0) {
                this.Stop(this.scanner);
            }

            if (remainingLength == 0) {
                String password = new String(currentPassword);
                ++this.attempts;
                if (attempts % 1000 == 0) {
                    System.out.println("Attempt: [" + this.attempts + "] Trying: " + password);
                }
                return this.validator.validate(password) ? password : null;
            } else {
                int[] var4 = this.maskIndices;
                int var5 = var4.length;

                int var6;
                for(var6 = 0; var6 < var5; ++var6) {
                    int index = var4[var6];
                    if (position == index) {
                        currentPassword[position] = this.mask.charAt(this.findMaskIndex(position));
                        return this.generateAndValidate(currentPassword, position + 1, remainingLength - 1);
                    }
                }

                char[] var9 = this.charSet.toCharArray();
                var5 = var9.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    char c = var9[var6];
                    if (this.stopRequested) {
                        return null;
                    }

                    currentPassword[position] = c;
                    String result = this.generateAndValidate(currentPassword, position + 1, remainingLength - 1);
                    if (result != null) {
                        return result;
                    }
                }

                return null;
            }
        }
    }

    private int findMaskIndex(int position) {
        for(int i = 0; i < this.maskIndices.length; i++) {
            if (this.maskIndices[i] == position) {
                return i;
            }
        }

        return -1;
    }

    private void Stop(Scanner scanner) {
        try {
            if (System.in.available() > 0) {
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    this.stopRequested = true;
                }
            }

        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }
}
