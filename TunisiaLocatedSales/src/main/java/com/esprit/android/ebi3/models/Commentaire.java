package com.esprit.android.ebi3.models;

/**
 * Created by Hassan on 10/02/16.
 */
public class Commentaire {
    public String Commentor;
    public String Commentairetxt;
    public int CommentorImg;

    public Commentaire(String commentor, String commentairetxt, int commentorImg) {
        Commentor = commentor;
        Commentairetxt = commentairetxt;
        CommentorImg = commentorImg;
    }

    public String getCommentor() {
        return Commentor;
    }

    public void setCommentor(String commentor) {
        Commentor = commentor;
    }

    public String getCommentairetxt() {
        return Commentairetxt;
    }

    public void setCommentairetxt(String commentairetxt) {
        Commentairetxt = commentairetxt;
    }

    public int getCommentorImg() {
        return CommentorImg;
    }

    public void setCommentorImg(int commentorImg) {
        CommentorImg = commentorImg;
    }
}
