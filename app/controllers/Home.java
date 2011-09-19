package controllers;

import play.*;
import play.libs.Codec;
import play.mvc.*;

import java.util.*;

import controllers.Secure.Security;

import models.*;

public class Home extends Controller {

    public static void home() {
        if(Security.isConnected()) {
        	User user = User.findByEmail(Security.connected());
        	String gravatar = "http://www.gravatar.com/avatar/" + Codec.hexMD5(user.email) + "?s=20";
        	render("Home/connected.html", user, gravatar);
        } else {
        	render("Home/anonymous.html");
        }
    }

	

}