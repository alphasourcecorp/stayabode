package stayabode.foodyHive.constants;


public interface APIBaseURL {


//testing
   /* String BASEURLLINK = "https://qaapi.foodyhive.com/api/";
    String CHECKOUTURLLINK = "https://qa.foodyhive.com/";*/



/***test**/
//    String BASEURLLINK = "https://cimaviapi.azurewebsites.net/api/";
//    String CHECKOUTURLLINK = "https://www.alphasource-team.xyz/";

    /****live****/
    String BASEURLLINK = "https://foodyhiveservices.azurewebsites.net/api/";
    String CHECKOUTURLLINK = "https://www.foodyhive.com/";


    String LoginUserInfoPOSTURL = BASEURLLINK + "UserInfo";

    String getUserRoleInfo = BASEURLLINK + "UserInfo/GetUserRoleInfo/";

    String checkUserExistsOrNot = BASEURLLINK + "UserInfo/GetUserDetailsByEmail/";


    //String BASEURLLINK_B2B = "http://foodyhivesubscriptionrequestv2.eastus.azurecontainer.io/";
    //String BASEURLLINK_B2B = "https://fhsubscriptionrequestv2.azurewebsites.net/";
   // String BASEURLLINK_B2B = "https://devstayaboadefoodyhivesubscriptionrequest-v2.azurewebsites.net/";
   // String BASEURLLINK_B2B = "https://devcolivefoodyhivesubscriptionrequest-v2.azurewebsites.net/";
    // String BASEURLLINK_B2B = "https://devstayaboadefoodyhivesubscriptionrequest-v2.azurewebsites.net/";
    String BASEURLLINK_B2B = "https://stayabodefoodyhivesubscriptionrequest-v2.azurewebsites.net/";
    String BASEURLLINK_B2B_LOGIN = BASEURLLINK_B2B+"api/UserLogin/userlogin";
    String BASEURLLINK_B2B_ADD_SCHEDULE = BASEURLLINK_B2B+"api/Scheduling/addnewschedule";
    String BASEURLLINK_B2B_UPDATE_SCHEDULE = BASEURLLINK_B2B+"api/Scheduling/UpdateSchedule";
    String BASEURLLINK_B2B_SCHEDULE_LIST = BASEURLLINK_B2B+"api/Scheduling/GetSchedulesByID/";
    String BASEURLLINK_B2B_SCHEDULE_DELETE = BASEURLLINK_B2B+"api/Scheduling/DeleteSchedule/";
    String BASEURLLINK_B2B_SB_REQUEST = BASEURLLINK_B2B+"api/SubscriptionRequest/Create";
    String BASEURLLINK_B2B_SB_UPDATE = BASEURLLINK_B2B+"api/SubscriptionRequest/UpdateRequest";
    String BASEURLLINK_B2B_company_list = BASEURLLINK_B2B+"api/CompanySignUp";
    String BASEURLLINK_B2B_Subscription_list = BASEURLLINK_B2B+"api/SubscriptionRequest/GetSubscriptionRequestByFilter";
    String BASEURLLINK_B2B_Subscription_list_detail = BASEURLLINK_B2B+"api/SubscriptionRequest/GetRequestListBy/";
    String BASEURLLINK_B2B_UpdateRequestStatus = BASEURLLINK_B2B+"api/SubscriptionRequest/UpdateRequestStatus/";
    String BASEURLLINK_B2B_AddCustomer = BASEURLLINK_B2B+"api/CompanySignUp/RegisterNewCompany";
    String BASEURLLINK_B2B_AddRequestId = BASEURLLINK_B2B+"api/SubscriptionRequest/GetAllRequestsListBy/";

    String BASEURLLINK_B2B_change_PWD = BASEURLLINK_B2B+"/api/CompanySignUp/ChangeCompanyPassword";
    String BASEURLLINK_B2B_Dropmessage = BASEURLLINK_B2B+"/api/DropMessage/AddMessage";





    /**
     Platform Module API's Lists
     **/

    String franchisesURL = BASEURLLINK + "Franchise/GetFranchiseByFilter/1";

    String LeftSideMenuURL = BASEURLLINK + "LeftMenu";

    String chefsURL = BASEURLLINK + "Chef";

    String consumersURL = BASEURLLINK + "Consumer";

    String usersURL = BASEURLLINK + "Dashboard/User";

    String rolesURL = BASEURLLINK + "Dashboard/Roles";

    String cloudKitchenURl = BASEURLLINK + "CloudKitchen";

    String deliveryPartnerURL = BASEURLLINK + "Deliverypartner";

    String paymentGatewayURL = BASEURLLINK + "PaymentGateway";

    String franchiseProfileID = BASEURLLINK + "FranchiseDetail/";

    String cloudKitchenProfileID = BASEURLLINK + "CloudKitchenProfile/";

