package login;

/**
 * Created by migueltorresporta on 9/4/16.
 */
public class User {

    private String name;
    private String pass;

    public User (String name, String pass){

        this.name = name;
        this.pass = pass;

    }

    public String getName(){
        return this.name;
    }

    public String getPass (){
        return this.pass;
    }
}
