package com.techexplore.autosuggest.framework.searchalgorithm;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
public class TrieNode {


    private Map<Character, TrieNode> children = new HashMap<>();

    private boolean isTerminated = false;

    private char character;

    private char displayCharacter;

    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public TrieNode(char character) {
        this.character = character;
    }

    public TrieNode() {
    }

    public TrieNode(Map<Character, TrieNode> children) {
        this.children = children;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public char getCharacter() {
        return character;
    }

    public void addWord(final String word) {
        if (StringUtils.isBlank(word))
            return;

        final String lowerCase = word.toLowerCase();
        char firstChar = lowerCase.charAt(0);

        TrieNode nodeOfFirstChar = getChildNode(firstChar);
        if (Objects.isNull(nodeOfFirstChar)) {
            nodeOfFirstChar = new TrieNode(firstChar);
            nodeOfFirstChar.setDisplayCharacter(word.charAt(0));
            children.put(firstChar, nodeOfFirstChar);
        }

        if (lowerCase.length() > 1) {
            nodeOfFirstChar.addWord(word.substring(1));
        } else {
            nodeOfFirstChar.setTerminated(true);
        }

    }

    public void insert(final String word) {
        if (StringUtils.isBlank(word))
            return;

        final String lowerCase = word.toLowerCase();
        TrieNode node = this;
        for (char c : lowerCase.toCharArray()) {
            if (node.getChildNode(c) == null) {
                node.getChildren().put(c, new TrieNode(c));
            }
            node = node.getChildNode(c);

        }
            node.setTerminated(true);
            node.setWord(word);

        }

    public char getDisplayCharacter() {
        return displayCharacter;
    }

    public void setDisplayCharacter(char displayCharacter) {
        this.displayCharacter = displayCharacter;
    }

    public TrieNode getChildNode(char c) {
        return children.get(c);
    }
}