    String deliveryPartnerProfileID = BASEURLLINK + "FranchiseesMapped/";

    String paymentGatewayProfileURL = BASEURLLINK + "PaymentGateway/";

    String subscriptionsURL = BASEURLLINK + "Subscription";

    String getrevenueByFranchisee = BASEURLLINK + "Dashboard/GetRevenueByFranchise";

    String getTopFranchiseesList = BASEURLLINK + "Franchise/GetTopProspectFranchiseList";

    String getDashboardCardDetails = BASEURLLINK + "Dashboard/GetDashboardCardDetails";

    String revenueByYearForFranchise = BASEURLLINK + "/RevenueByYear/GetRevenueByFranchise";

    String revenueByYear = BASEURLLINK + "RevenueByYear";

    String search = BASEURLLINK + "Dashboard/MobileSearch";

    String notifications = BASEURLLINK + "Notification";

    String role = BASEURLLINK + "Role";

    String revenueSharing = BASEURLLINK + "RevenueSharing";

    String usersInfo = BASEURLLINK + "UserInfo/GetAllUserInfo/101";


    /**
     Franchisee Module API's Lists
     **/

    String franchiseGetRevenueByChef = BASEURLLINK + "FranchiseDashboard/GetRevenueByChef/";

    String franchiseeDashboardCardDetails = BASEURLLINK + "FranchiseDashboard/GetFranchiseDashboardCardDetails/";

    String franchiseeDashboardGetCloudKitchen = BASEURLLINK + "FranchiseDashboard/GetCloudKitchen/";

    String franchiseeDashboardOrdersRevenue = BASEURLLINK + "FranchiseDashboard/OrdersRevenue";

    String franchiseeGetCloudKitchenByFranchiseID = BASEURLLINK + "CloudKitchen/GetCloudKitchenBYFranchise/";

    String franchiseesGetFranchiseByFranchiseeID = BASEURLLINK + "Franchise/";

    String franchiseeGetDeliveryPartnerByFranchiseeID = BASEURLLINK + "Deliverypartner/GetDeliveryPartnerBYFranchise/";

    String franchiseeGetPaymentGatewayByFranchiseeID = BASEURLLINK + "PaymentGateway/GetPaymentDetailBYFranchise/";



    /**
     Chefs Module API's Lists
     **/

    String chefDashboardCardHome = BASEURLLINK + "ChefDashboard/CardDetails/";

    String saveUserDeviceToken = BASEURLLINK + "DeviceDetail/save";

    String chefGlobalSeach = BASEURLLINK + "Dashboard/GlobalSearchChefMobile";

    String chefProfile = BASEURLLINK + "Chef/";

    String chefDashbaordRevenueByDish = BASEURLLINK + "ChefDashboard/RevenueByDish/";

    String chefDashBoardBulkOrders = BASEURLLINK + "ChefDashboard/BulkOrder";

    String chefsGETMenus = BASEURLLINK + "Dish/GetChefMenu/";

    String chefsGETMenusByFilter = BASEURLLINK + "Dish/GetChefMenuByFilter/";

    String chefGETDishID = BASEURLLINK + "Dish/GetDishById/";

    String chefSideMenu = BASEURLLINK + "LeftMenu/ChefSideMenu";

    String chefsGETPromoCodes = BASEURLLINK + "PromoCode/Chef/";

    String consumersReviews = BASEURLLINK + "ConsumerReview";

    String getChefsOrdersDetails = BASEURLLINK + "Order/GetOrderDetails/";

    String getFilteredOrderDetails = BASEURLLINK + "Order/GetFilterOrderDetails";

    String updateOrderStatus = BASEURLLINK + "Order/UpdateStatus/";

    String readyForDelivery = BASEURLLINK + "OrderDelivery/OrderDelivered/";


    String updatePreparationTime = BASEURLLINK + "Order/Updatepreparationtime/";

    String getChefsDashboard = BASEURLLINK + "DashboardDetails/GetChefDashboard?chefid=";

    String getChefsNotifications = BASEURLLINK + "Notification/Role?role=2&Id=";


    String chefTopRatedItems = BASEURLLINK + "Dish/GetMostOrderedAndTopRatedDish/";

    String activeOrDeactiveStatus = BASEURLLINK + "Dish/ActiveStatus";

    String deleteDish = BASEURLLINK + "Dish/Delete/";

    String updateChefProfile = BASEURLLINK + "Chef";


    /**
     Comsumer Module API's Lists Consumer Home Screen API's
     **/

    String consumerLeftMenu = BASEURLLINK + "LeftMenu/Consumer";


    String searchOptions = BASEURLLINK + "Dish/GetListOfValues";

    String getHomePageCategories = BASEURLLINK + "Dish/GetDishCategories";

    String consumerHomePage = BASEURLLINK + "customer/Catalog/search";

