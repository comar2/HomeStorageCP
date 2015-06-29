package it.comar.admin.homestroragecp.cassetto;

/**
 * Created by admin on 19/06/2015.
 */
public class DrawerItem {
    private int position;
    private final String name;
//    private final String imgUrl;

    public DrawerItem(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
