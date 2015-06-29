package it.comar.admin.homestroragecp.cassetto;

/**
 * Created by admin on 18/06/2015.
 */
public class ObjectItem {
    public int getID() {
        return ID;
    }

    public void setID(int ID) { this.ID = ID;  }

    public String getTag() {  return Tag; }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getState() { return State;  }

    public void setState(int stato) {
        State = stato;
    }

    public int getMyDrawer() { return MyDrawer; }

    public void setMyDrawer(int myDrawer) { MyDrawer = myDrawer; }

    private int ID;
    private String Tag;
    private String Url;
    private int State;
    private int MyDrawer;

    public ObjectItem(int myDrawer) {
        MyDrawer = myDrawer;
    }

    public ObjectItem(String tag) {
        Tag = tag;
    }

    public ObjectItem(int ID, String tag) {
        this.ID = ID;
        Tag = tag;
    }
}
