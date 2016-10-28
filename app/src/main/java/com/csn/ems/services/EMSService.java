package com.csn.ems.services;

import com.csn.ems.model.BreakDetails;
import com.csn.ems.model.CreateLeaveRequest;
import com.csn.ems.model.EmployeeDetails;
import com.csn.ems.model.InTakeMasterDetails;
import com.csn.ems.model.InsertBreakIn;
import com.csn.ems.model.InsertClockIn;
import com.csn.ems.model.LeaveDetails;
import com.csn.ems.model.Login;
import com.csn.ems.model.TimeSheetDetails;
import com.csn.ems.model.UpcomingEvents;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by uyalanat on 24-10-2016.
 */
public interface EMSService {

    @PUT("Employee/UpdateEmployee")
    Call<EmployeeDetails> updateEmployee(@Body EmployeeDetails employeeDetails);

    @GET("Employee/GetEmployeeById")
    Call<EmployeeDetails> getEmployeeById(@Query("employeeId") int employeeId);

    @GET("Leave/GetLeaveDetails")
    Call<List<LeaveDetails>> getLeaveDetails(@Query("employeeId") int employeeId, @Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo, @Query("LeaveStatusId") int leaveStatusId);

    @POST("Leave/CreateLeaveRequest")
    Call<CreateLeaveRequest> createLeaveRequest(@Body CreateLeaveRequest createLeaveRequest);

    @GET("Login/GetLogin")
    Call<Login> getLogin(@Query("UserName") String userName,@Query("Password") String password);

    @POST("Login/ChangePassword")
    Call<Login> changePassword(@Query("UserName") String userName,@Query("Password") String password);

    @GET("TimeSheet/GetTimeSheetDetails")
    Call<List<TimeSheetDetails>> getTimeSheetDetails(@Query("employeeId") int employeeId, @Query("dateFrom") String dateFrom,@Query("dateTo") String dateTo,@Query("status") String status);

    @PUT("TimeSheet/UpdateTimeCardApproval")
    Call<TimeSheetDetails> updateTimeCardApproval(@Body TimeSheetDetails timeSheetDetails);

    @GET("InTakeMaster/GetInTakeMasterDetails")
    Call<InTakeMasterDetails> getInTakeMasterDetails(@Body InTakeMasterDetails inTakeMasterDetails);

    @POST("TimeSheet/InsertClockIn")
    Call<InsertClockIn> insertClockIn(@Body InsertClockIn insertClockIn);

    @PUT("TimeSheet/UpdateClockOut")
    Call<InsertClockIn> updateClockOut(@Body InsertClockIn insertClockIn);

    @POST("TimeSheet/InsertBreakIn")
    Call<InsertBreakIn> InsertBreakIn(@Body InsertBreakIn insertBreakIn);

    @PUT("TimeSheet/UpdateBreakOut")
    Call<InsertBreakIn> updateBreakOut(@Body InsertBreakIn insertBreakIn);

    @GET("TimeSheet/GetBreakDetails")
    Call<List<BreakDetails>> getBreakDetails(@Query("timeSheetId") int timeSheetId,@Query("employeeId") int employeeId);


    @GET("OrgManagement/GetUpcomingEvents")
    Call<List<UpcomingEvents>> getUpcomingEvents(@Query("selectedDate") String selectedDate);

