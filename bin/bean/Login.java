public class Login {
    private int id;
    private String password;
    private String message;

    public Login(int id, String password){
        this.id = id;
        this.password = password;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String msg){
        this.message = msg;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    }