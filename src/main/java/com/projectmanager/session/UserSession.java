package com.projectmanager.session;
import com.projectmanager.models.entity.User;

public class UserSession {
    
    private static User currentUser;

   private UserSession() {}
   
   public static void set(User user) {
    currentUser = user;
   }

   public static User get() {
    return currentUser;
   }

   public static void clear() {
    currentUser =  null;
   }

   public static boolean isAdmin(){
    return currentUser != null && "admin".equals(currentUser.role);
   }
}
