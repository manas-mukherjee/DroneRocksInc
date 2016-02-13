package com.drone.qb;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.BillableStatusEnum;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.TimeActivity;
import com.intuit.ipp.data.TimeActivityTypeEnum;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.services.ReportService;
import com.intuit.ipp.util.Config;

public class QuickBookService {

	public static final Logger LOG = Logger.getLogger(QuickBookService.class);
	private DataService service;
	private ReportService reportService;
	
	private static HashMap<String, String> DRONE_ID_NAME_MAP = new HashMap<String, String>();
	
	static{
		DRONE_ID_NAME_MAP.put("drone1", "68");
		DRONE_ID_NAME_MAP.put("drone2", "69");
		DRONE_ID_NAME_MAP.put("drone3", "70");
		DRONE_ID_NAME_MAP.put("drone4", "71");
	}
	
	QuickBookService(){
		this.setContext();
	}

/*	public static void main(String args[]) {
		QuickBookService test = new QuickBookService();
		test.setContext();
		//test.simpleQuery();
		test.createTimeActivity(5,"drone1","JoAnn");
	}
*/
	/*
	 *<TimeActivity domain="QBO" sparse="false">
	      <TxnDate>2016-02-13</TxnDate>
	      <NameOf>Employee</NameOf>
	      <EmployeeRef name="Drone1">68</EmployeeRef>
	      <CustomerRef name="JoAnn Peach">67</CustomerRef>
	      <ItemRef name="Hours">2</ItemRef>
	      <ClassRef name="Drone_onshore">5100000000000000541</ClassRef>
	      <BillableStatus>Billable</BillableStatus>
	      <Taxable>false</Taxable>
	      <HourlyRate>15</HourlyRate>
	      <Hours>0</Hours>
	      <Minutes>8</Minutes>
      </TimeActivity>
*/
	public void createTimeActivity(int minutesWorked, String droneName, String customerName) {
		TimeActivity timeActivity = new TimeActivity();
		timeActivity.setNameOf(TimeActivityTypeEnum.EMPLOYEE);
		
		ReferenceType empRef = new ReferenceType();
		empRef.setValue(DRONE_ID_NAME_MAP.get(droneName));
		empRef.setName(droneName);
		timeActivity.setEmployeeRef(empRef);
		
		ReferenceType custRef = new ReferenceType();
		custRef.setValue("67");
		custRef.setName(customerName);
		timeActivity.setCustomerRef(custRef);
		
		ReferenceType itemRef = new ReferenceType();
		itemRef.setValue("2");
		itemRef.setName("Hours");
		timeActivity.setItemRef(itemRef);
		
		timeActivity.setTaxable(false);
		timeActivity.setHourlyRate(new BigDecimal(10));
		timeActivity.setBillableStatus(BillableStatusEnum.BILLABLE);
		
		timeActivity.setHours(0);
		timeActivity.setMinutes(minutesWorked);
		
		timeActivity.setDescription("Moving staff at warehouse");
		
		try {
			this.service.add(timeActivity);
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}

	private void simpleQuery() {
		try {
			QueryResult executeQuery = this.service.executeQuery("select * from Account");
		
			Customer entity = (Customer)executeQuery.getEntities().get(0);
			System.out.println("Name - " + entity.getDisplayName());
		} catch (FMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void setContext() {
	
		Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, "xml");
		Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
		
		Config.setProperty(Config.BASE_URL_QBO,"https://sandbox-quickbooks.api.intuit.com/v3/company");

		String appToken = "103dd5ceb2e91b493eb8410bc59e04f2302e";
		String consumerKey = "qyprdpy0GBWqPca222HzeETKsg1QIF";
		String consumerSecret = "N4cfj6VzUcpSkzEpYyNLI0vViNIL6hHrywwRp6Zi";
		String accessToken = "qyprdCy5z42FaYE1Yn6wGn33Mj809zZcUVqbOtzfklFjWbpH";
		String accessTokenSecret = "xWUfigZi1e7DFEboUj72Dig8LfMON5r2hWergjK2";
		String realmID = "193514292411492";
		
		OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey,
				consumerSecret, accessToken, accessTokenSecret);

		Context context = null;
		try {
			context = new Context(oauth, appToken, ServiceType.QBO, realmID);
		} catch (FMSException e) {
			e.printStackTrace();
		}
		this.service = new DataService(context);
		this.reportService = new ReportService(context);
	}
}