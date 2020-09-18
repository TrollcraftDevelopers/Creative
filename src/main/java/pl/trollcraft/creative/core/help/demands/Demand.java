package pl.trollcraft.creative.core.help.demands;

import java.util.Random;

public class Demand {

    /**
     * Numerical id of the demand.
     */
    private int id;

    /**
     * Grouping tag.
     */
    private String tag;

    /**
     * Who is demanding.
     */
    private String who;

    /**
     * Whom is the demand directed to.
     */
    private String to;

    /**
     * Handles player accepting
     * and declining the request.
     */
    private DemandHandler action;

    /**
     * Time when the demand was created.
     */
    private long creationTime;

    /**
     * @param who creator of the demand.
     * @param to demand target.
     * @param action handles the states of demand.
     */
    public Demand(String tag, String who, String to, DemandHandler action) {
        id = new Random().nextInt();
        this.tag = tag;
        this.who = who;
        this.to = to;
        this.action = action;
        creationTime = System.currentTimeMillis();
    }

    /**
     * @return id numerical demand id.
     */
    public int getId() {
        return id;
    }

    /**
     * @return tag grouping tag.
     */
    public String getTag() {
        return tag;
    }

    /**
     * @return who is demanding
     */
    public String getWho() {
        return who;
    }

    /**
     * @return target of the demand.
     */
    public String getTo() {
        return to;
    }

    /**
     * @return action to handle the demand states.
     */
    public DemandHandler getAction() {
        return action;
    }

    /**
     * @return creation time of the demand.
     */
    public long getCreationTime() {
        return creationTime;
    }

}
