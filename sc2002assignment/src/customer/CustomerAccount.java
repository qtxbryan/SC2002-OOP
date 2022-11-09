package customer;

import java.util.List;

public class CustomerAccount {
	
	
	private String customerID; 
	private String customerName;
	private String mobileNo; 
	private String email; 
	private List<String> bookingHistory;
	

	public CustomerAccount(String name, String email2, String mobileNo2) {
        // TODO Auto-generated constructor stub
	    customerName = name;
	    email = email2;
	    mobileNo = mobileNo2;
    }


    public String getCustomerID() {
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


	public String getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<String> getBookingHistory() {
		return bookingHistory;
	}


	public void setBookingHistory(List<String> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}
	
	

	public void addBookingID(String bookingID) {
    	bookingHistory.add(bookingID);
    }
	

}
