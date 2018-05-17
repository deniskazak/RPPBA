package bean;


import java.io.Serializable;
import java.util.Date;

/**
 * Message between two users.
 */
public class Message implements Serializable {

    private int id;
    private int sender_id;
    private int dest_id;
    private String message;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getDest_id() {
        return dest_id;
    }

    public void setDest_id(int dest_id) {
        this.dest_id = dest_id;
    }
    /**
     * @return Content of the message.
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message  Content of the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @return  Date of the sent message.
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date  Date of the sent message.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (id != message1.id) return false;
        return message != null ? message.equals(message1.message) : message1.message == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
