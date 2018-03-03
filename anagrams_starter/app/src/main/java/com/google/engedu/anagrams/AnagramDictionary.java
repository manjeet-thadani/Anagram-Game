/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;

        wordList = new ArrayList<>();
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();

        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordList.add(word);

            String sortedWord = sortLetters(word);
            ArrayList<String> temp = new ArrayList<>();

            if (lettersToWord.containsKey(sortedWord)){
                temp = lettersToWord.get(sortedWord);
            }

            temp.add(word);

            lettersToWord.put(sortedWord, temp);
        }
    }

    public boolean isGoodWord(String word, String base) {

        if (wordList.contains(word) && !word.contains(base)){
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for (String dict : wordList){
            if (dict.length() == word.length()){
                if (sortLetters(dict).equals(sortLetters(word))){
                    result.add(dict);
                }
            }
        }

        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for (char i = 'a'; i <= 'z'; i++){
            String newString = word + i;
            String sortedString = sortLetters(newString);

            if (lettersToWord.containsKey(sortedString)){
                ArrayList<String> temp = lettersToWord.get(sortedString);

                for (String t : temp){
                    result.add(t);
                }
            }
        }


        return result;
    }

    public String pickGoodStarterWord() {

        return "stop";
    }

    public String sortLetters(String word){
        char[] result = word.toCharArray();

        for (int i = 0; i < word.length(); i++){
            for (int j = 0; j < word.length() - i - 1; j++){
                if (result[j] > result[j + 1]){
                    //swap
                    char temp = result[j];
                    result[j] = result[j+1];
                    result[j+1] = temp;
                }
            }
        }

        return new String(result);
    }
}
