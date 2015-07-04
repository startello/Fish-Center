package com.bionic.fishtrading;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bionic.fishtrading.bo.AccountantService;
import com.bionic.fishtrading.bo.ColdStoreManagerService;
import com.bionic.fishtrading.bo.GeneralManagerService;
import com.bionic.fishtrading.bo.GoodsListService;
import com.bionic.fishtrading.bo.SecurityOfficerService;
import com.bionic.fishtrading.bo.UserService;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.FishType;
import com.bionic.fishtrading.model.Parcel;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.ParcelOrderItem;
import com.bionic.fishtrading.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/application-config.xml" })
public class Tests {
	private ApplicationContext context;
	private GeneralManagerService gms;
	private Logger logger;
	private ColdStoreManagerService csms;
	private GoodsListService gls;
	private UserService cs;
	@SuppressWarnings("unused")
	private AccountantService as;
	@SuppressWarnings("unused")
	private SecurityOfficerService sos;

	@Before
	public void setUp() {
		logger = LogManager.getLogger(Tests.class);
		context = new ClassPathXmlApplicationContext(
				"spring/application-config.xml");
		gms = context.getBean(GeneralManagerService.class);
		csms = context.getBean(ColdStoreManagerService.class);
		gls = context.getBean(GoodsListService.class);
		cs = context.getBean(UserService.class);
		as = context.getBean(AccountantService.class);
		sos = context.getBean(SecurityOfficerService.class);
	}

	@Test
	public void tests() {
		// fishTypeTests();
		/*
		 * parcelTests(); coldStoreTests(); customerTests(); reportTests();
		 * accountantTests(); securityOfficerTests();
		 */
	}