    /*  @GET("client")
    Call<List<Client>> listClients();

    @GET("Property")
    Call<List<Property>> listProperties();

    @GET("ClientUserMapping")
    Call<List<ClientAllocation>> listClientAllocations();

    @GET("Hoarding")
    Call<List<Hoarding>> listHoardings();

    @GET("ClientUserMapping/GetAllUsersClients")
    Call<ClientAllocationDefault> fetchClientUserDefaults();

    @GET("SupervisorSurveyorMapping/GetAllServeyorsSupervisors")
    Call<SupervisorAllocationDefault> fetchSupervisorSurveyorDefaults();

    @POST("Hoarding")
    Call<Hoarding> createHoarding(@Body Hoarding hoarding);

    @POST("property")
    Call<Property> createProperty(@Body Property property);

    @PUT("property")
    Call<Property> updateProperty(@Body Property property);

    @PUT("Hoarding")
    Call<Hoarding> updateHoarding(@Body Hoarding hoarding);

    @POST("User")
    Call<User> createUser(@Body User user);

    @POST("ClientUserMapping")
    Call<ClientAllocation> createClientAllocation(@Body ClientAllocation clientAllocation);

    @POST("SupervisorSurveyorMapping")
    Call<SupervisorAllocation> createSupervisorAllocation(@Body SupervisorAllocation supervisorAllocation);

    @PUT("ClientUserMapping")
    Call<ClientAllocation> updateClientAllocation(@Body ClientAllocation clientAllocation);

    @PUT("SupervisorSurveyorMapping")
    Call<SupervisorAllocation> updateSupervisorAllocation(@Body SupervisorAllocation supervisorAllocation);

    @PUT("User")
    Call<User> updateUser(@Body User user);

    @POST("Client")
    Call<Client> createClient(@Body Client client);

    @PUT("Client")
    Call<Client> updateClient(@Body Client client);

    @POST("RemindPropertySurvey")
    Call<SurveyReminder> createSurveyReminderCall(@Body SurveyReminder surveyReminder);

    @PUT("RemindPropertySurvey")
    Call<SurveyReminder> updateSurveyReminder(@Body SurveyReminder surveyReminder);

    @GET("User/SearchUsers")
    Call<List<User>> filterUsers(@Query("SearchItem") String searchKey, @Query("SearchItemValue") String searchValue);

    @GET("Client/SearchClient")
    Call<List<Client>> filterClients(@Query("SearchItem") String searchKey, @Query("SearchItemValue") String searchValue);

    @GET("ClientUserMapping/SearchClientUserMapping")
    Call<List<ClientAllocation>> filterClientAllocations(@Query("SearchItem") String searchKey, @Query("SearchItemValue") String searchValue);

    @GET("SupervisorSurveyorMapping/SearchSupervisorSurveyorMapping")
    Call<List<SupervisorAllocation>> filterSupervisorAllocations(@Query("SearchItem") String searchKey, @Query("SearchItemValue") String searchValue);

    @GET("Hoarding/SearchHoardings")
    Call<List<Hoarding>> filterHoardings(@Query("UserId") String userId, @Query("SearchItem") String searchKey, @Query("SearchItemValue") String searchValue);

    @GET("RemindPropertySurvey/SearchRemindServeys")
    Call<List<SurveyReminder>> filterSurveyReminders(@Query("UserId") String userId, @Query("SearchItem") String searchKey, @Query("SearchItemValue") String searchValue);

    @GET("Property/SearchProperties")
    Call<List<Property>> filterProperties(@Query("UserId") String userId, @Query("SearchItem") String searchKey, @Query("SearchItemValue") String searchValue);

    @GET("Property/GetById")
    Call<Property> getProperty(@Query("id") int propertyId);

    @GET("Hoarding/GetById")
    Call<Hoarding> getHoarding(@Query("id") int hoardingId);

    @DELETE("User")
    Call<okhttp3.ResponseBody> deleteUser(@Query("id") String userId);

    @GET("User/CheckLoginDetails")
    Call<Login> loginUser(@Query("UserName") String userName, @Query("Password") String password, @Query("ClientId") String clientId);

    @GET("RemindPropertySurvey")
    Call<List<SurveyReminder>> listSurveyReminders();

    @GET("Configurations")
    Call<List<ClientConfiguration>> listConfigurations();

    @GET("PropertyType")
    Call<List<PropertyType>> listPropertyType();

    @GET("PropertyTypeCategory")
    Call<List<PropertyTypeCategory>> listPropertyTypeCategory();

    @GET("GetAllPropertyAgeTypes")
    Call<List<GetAllPropertyAgeTypes>> listGetAllPropertyAgeTypes();

    @GET("GetAllPropertyAgeTypes")
    Call<List<GetAllWaterConnectionTypes>> listGetAllWaterConnectionTypes();

    @GET("GetAllBuildingClassifications")
    Call<List<GetAllBuildingClassifications>> listGetAllBuildingClassifications();

    @GET("GetAllElectricityTypes")
    Call<List<GetAllElectricityTypes>> listGetAllElectricityTypes();

    *//**
     * Constains consolidated data for properties.
     *
     * @return
     *//*

    @GET("property")
    Call<Property> getPropertydata();

    @GET("MasterData/GetAllPropertyMastersData")
    Call<PropertyMaster> fetchMasterData();

    @GET("MasterData/GetAllDataProviders")
    Call<List<GetAllDataProviders>> listGetAllDataProviders();

    @GET("Property/GetPropTypesCategoriesSubCategories")
    Call<List<PropertyType>> listPropertyTypes();

    @DELETE("Hoarding")
    Call<okhttp3.ResponseBody> deleteHoarding(@Query("id") String hoardingId);

    @DELETE("RemindPropertySurvey")
    Call<okhttp3.ResponseBody> deleteSurveyReminder(@Query("id") String surveyReminderId);

    @DELETE("Property")
    Call<okhttp3.ResponseBody> deleteProperty(@Query("id") String propertyId);

    @DELETE("ClientUserMapping")
    Call<okhttp3.ResponseBody> deleteClientAllocation(@Query("id") String allocationId);

    @DELETE("SupervisorSurveyorMapping")
    Call<okhttp3.ResponseBody> deleteSupervisorAllocation(@Query("id") String allocationId);

    @DELETE("Client")
    Call<okhttp3.ResponseBody> deleteClient(@Query("id") String clientId);*/
}
