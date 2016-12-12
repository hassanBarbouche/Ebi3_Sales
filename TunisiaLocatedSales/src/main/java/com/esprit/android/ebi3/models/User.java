package com.esprit.android.ebi3.models;

import java.io.Serializable;

/**
 * Created by Hassan on 09/02/16.
 */
public class User implements Serializable{
    public int id;
    public String UserName;
    public String UserDescription;
    public String UserMetier;
    public String UserVille;
    public String UserAdresse;
    public String UserTelephone;
    public String UserMail;
    public int UserImg;
    public String ImagePath;
    public int Rate;
    public int Ratedby;

    public User(int id, String userName, String userDescription, String userMetier, String userVille, String userAdresse, String userTelephone, String userMail, String imagePath,int rate) {
        this.id = id;
        UserName = userName;
        UserDescription = userDescription;
        UserMetier = userMetier;
        UserVille = userVille;
        UserAdresse = userAdresse;
        UserTelephone = userTelephone;
        UserMail = userMail;
        ImagePath = imagePath;
        Rate = rate;
    }

    public User(int id, String userName, String userDescription, String userMetier, String userVille, String userAdresse, String userTelephone, String userMail, String imagePath,int rate,int ratedby) {
        this.id = id;
        UserName = userName;
        UserDescription = userDescription;
        UserMetier = userMetier;
        UserVille = userVille;
        UserAdresse = userAdresse;
        UserTelephone = userTelephone;
        UserMail = userMail;
        ImagePath = imagePath;
        Rate = rate;
        Ratedby = ratedby;
    }

    public User(int id, String userName, String userDescription, String userMetier, String userVille, String userAdresse, String userTelephone, String userMail, String imagePath) {
        this.id = id;
        UserName = userName;
        UserDescription = userDescription;
        UserMetier = userMetier;
        UserVille = userVille;
        UserAdresse = userAdresse;
        UserTelephone = userTelephone;
        UserMail = userMail;
        ImagePath = imagePath;

    }

    public User(String userName, String userDescription, String userMetier, String userVille, String userAdresse, String userTelephone, String userMail, String imagePath) {
        UserName = userName;
        UserDescription = userDescription;
        UserMetier = userMetier;
        UserVille = userVille;
        UserAdresse = userAdresse;
        UserTelephone = userTelephone;
        UserMail = userMail;
        ImagePath = imagePath;
    }

    public User(String userName, String userDescription, String userMetier, String userVille, String userAdresse, String userTelephone, String userMail, int userImg) {
        UserName = userName;
        UserDescription = userDescription;
        UserMetier = userMetier;
        UserVille = userVille;
        UserAdresse = userAdresse;
        UserTelephone = userTelephone;
        UserMail = userMail;
        UserImg = userImg;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserDescription() {
        return UserDescription;
    }

    public String getUserMetier() {
        return UserMetier;
    }

    public String getUserVille() {
        return UserVille;
    }

    public String getUserAdresse() {
        return UserAdresse;
    }

    public String getUserTelephone() {
        return UserTelephone;
    }

    public String getUserMail() {
        return UserMail;
    }

    public int getUserImg() {
        return UserImg;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserDescription(String userDescription) {
        UserDescription = userDescription;
    }

    public void setUserMetier(String userMetier) {
        UserMetier = userMetier;
    }

    public void setUserVille(String userVille) {
        UserVille = userVille;
    }

    public void setUserAdresse(String userAdresse) {
        UserAdresse = userAdresse;
    }

    public void setUserTelephone(String userTelephone) {
        UserTelephone = userTelephone;
    }

    public void setUserMail(String userMail) {
        UserMail = userMail;
    }

    public void setUserImg(int userImg) {
        UserImg = userImg;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public int getRatedby() {
        return Ratedby;
    }

    public void setRatedby(int ratedby) {
        Ratedby = ratedby;
    }
}
