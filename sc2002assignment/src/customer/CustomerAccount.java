package customer;

import java.util.List;

import booking.Booking;

public class CustomerAccount {
	
	
	private int customerID; 
	private String customerName;
	private int mobileNo; 
	private String email; 
	private List<Booking> bookingHistory;
	

	public CustomerAccount(String name, String email2, String mobileNo2) {
        // TODO Auto-generated constructor stub
	    customerName = name;
	    email = email2;
	    mobileNo = mobileNo2;
    }


    public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(String string) {
		this.customerID = string;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public int getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Booking> getBookingHistory() {
		return bookingHistory;
	}


	public void setBookingHistory(List<Booking> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}
	
	

	
	

}
