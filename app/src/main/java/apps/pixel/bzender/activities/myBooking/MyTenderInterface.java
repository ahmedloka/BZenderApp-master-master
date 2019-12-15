package apps.pixel.bzender.activities.myBooking;

import java.util.List;

import apps.pixel.bzender.Models.my_tenders.MyBookingBody;
import apps.pixel.bzender.Models.my_tenders.MyTendersBody;

public interface MyTenderInterface {
    void getMyooking(MyBookingBody myBookingBody);

    void getMyTender(List<MyTendersBody> myTendersBodyList);


}
