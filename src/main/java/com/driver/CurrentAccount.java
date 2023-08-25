package com.driver;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class CurrentAccount extends BankAccount {
    public class Pair {
        char ch;
        int freq;

        Pair(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

    }

    String tradeLicenseId; //consists of Uppercase English characters only

    public CurrentAccount(String name, double balance, String tradeLicenseId) throws Exception {
        // minimum balance is 5000 by default. If balance is less than 5000, throw "Insufficient Balance" exception
        super(name, balance, 5000);
        this.tradeLicenseId = tradeLicenseId;
        if (balance < 5000) throw new Exception("Insufficient Balance");

    }

    public String getTradeLicenseId() {
        return tradeLicenseId;
    }

    public void validateLicenseId() throws Exception {
        // A trade license Id is said to be valid if no two consecutive characters are same
        // If the license Id is valid, do nothing
        // If the characters of the license Id can be rearranged to create any valid license Id
        // If it is not possible, throw "Valid License can not be generated" Exception
        String s = this.tradeLicenseId;
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> b.freq - a.freq);

        for (Map.Entry<Character, Integer> e : map.entrySet()) {
            pq.add(new Pair(e.getKey(), e.getValue()));
        }

        Pair prev = new Pair('#', -1);

        StringBuilder sb = new StringBuilder();

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            sb.append(curr.ch);
            curr.freq--;

            if (prev.freq > 0) {
                pq.add(prev);
            }

            prev = curr;
        }

        if (sb.length() != s.length()) throw new Exception("Valid License can not be generated");
        this.tradeLicenseId = sb.toString();
//            return sb.toString();

    }
}
