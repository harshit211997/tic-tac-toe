package com.example.apple.tic_tac_toe;

public class Highscores {

    private int _score;
    private String _name;

    public Highscores(int _score, String _name) {
        this._score = _score;
        this._name = _name;
    }

    public int get_score() {
        return _score;
    }

    public String get_name() {
        return _name;
    }

    public void set_highscore(int _score) {
        this._score = _score;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
