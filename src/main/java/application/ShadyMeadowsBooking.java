package application;

import org.openqa.selenium.WebDriver;
import pages.AdminHomeScreen;
import pages.AdminLogin;
import pages.AdminRoomDetailPage;
import pages.BookingPlatform;

public class ShadyMeadowsBooking {

    public AdminLogin adminLogin;
    public AdminHomeScreen adminHomeScreen;
    public AdminRoomDetailPage adminRoomDetailPage;
    public BookingPlatform bookingPlatform;

    public ShadyMeadowsBooking(WebDriver driver){
        adminLogin = new AdminLogin(driver);
        adminHomeScreen = new AdminHomeScreen(driver);
        adminRoomDetailPage = new AdminRoomDetailPage(driver);
        bookingPlatform = new BookingPlatform(driver);
    }
}
