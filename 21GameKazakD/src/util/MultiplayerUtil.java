package util;

import bean.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Util class for multiplayer mode.
 */
public class MultiplayerUtil {

    private static Map<User, User> userMap = new HashMap<>();
    private static MultiplayerUtil ourInstance = new MultiplayerUtil();

    /**
     * Get instance.
     * @return MultiplayerUtil instance.
     */
    public static MultiplayerUtil getInstance() {
        return ourInstance;
    }

    private MultiplayerUtil() {
    }
    /**
     * Join player to the multiplayer.
     * @param user Added user.
     */
    public static void join(User user){
        for (Map.Entry<User,User> entry : userMap.entrySet()){
            if (!entry.getKey().equals(user) && entry.getValue() == null){
                entry.setValue(user);
                return;
            }
        }
        userMap.put(user,null);
        return;
    }
    /**
     * @param firstPlayer First player.
     * @return Second player.
     */
    public static User getSecondPlayer(User firstPlayer){
        for (Map.Entry<User,User> entry : userMap.entrySet()){
            if (entry.getKey().equals(firstPlayer)){
                return entry.getValue();
            } else if (entry.getValue().equals(firstPlayer)){
                return entry.getKey();
            }
        }
        return null;

    }
    /**
     * Clear game for the user.
     * @param user Current user.
     */
    public static void deleteUserGame(User user){
        if (userMap.containsValue(user)){
            userMap.remove(getSecondPlayer(user));
        } else if(userMap.containsKey(user)){
            userMap.remove(user);
        }
    }
}
