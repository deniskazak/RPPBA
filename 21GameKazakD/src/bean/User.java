package bean;


import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String nickname;
    private String email;
    private String password;
    private int money;
    private int rate;
    private boolean isBanned;
    private boolean isAdmin;
    private Game currentGame;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Nickname of the user.
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * @param nickname  Nickname of the user.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    /**
     * @return Email of the user.
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email  Email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return Password of the user.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password  Password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return Cash of the user.
     */
    public int getMoney() {
        return money;
    }
    /**
     * @param money Cash of the user.
     */
    public void setMoney(int money) {
        this.money = money;
    }
    /**
     * @return Rating of the user.
     */
    public int getRate() {
        return rate;
    }
    /**
     * @param rate Rating of the user.
     */
    public void setRate(int rate) {
        this.rate = rate;
    }
    /**
     * @return Either user is banned.
     */
    public boolean isBanned() {
        return isBanned;
    }
    /**
     * @param banned Ban of the user.
     */
    public void setBanned(boolean banned) {
        isBanned = banned;
    }
    /**
     * @return Either user is admin.
     */
    public boolean isAdmin() {
        return isAdmin;
    }
    /**
     * @param admin Admin access.
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    /**
     * @return Current game.
     */
    public Game getCurrentGame() {
        return currentGame;
    }
    /**
     * @param currentGame Current game.
     */
    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
