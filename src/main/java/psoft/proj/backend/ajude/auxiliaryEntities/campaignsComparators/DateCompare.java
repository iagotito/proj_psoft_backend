package psoft.proj.backend.ajude.auxiliaryEntities.campaignsComparators;

import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateCompare implements Comparator<Campaign> {

    public int compare(Campaign c1, Campaign c2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date date1;
        Date date2;

        try {
            date1 = sdf.parse(c1.getDate());
            date2 = sdf.parse(c2.getDate());
            return date2.compareTo(date1);
        } catch (ParseException e) {
            return 0;
        }
    }
}