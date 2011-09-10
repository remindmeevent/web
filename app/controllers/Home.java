package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import controllers.Secure.Security;

import models.*;

public class Home extends Controller {

    public static void home() {
        if(Security.isConnected()) {
        	render("Home/connected.html");
        } else {
        	render("Home/anonymous.html");
        }
    }

	

}