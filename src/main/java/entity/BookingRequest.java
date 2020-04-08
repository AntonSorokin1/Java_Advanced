package entity;

import java.util.Date;

public class BookingRequest {
    private String ad;
    private Date date;
    private String hotel;

    public BookingRequest() { }

    public BookingRequest(String ad, Date date, String hotel) {
        this.ad = ad;
        this.date = date;
        this.hotel = hotel;
    }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getHotel() { return hotel; }
    public void setHotel(String hotel) { this.hotel = hotel; }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "ad='" + ad + '\'' +
                ", date=" + date +
                ", hotel='" + hotel + '\'' +
                '}';
    }
}
