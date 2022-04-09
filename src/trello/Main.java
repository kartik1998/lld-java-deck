package trello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * @link: https://workat.tech/machine-coding/practice/trello-problem-t0nwwqt61buz
 */

enum Privacy {
    PUBLIC, PRIVATE
}

class Board {
    private final String id = UUID.randomUUID().toString();
    private String name, url;
    private Privacy privacy = Privacy.PUBLIC;
    private ArrayList<User> members;
    private ArrayList<List> lists;

    public Board(String name, String url, Privacy privacy, ArrayList<User> members, ArrayList<List> lists) {
        this.name = name;
        this.url = url;
        this.privacy = privacy;
        this.members = members;
        this.lists = lists;
    }

    public Board(String name, String url, ArrayList<User> members, ArrayList<List> lists) {
        this.name = name;
        this.url = url;
        this.members = members;
        this.lists = lists;
    }

    public Board(String name, String url, Privacy privacy) {
        this.name = name;
        this.url = url;
        this.privacy = privacy;
        this.members = new ArrayList<>();
        this.lists = new ArrayList<>();
    }

    public Board(String name, String url) {
        this.name = name;
        this.url = url;
        this.members = new ArrayList<>();
        this.lists = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public String getId() {
        return id;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<List> getLists() {
        return lists;
    }

    public boolean addList(List list) {
        for (List l : this.lists) {
            if (l.getId().equals(list.getId())) return false;
        }
        return this.lists.add(list);
    }

    public boolean addMember(User user) {
        for (User u : this.getMembers()) {
            if (u.getId().equals(user.getId())) return false;
        }
        this.members.add(user);
        return true;
    }

    public boolean removeMember(User user) {
        return this.removeMemberViaId(user.getId());
    }

    public boolean removeMemberViaId(String id) {
        ArrayList<User> temp = new ArrayList<>();
        boolean flag = false;
        for (User u : this.getMembers()) {
            if (u.getId().equals(id)) {
                flag = true;
            } else temp.add(u);
        }
        this.members = new ArrayList<>(temp);
        return flag;
    }
}

class List {
    private final String id = UUID.randomUUID().toString();
    private String name;
    private HashMap<String, Card> cardsMap;

    public List(String name) {
        this.name = name;
        this.cardsMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public boolean addCard(Card card) {
        if (cardsMap.containsKey(card.getId())) return false;
        cardsMap.put(card.getId(), card);
        return true;
    }

    public Card getCard(String id) {
        return cardsMap.getOrDefault(id, null);
    }
}

class Card {
    private final String id = UUID.randomUUID().toString();
    private String name, description;
    private User assignee;

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getId() {
        return this.id;
    }
}

class User {
    private final String id = UUID.randomUUID().toString();
    private String name, email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class Trello {
    private static ArrayList<Board> boards = new ArrayList<>();

    public static Board createBoard(String name, String url, Privacy privacy) {
        Board board = new Board(name, url, privacy);
        boards.add(board);
        return board;
    }

    public static Board createBoard(String name, String url) {
        Board board = new Board(name, url);
        boards.add(board);
        return board;
    }
}

public class Main {
    public static void main(String[] args) {
        Board b1 = Trello.createBoard("todo-list", "https://trello-board.com");
        User u1 = new User("name", "name@gmail.com");
        User u2 = new User("name1", "name1@gmail.com");
        List l1 = new List("todo-list");
        Card c1 = new Card("chore home", "get groceries");
        Card c11 = new Card("chore work", "get laptop from work");
        l1.addCard(c1);
        l1.addCard(c11);
        b1.addMember(u1);
        b1.addMember(u2);
        b1.addList(l1);
        System.out.println(b1);
    }
}
