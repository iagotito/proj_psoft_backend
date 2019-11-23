package psoft.proj.backend.ajude.auxiliaryEntities.campaignsComparators;

import psoft.proj.backend.ajude.campaigns.entities.Campaign;

import java.util.Comparator;

public class DonationsCompare implements Comparator<Campaign> {

    public int compare(Campaign c1, Campaign c2) {
        if ((c1.getGoal() - c1.getDonations()) > (c2.getGoal() - c2.getDonations()))
            return 1;
         else if ((c1.getGoal() - c1.getDonations()) < (c2.getGoal() - c2.getDonations()))
            return -1;
         else
            return 0;
    }

}
