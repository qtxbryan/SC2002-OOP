package customer;

import java.util.List;

import booking.Booking;

public class CustomerAccount {
	
	
	private int customerID; 
	private String customerName;
	private int mobileNo; 
	private String email; 
	private List<Booking> bookingHistory;
	
	
	public CustomerAccount(int customerID) {
		
		this.customerID = customerID; 
		
	}


	public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(int customerID) {
		this.customerID = customerID;
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