    String getDishByFullId = BASEURLLINK + "Dish/GetFullDishById/";

    String getDishById = BASEURLLINK+"Dish/GetDishById/";

    String addToCart = BASEURLLINK + "AddToCart";

    String updateCart = BASEURLLINK + "AddToCart/UpdateCart";


    String updateChefMenuItem = BASEURLLINK + "Dish/Update/103";

    String addChefsMenuItems = BASEURLLINK + "Dish/Create";

    String removeCart = BASEURLLINK + "AddToCart/";

    String getCartsList = BASEURLLINK + "AddToCart/GetCartList/";

    String addDeliveryAddress = BASEURLLINK + "Consumer/AddDeliveryAddress";

    String getAvailableDeliveryAddress = BASEURLLINK + "Consumer/GetAvaliableAddress/";

    String placeOrder = BASEURLLINK + "Consumer/OrderConfirmAndPay/";

    String removeAllCarts = BASEURLLINK + "AddToCart/EmptyCart/";

    String getFavouritesForCustomers = BASEURLLINK + "customer/FavouriteDish/";

    String addFavourites = BASEURLLINK + "customer/FavouriteDish/isfavourite/";

    String getConsumersOrdersList = BASEURLLINK + "Consumer/OrderHistory";



    String getCookedChefProfile = BASEURLLINK + "ChefProfileMobile/GetChefRatings&Reviews";

    String getCookedChefTodaysMenu = BASEURLLINK + "Dish/GetChefMenuForToday/";

    String getLocationURl = BASEURLLINK + "Location";

    String getChefsLocation = BASEURLLINK + "Chef/GetChefBYFranchiseOnConsumerLocation";

    String getAllUIAssets = BASEURLLINK + "Assets/GetAllUIAssets";

    String getChefsPopularMenus = BASEURLLINK + "Dish/GetChefPopularMenu/";

    String addJoinChefs = BASEURLLINK + "BecomeAChefOrFranchise/Chef";

    String addRating = BASEURLLINK + "ChefProfileMobile/EnterComments&Ratings";

    String getReviewsAndRatings = BASEURLLINK + "ChefProfileMobile/GetDishRatingAverage/";

    String updateAddress = BASEURLLINK + "Consumer/UpdateDeliveryAddress/";

    String deleteConsumerDeliveryAddress = BASEURLLINK + "Consumer/DeleteDeliveryAddress/";

    String getUsersLocationAddress = BASEURLLINK + "customer/Catalog/GetAddress";

    String getPlacesAddress = BASEURLLINK + "customer/Catalog/GetAutoPopulateAddress/";

    String getLatLongAPIFromAddress = BASEURLLINK + "customer/Catalog/GetGeoCoOrdinate/";


    String getDeliveryCharges = BASEURLLINK + "Consumer/DeliveryCharges/";

    String getOrdersListFromInvoiceNumber = BASEURLLINK + "Consumer/OrderNo/";

    String getSideMenuCounts = BASEURLLINK + "Consumer/GetNotificationCount/";

    String checkServicesAvailability = BASEURLLINK + "Franchise/GetFranchiseByFilter/1/";

    String checkBusinessServicesAvailability = BASEURLLINK + "customer/Catalog/GetServiceAvailability";

    String getWalletHistory = BASEURLLINK + "Wallet/";

    String inviteFriend = BASEURLLINK + "InviteFreind";

    String checkHavingReferralOrNot = BASEURLLINK + "IsHavingRefferalOrNot/";
   // String chefLocation = "https://cimaviapi.azurewebsites.net/api/chef/chef-location-details";
    String chefLocation =  BASEURLLINK +"chef/chef-location-details";

    String getConsumersOrdersfullList = BASEURLLINK + "consumer/get-orders/";

//test
//    String getReferrals = "https://alphasourcehostingreferralcodes.azurewebsites.net/api/referrals/";
//    String addReferrals = "https://alphasourcehostingreferralcodes.azurewebsites.net/api/referrals/batch";
//    String getUserInfoForReferral =BASEURLLINK+"userinfo/GetUserDetailsByEmail/";
//    String applyReferral =BASEURLLINK+"userinfo/apply-referral/";
 //   String getReferralpoints =BASEURLLINK+"wallet/score/";

//production
    String getReferrals = "https://foodyhivereferrals.azurewebsites.net/api/referrals/";
    String addReferrals = "https://foodyhivereferrals.azurewebsites.net/api/referrals/batch";
    String getUserInfoForReferral =BASEURLLINK+"userinfo/GetUserDetailsByEmail/";
    String applyReferral =BASEURLLINK+"userinfo/apply-referral/";
    String getReferralpoints =BASEURLLINK+"wallet/score/";

    String getprivilege = BASEURLLINK +"userinfo/privilege-status/";
    String offlinepay = BASEURLLINK +"consumer/orderconfirmandpay/";

}