	public void fishTypeTests() {
		gms.addFishType(new FishType("Тунец", "Рыба"));
		gms.addFishType(new FishType("Рак", null));
		gms.addFishType(new FishType("Щука", null));
		gms.addFishType(new FishType("Окунь", null));
		FishType fishType = gms.getFishTypeById(1);
		fishType.setName("Кит");
		gms.updateFishType(fishType);
		logger.warn(" ");
		logger.warn("FishTypes");
		gms.getAllFishTypes().forEach(item -> logger.warn(item.toString()));
		Parcel parcel = new Parcel("Поставка", null, "Аврора 100", 125,
				java.sql.Date.valueOf(LocalDate.of(2015, Month.MAY, 10)));
		List<ColdStoreItem> items = new ArrayList<ColdStoreItem>();
		items.add(new ColdStoreItem(gms.getFishTypeById(1), 145, 56, 2.5));
		items.add(new ColdStoreItem(gms.getFishTypeById(2), 132, 32, 6.3));
		for (ColdStoreItem item : items)
			item.setColdStoreItemParcel(parcel);
		parcel.setColdStoreItems(items);
		gms.addParcel(parcel);
		logger.warn(" ");
		logger.warn("Parcels");
		gms.getAllParcels().forEach(item -> logger.warn(item.toString()));
		parcel = gms.getParcelById(1);
		Collection<ColdStoreItem> parcelItems = new ArrayList<ColdStoreItem>();
		logger.warn(parcel.getColdStoreItems().isEmpty());
		if (!parcel.getColdStoreItems().isEmpty())
			for (ColdStoreItem item : parcel.getColdStoreItems()) {
				item.setArrivalDate(java.sql.Date.valueOf(LocalDate.now()));
				item.setWeightArrived(item.getWeightPurchased());
				item.setWeightLeft(item.getWeightPurchased());
				item.setColdStoreItemParcel(parcel);
				logger.warn(item);
				parcelItems.add(item);
			}
		parcel.setColdStoreItems(parcelItems);
		try {
			csms.updateParcel(parcel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.warn(" ");
		logger.warn("ColdStoreItems");
		csms.getColdStoreItems().forEach(item -> logger.warn(item.toString()));
		// wqdqwdqw
		gms.getArrivedColdStoreItems().forEach(
				item -> logger.warn(item.toString()));
		cs.addUser(new User("Олег", "Иванов", "i@i.i", "64285", 0));
		User user = cs.auth("i@i.i", "64285");
		cs.updateUser(user);
		user = cs.auth("i@i.i", "64285");
		cs.addUser(new User("Иван", "Иванов", "i231@i.i", "224242", 0));
		List<ParcelOrderItem> orderItems = new ArrayList<ParcelOrderItem>();
		ColdStoreItem csi = gls.getColdStoreItemById(1);
		orderItems.add(new ParcelOrderItem(csi, csi.getWeightLeft(), csi
				.getSellingPrice()));
		csi = gls.getColdStoreItemById(2);
		orderItems.add(new ParcelOrderItem(csi, csi.getWeightLeft(), csi
				.getSellingPrice()));
		ParcelOrder parcelOrder = new ParcelOrder(user, "sh12000",
				java.sql.Date.valueOf(LocalDate.of(2015, Month.MAY, 17)), null,
				orderItems);
		try {
			cs.addParcelOrder(parcelOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.warn(" ");
		logger.warn("ColdStoreItems");
		csms.getColdStoreItems().forEach(item -> logger.warn(item.toString()));
		logger.warn(" ");
		logger.warn("Parcels");
		gms.getAllParcels().forEach(item -> logger.warn(item.toString()));
		user = cs.auth("i@i.i", "64285");
	}
	/*
	 * public void parcelTests() { List<ParcelItem> parcelItems = new
	 * ArrayList<ParcelItem>(); parcelItems.add(new
	 * ParcelItem(gms.getFishTypeById(1), 145, 56)); parcelItems.add(new
	 * ParcelItem(gms.getFishTypeById(2), 132, 32)); gms.addParcel(new
	 * Parcel("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅ", null,
	 * "пїЅпїЅпїЅпїЅпїЅпїЅ 100", 125, java.sql.Date.valueOf(LocalDate.of(2015,
	 * Month.MAY, 10))), parcelItems); Parcel parcel = gms.getParcelById(1);
	 * parcel.setDescription("ololo"); List<ParcelItem> items = new
	 * ArrayList<ParcelItem>( parcel.getParcelItems());
	 * items.get(1).setPrice(760); items.add(new
	 * ParcelItem(gms.getFishTypeById(3), 100, 10)); gms.updateParcel(parcel,
	 * items); logger.warn(" "); logger.warn("Parcels");
	 * gms.getAllParcels().forEach( item -> { logger.warn(item.toString());
	 * logger.warn("ParcelItems"); List<ParcelItem> parcelList = new
	 * ArrayList<ParcelItem>( item.getParcelItems());
	 * parcelList.forEach(parcelItem -> logger.warn(parcelItem .toString()));
	 * }); }
	 * 
	 * public void coldStoreTests() { List<ColdStoreItem> coldStoreItems = new
	 * ArrayList<ColdStoreItem>(); coldStoreItems.add(new
	 * ColdStoreItem(gms.getFishTypeById(1), 145, 10)); coldStoreItems.add(new
	 * ColdStoreItem(gms.getFishTypeById(2), 132, 12)); csms.add(coldStoreItems,
	 * 1, java.sql.Date.valueOf(LocalDate.of(2015, Month.MAY, 06)));
	 * logger.warn(" "); logger.warn("Cold Store Items");
	 * gms.getAllColdStoreItems().forEach(item -> logger.warn(item));
	 * logger.warn(" "); logger.warn("1 Parcel Items"); List<ParcelItem> list =
	 * new ArrayList<ParcelItem>(gms.getParcelById(1) .getParcelItems());
	 * list.forEach(item -> logger.warn(item)); gms.setForSale(100, 97, 1);
	 * gms.setForSale(45, 88, 2); logger.warn(" "); logger.warn("Goods List");
	 * gls.getAll().forEach(item -> logger.warn(item)); try {
	 * csms.requestItemWriteOff(1, 67); } catch (Exception e) { } try {
	 * csms.requestItemWriteOff(2, 67); } catch (Exception e) { }
	 * gms.confirmColdStoreItemWriteOff(1); gms.declineColdStoreItemWriteOff(2);
	 * csms.commitItemWriteOff(1, java.sql.Date.valueOf(LocalDate.now()));
	 * csms.commitItemWriteOff(2, java.sql.Date.valueOf(LocalDate.now()));
	 * logger.warn(" "); logger.warn("WrittenOffItems");
	 * gms.getAllItemsForWriteOff().forEach(item -> logger.warn(item));
	 * logger.warn(" "); logger.warn("Cold Store Items");
	 * gms.getAllColdStoreItems().forEach(item -> logger.warn(item)); }
	 * 
	 * public void customerTests() { cs.addNewCustomer(new
	 * Customer("пїЅпїЅпїЅпїЅ", "пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ", "i@i.i", "64285"));
	 * cs.addBankAccount(1, new BankAccount("214321421", null));
	 * cs.addNewCustomer(new Customer("пїЅпїЅпїЅпїЅ",
	 * "пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ", "i231@i.i", "224242")); Customer customer =
	 * cs.getCustomerById(1); List<BankAccount> bAccounts = new
	 * ArrayList<BankAccount>( customer.getBankAccounts()); logger.warn(" ");
	 * logger.warn("Bank Accounts"); bAccounts.forEach(item ->
	 * logger.warn(item)); logger.warn(" "); logger.warn("Customers");
	 * gms.getAllCustomers().forEach(item -> logger.warn(item));
	 * List<ParcelOrderItem> orderItems = new ArrayList<ParcelOrderItem>();
	 * ColdStoreItem csi = cs.getColdStoreItemById(1); orderItems.add(new
	 * ParcelOrderItem(csi, csi.getWeightForSale(), csi .getPrice())); csi =
	 * cs.getColdStoreItemById(2); orderItems.add(new ParcelOrderItem(csi,
	 * csi.getWeightForSale() + 100, csi.getPrice())); try {
	 * cs.createParcelOrder(customer, bAccounts.get(0), orderItems,
	 * java.sql.Date.valueOf(LocalDate.of(2015, Month.MAY, 12))); } catch
	 * (Exception e) { } orderItems = new ArrayList<ParcelOrderItem>(); csi =
	 * cs.getColdStoreItemById(1); orderItems.add(new ParcelOrderItem(csi,
	 * csi.getWeightForSale(), csi .getPrice())); csi =
	 * cs.getColdStoreItemById(2); orderItems.add(new ParcelOrderItem(csi,
	 * csi.getWeightForSale(), csi .getPrice())); try {
	 * cs.createParcelOrder(customer, bAccounts.get(0), orderItems,
	 * java.sql.Date.valueOf(LocalDate.of(2015, Month.MAY, 12))); } catch
	 * (Exception e) { } logger.warn(" "); logger.warn("Cold Store Items");
	 * gms.getAllColdStoreItems().forEach(item -> logger.warn(item));
	 * logger.warn(" "); logger.warn("Customer Auth");
	 * logger.warn(cs.auth("i@i.i", "64285")); }
	 * 
	 * public void reportTests() { logger.warn(" ");
	 * logger.warn("Parcel Items By Fish Type");
	 * gms.getParcelItemsByFishType(1).forEach(item -> logger.warn(item));
	 * logger.warn(" "); logger.warn("WrittenOffItems By Fish Type");
	 * List<WrittenOffItem> csicol = new ArrayList<WrittenOffItem>(gms
	 * .getColdStoreItemById(1).getWrittenOffItems()); csicol.forEach(item ->
	 * logger.warn(item)); logger.warn(" ");
	 * logger.warn("ParcelOrderItems By Fish Type"); List<ParcelOrderItem>
	 * poicol = new ArrayList<ParcelOrderItem>(gms
	 * .getColdStoreItemById(2).getParcelOrderItems()); poicol.forEach(item ->
	 * logger.warn(item)); logger.warn(" "); logger.warn("Parcels By Date");
	 * gms.getParcelsByDate(java.sql.Date.valueOf(LocalDate.now())).forEach(
	 * item -> logger.warn(item)); logger.warn(" ");
	 * logger.warn("Written Off Items By Date");
	 * gms.getWrittenOffItemsByDate(java.sql.Date.valueOf(LocalDate.now()))
	 * .forEach(item -> logger.warn(item)); logger.warn(" ");
	 * logger.warn("Parcel Orders By Date");
	 * gms.getParcelOrdersByDate().forEach(item -> logger.warn(item)); }
	 * 
	 * public void accountantTests() { csms.commitShipment(1);
	 * as.registerPayment(1, as.getParcelOrderById(1).getSumToPay() / 2);
	 * csms.commitShipment(1); gms.setCustomerStatus(1, 2);
	 * csms.commitShipment(1); }
	 * 
	 * public void securityOfficerTests() { sos.add(new
	 * StaffMember("пїЅпїЅпїЅпїЅпїЅпїЅ", "пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ",
	 * "eqw@WDq.dq", "21221", 2)); sos.add(new StaffMember("пїЅпїЅпїЅпїЅ",
	 * "пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ", "eqwqdqw@22dw.dq", "qwqwad", 1));
	 * logger.warn(" "); logger.warn("Staff Members"); sos.getAll().forEach(item
	 * -> logger.warn(item)); logger.warn(" ");
	 * logger.warn("Staff Members By Position");
	 * sos.getAllByPosition(1).forEach(item -> logger.warn(item));
	 * logger.warn(" "); logger.warn("Staff Member Auth"); sos.deactivate(1);
	 * logger.warn(sos.auth("eqw@WDq.dq", "21221")); sos.activate(1);
	 * logger.warn(sos.auth("eqw@WDq.dq", "21221")); StaffMember sm =
	 * sos.getById(1); sm.setPassword("22221"); sos.update(sm); try {
	 * logger.warn(sos.auth("eqw@WDq.dq", "21221")); } catch (Exception e) { } }
	 */
}
